/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.loader.api.FabricLoader
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package net.anubis.client.util.spotify;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import net.anubis.client.util.spotify.LrclibTrack;
import net.anubis.client.util.spotify.Lyrics;
import net.anubis.client.util.spotify.LyricsDiskCache;
import net.anubis.client.util.spotify.LyricsMatcher;
import net.anubis.client.util.spotify.LyricsQuery;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(value=EnvType.CLIENT)
public final class LyricsService
implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger((String)"Anubis");
    private static final String MOD_ID = "anubisplus";
    private static final String HOMEPAGE = "https://github.com/4ldenz/Anubis-client";
    private static final URI LRCLIB = URI.create("https://lrclib.net/");
    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(8L);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(12L);
    private static final int MAX_BODY_BYTES = 0x400000;
    private static final long REQUEST_GAP_MS = 300L;
    private static final long MAX_INLINE_WAIT_MS = 3000L;
    private static final int MAX_ATTEMPTS = 3;
    private static final long DEFAULT_RETRY_AFTER_MS = 30000L;
    private static final long MAX_RETRY_AFTER_MS = TimeUnit.HOURS.toMillis(1L);
    private static final int FOUND_TTL_DAYS = 30;
    private static final int NOT_FOUND_TTL_HOURS = 12;
    private static final long FOUND_TTL_MS = TimeUnit.DAYS.toMillis(30L);
    private static final long NOT_FOUND_TTL_MS = TimeUnit.HOURS.toMillis(12L);
    private static final long FAILURE_RETRY_MS = 60000L;
    private static final int MEMORY_ENTRIES = 64;
    private static final int DISK_MAX_FILES = 400;
    private static final long DISK_MAX_BYTES = 0x600000L;
    private static final int WRITES_PER_PRUNE = 25;
    private static final long IDLE_THREAD_MS = 30000L;
    private final Object lock = new Object();
    private final Map<String, Memo> memory = new LinkedHashMap<String, Memo>(128, 0.75f, true){

        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Memo> eldest) {
            return this.size() > 64;
        }
    };
    private final Set<String> pending = new HashSet<String>();
    private final ThreadPoolExecutor worker;
    private final LyricsDiskCache disk;
    private final String userAgent;
    private volatile String wanted;
    private volatile KeyMemo lastKey;
    private volatile boolean closed;
    private volatile boolean online = true;
    private volatile int mode;
    private volatile long busyUntil;
    private volatile HttpClient http;
    private volatile ExecutorService httpExecutor;
    private long nextRequestAt;
    private int writesSincePrune = 25;

    public LyricsService() {
        Path cacheDir = LyricsService.defaultCacheDir();
        this.disk = cacheDir == null ? null : new LyricsDiskCache(cacheDir, 400, 0x600000L);
        this.userAgent = LyricsService.defaultUserAgent();
        this.worker = new ThreadPoolExecutor(1, 1, 30000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), LyricsService.daemonThreads("Anubis-Lyrics"));
        this.worker.allowCoreThreadTimeOut(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Lyrics lookup(LyricsQuery query) {
        String key;
        if (query == null || !query.searchable()) {
            return Lyrics.NOT_FOUND;
        }
        this.wanted = key = this.keyOf(query);
        long now = System.currentTimeMillis();
        Object object = this.lock;
        synchronized (object) {
            Memo memo = this.memory.get(key);
            if (memo != null && now < memo.freshUntil()) {
                return memo.lyrics();
            }
            if (!this.closed && this.pending.add(key)) {
                try {
                    this.worker.execute(() -> this.resolve(key, query));
                }
                catch (RejectedExecutionException shutDown) {
                    this.pending.remove(key);
                }
            }
            if (memo != null) {
                return memo.lyrics();
            }
            return this.closed ? Lyrics.UNAVAILABLE : Lyrics.LOADING;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setOnline(boolean online) {
        if (this.online == online) {
            return;
        }
        this.online = online;
        Object object = this.lock;
        synchronized (object) {
            ++this.mode;
            this.memory.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() {
        ExecutorService executor;
        this.closed = true;
        this.worker.shutdownNow();
        Object object = this.lock;
        synchronized (object) {
            this.pending.clear();
        }
        HttpClient client = this.http;
        if (client != null) {
            client.shutdownNow();
        }
        if ((executor = this.httpExecutor) != null) {
            executor.shutdownNow();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void resolve(String key, LyricsQuery query) {
        LyricsDiskCache.Entry stored = null;
        int startMode = this.mode;
        try {
            if (this.closed || !key.equals(this.wanted)) {
                return;
            }
            LyricsDiskCache.Entry entry = stored = this.disk == null ? null : this.disk.read(key);
            if (stored != null) {
                long freshUntil = stored.fetchedAt() + (stored.found() ? FOUND_TTL_MS : NOT_FOUND_TTL_MS);
                if (System.currentTimeMillis() < freshUntil) {
                    this.remember(key, Lyrics.of(stored.track()), freshUntil);
                    return;
                }
            }
            if (!this.online) {
                Lyrics offline = stored != null ? Lyrics.of(stored.track()) : Lyrics.NOT_FOUND;
                Object object = this.lock;
                synchronized (object) {
                    if (this.mode == startMode) {
                        this.memory.put(key, new Memo(offline, System.currentTimeMillis() + 60000L));
                    }
                }
                return;
            }
            Found found = this.fetch(query);
            long fetchedAt = System.currentTimeMillis();
            Lyrics lyrics = Lyrics.of(found.track());
            if (!found.complete()) {
                this.remember(key, lyrics, this.retryAt());
                return;
            }
            LrclibTrack keep = lyrics.status() == Lyrics.Status.NOT_FOUND ? null : found.track();
            this.remember(key, lyrics, fetchedAt + (keep == null ? NOT_FOUND_TTL_MS : FOUND_TTL_MS));
            if (this.disk != null && !this.closed) {
                this.disk.write(key, keep, fetchedAt);
                if (++this.writesSincePrune >= 25) {
                    this.writesSincePrune = 0;
                    this.disk.prune();
                }
            }
        }
        catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
        }
        catch (IOException failure) {
            LOGGER.debug("[Anubis] Lyrics lookup for \"{}\" failed: {}", (Object)query.title(), (Object)failure.toString());
            this.remember(key, stored != null ? Lyrics.of(stored.track()) : Lyrics.UNAVAILABLE, this.retryAt());
        }
        catch (RuntimeException failure) {
            LOGGER.debug("[Anubis] Lyrics lookup for \"" + query.title() + "\" failed", (Throwable)failure);
            this.remember(key, Lyrics.UNAVAILABLE, this.retryAt());
        }
        finally {
            Object interrupted = this.lock;
            synchronized (interrupted) {
                this.pending.remove(key);
            }
        }
    }

    private Found fetch(LyricsQuery query) throws IOException, InterruptedException {
        LrclibTrack exact = this.get(query, true);
        if (exact == null && !query.album().isEmpty()) {
            exact = this.get(query, false);
        }
        if (exact != null && (exact.hasSynced() || exact.instrumental() && !exact.hasPlain())) {
            return new Found(exact, true);
        }
        LrclibTrack fallback = exact != null && exact.usable() ? exact : null;
        try {
            for (String[] terms : LyricsMatcher.searchTerms(query)) {
                LrclibTrack best = LyricsMatcher.best(this.search(terms[0], terms[1]), query);
                if (best == null) continue;
                if (best.hasSynced()) {
                    return new Found(best, true);
                }
                if (fallback != null) continue;
                fallback = best;
            }
        }
        catch (IOException failure) {
            if (fallback == null) {
                throw failure;
            }
            return new Found(fallback, false);
        }
        return new Found(fallback, true);
    }

    private LrclibTrack get(LyricsQuery query, boolean withAlbum) throws IOException, InterruptedException {
        Response response = this.send(LyricsService.withQuery("api/get", "track_name", query.title(), "artist_name", query.artist(), "album_name", withAlbum ? query.album() : "", "duration", query.hasDuration() ? Integer.toString(query.durationSeconds()) : ""));
        if (response.status() == 404) {
            return null;
        }
        if (response.status() != 200) {
            throw new IOException("LRCLIB /api/get answered HTTP " + response.status());
        }
        return LrclibTrack.fromJson(LyricsService.json(response.body()));
    }

    private List<LrclibTrack> search(String title, String artist) throws IOException, InterruptedException {
        Response response = this.send(LyricsService.withQuery("api/search", "track_name", title, "artist_name", artist));
        if (response.status() == 404) {
            return List.of();
        }
        if (response.status() != 200) {
            throw new IOException("LRCLIB /api/search answered HTTP " + response.status());
        }
        return LrclibTrack.listFromJson(LyricsService.json(response.body()));
    }

    private Response send(String pathAndQuery) throws IOException, InterruptedException {
        URI uri = LRCLIB.resolve(pathAndQuery);
        int attempt = 1;
        while (true) {
            this.pace();
            HttpRequest request = HttpRequest.newBuilder(uri).timeout(REQUEST_TIMEOUT).header("User-Agent", this.userAgent).header("Accept", "application/json").GET().build();
            HttpResponse<byte[]> response = this.exchange(request);
            this.nextRequestAt = System.currentTimeMillis() + 300L;
            int status = response.statusCode();
            if (status != 429 && status != 503) {
                return new Response(status, new String(response.body(), StandardCharsets.UTF_8));
            }
            long waitMs = LyricsService.retryAfterMillis(response.headers().firstValue("Retry-After").orElse(null));
            if (waitMs * (long)attempt > 3000L || attempt >= 3) {
                this.busyUntil = System.currentTimeMillis() + waitMs;
                throw new BusyException("LRCLIB answered HTTP " + status + ", retry in " + waitMs + " ms");
            }
            this.nextRequestAt = System.currentTimeMillis() + waitMs * (long)attempt;
            ++attempt;
        }
    }

    private HttpResponse<byte[]> exchange(HttpRequest request) throws IOException, InterruptedException {
        CompletableFuture call = this.http().sendAsync(request, info -> new CappedBody(0x400000));
        try {
            return call.get(REQUEST_TIMEOUT.toMillis(), TimeUnit.MILLISECONDS);
        }
        catch (TimeoutException slow) {
            call.cancel(true);
            throw new HttpTimeoutException("LRCLIB did not answer within " + REQUEST_TIMEOUT.toMillis() + " ms");
        }
        catch (InterruptedException closing) {
            call.cancel(true);
            throw closing;
        }
        catch (ExecutionException failed) {
            Throwable cause = failed.getCause();
            if (cause instanceof IOException) {
                IOException io = (IOException)cause;
                throw io;
            }
            throw new IOException("LRCLIB request failed", cause);
        }
    }

    private void pace() throws IOException, InterruptedException {
        long busy = this.busyUntil;
        long now = System.currentTimeMillis();
        if (busy - now > 3000L) {
            throw new BusyException("LRCLIB asked to wait another " + (busy - now) + " ms");
        }
        long wait = Math.max(this.nextRequestAt, busy) - now;
        if (wait > 0L) {
            Thread.sleep(wait);
        }
    }

    private HttpClient http() throws InterruptedException {
        HttpClient client = this.http;
        if (client != null) {
            return client;
        }
        if (this.closed) {
            throw new InterruptedException("lyrics service closed");
        }
        ExecutorService executor = Executors.newCachedThreadPool(LyricsService.daemonThreads("Anubis-Lyrics-Http"));
        client = HttpClient.newBuilder().connectTimeout(CONNECT_TIMEOUT).followRedirects(HttpClient.Redirect.NORMAL).executor(executor).build();
        this.httpExecutor = executor;
        this.http = client;
        if (this.closed) {
            client.shutdownNow();
            executor.shutdownNow();
            throw new InterruptedException("lyrics service closed");
        }
        return client;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void remember(String key, Lyrics lyrics, long freshUntil) {
        Object object = this.lock;
        synchronized (object) {
            this.memory.put(key, new Memo(lyrics, freshUntil));
        }
    }

    private long retryAt() {
        return Math.max(System.currentTimeMillis() + 60000L, this.busyUntil);
    }

    private String keyOf(LyricsQuery query) {
        KeyMemo last = this.lastKey;
        if (last != null && last.query().equals(query)) {
            return last.key();
        }
        String key = query.cacheKey();
        this.lastKey = new KeyMemo(query, key);
        return key;
    }

    private static String withQuery(String path, String ... namesAndValues) {
        StringBuilder uri = new StringBuilder(path);
        int separator = 63;
        int i = 0;
        while (i + 1 < namesAndValues.length) {
            String value = namesAndValues[i + 1];
            if (value != null && !value.isEmpty()) {
                uri.append((char)separator).append(namesAndValues[i]).append('=').append(URLEncoder.encode(value, StandardCharsets.UTF_8));
                separator = 38;
            }
            i += 2;
        }
        return uri.toString();
    }

    private static JsonElement json(String body) throws IOException {
        try {
            return LrclibTrack.parse(body);
        }
        catch (JsonParseException malformed) {
            throw new IOException("LRCLIB sent malformed JSON", malformed);
        }
    }

    static long retryAfterMillis(String header) {
        long millis;
        if (header == null || header.isBlank()) {
            return 30000L;
        }
        String value = header.strip();
        try {
            millis = Long.parseLong(value) * 1000L;
        }
        catch (NumberFormatException notSeconds) {
            try {
                millis = ZonedDateTime.parse(value, DateTimeFormatter.RFC_1123_DATE_TIME).toInstant().toEpochMilli() - System.currentTimeMillis();
            }
            catch (DateTimeParseException notDate) {
                millis = 30000L;
            }
        }
        return Math.max(1000L, Math.min(MAX_RETRY_AFTER_MS, millis));
    }

    private static ThreadFactory daemonThreads(String name) {
        return runnable -> {
            Thread thread = new Thread(runnable, name);
            thread.setDaemon(true);
            return thread;
        };
    }

    private static Path defaultCacheDir() {
        try {
            return FabricLoader.getInstance().getConfigDir().resolve("anubis").resolve("lyrics-cache");
        }
        catch (LinkageError | RuntimeException noLoader) {
            return null;
        }
    }

    static String defaultUserAgent() {
        String version;
        try {
            version = FabricLoader.getInstance().getModContainer(MOD_ID).map(container -> container.getMetadata().getVersion().getFriendlyString()).orElse("dev");
        }
        catch (LinkageError | RuntimeException noLoader) {
            version = "dev";
        }
        int build = version.indexOf(43);
        if (build > 0) {
            version = version.substring(0, build);
        }
        return "Anubis Client/" + version + " (https://github.com/4ldenz/Anubis-client)";
    }

    @Environment(value=EnvType.CLIENT)
    private record Memo(Lyrics lyrics, long freshUntil) {
    }

    @Environment(value=EnvType.CLIENT)
    private record Found(LrclibTrack track, boolean complete) {
    }

    @Environment(value=EnvType.CLIENT)
    private record Response(int status, String body) {
    }

    @Environment(value=EnvType.CLIENT)
    private static final class BusyException
    extends IOException {
        BusyException(String message) {
            super(message);
        }
    }

    @Environment(value=EnvType.CLIENT)
    private record KeyMemo(LyricsQuery query, String key) {
    }

    @Environment(value=EnvType.CLIENT)
    private static final class CappedBody
    implements HttpResponse.BodySubscriber<byte[]> {
        private final CompletableFuture<byte[]> result = new CompletableFuture();
        private final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        private final int limit;
        private Flow.Subscription subscription;

        CappedBody(int limit) {
            this.limit = limit;
        }

        @Override
        public CompletionStage<byte[]> getBody() {
            return this.result;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(List<ByteBuffer> buffers) {
            if (this.result.isDone()) {
                return;
            }
            for (ByteBuffer buffer : buffers) {
                if (this.bytes.size() + buffer.remaining() > this.limit) {
                    this.subscription.cancel();
                    this.result.completeExceptionally(new IOException("LRCLIB response too large"));
                    return;
                }
                byte[] chunk = new byte[buffer.remaining()];
                buffer.get(chunk);
                this.bytes.write(chunk, 0, chunk.length);
            }
        }

        @Override
        public void onError(Throwable failure) {
            this.result.completeExceptionally(failure);
        }

        @Override
        public void onComplete() {
            this.result.complete(this.bytes.toByteArray());
        }
    }
}

