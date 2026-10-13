package net.anubis.client.util.spotify;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
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
import net.anubis.client.addon.AddonManager;
import net.anubis.client.util.spotify.Lyrics;
import net.anubis.client.util.spotify.LyricsDiskCache;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
/* loaded from: classes-only.jar:net/anubis/client/util/spotify/LyricsService.class */
public final class LyricsService implements AutoCloseable {
    private static final String MOD_ID = "anubisplus";
    private static final String HOMEPAGE = "https://github.com/4ldenz/Anubis-client";
    private static final int MAX_BODY_BYTES = 4194304;
    private static final long REQUEST_GAP_MS = 300;
    private static final long MAX_INLINE_WAIT_MS = 3000;
    private static final int MAX_ATTEMPTS = 3;
    private static final long DEFAULT_RETRY_AFTER_MS = 30000;
    private static final int FOUND_TTL_DAYS = 30;
    private static final int NOT_FOUND_TTL_HOURS = 12;
    private static final long FAILURE_RETRY_MS = 60000;
    private static final int MEMORY_ENTRIES = 64;
    private static final int DISK_MAX_FILES = 400;
    private static final long DISK_MAX_BYTES = 6291456;
    private static final int WRITES_PER_PRUNE = 25;
    private static final long IDLE_THREAD_MS = 30000;
    private final ThreadPoolExecutor worker;
    private final LyricsDiskCache disk;
    private final String userAgent;
    private volatile String wanted;
    private volatile KeyMemo lastKey;
    private volatile boolean closed;
    private volatile int mode;
    private volatile long busyUntil;
    private volatile HttpClient http;
    private volatile ExecutorService httpExecutor;
    private long nextRequestAt;
    private static final Logger LOGGER = LoggerFactory.getLogger("Anubis");
    private static final URI LRCLIB = URI.create("https://lrclib.net/");
    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(8);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(12);
    private static final long MAX_RETRY_AFTER_MS = TimeUnit.HOURS.toMillis(1);
    private static final long FOUND_TTL_MS = TimeUnit.DAYS.toMillis(30);
    private static final long NOT_FOUND_TTL_MS = TimeUnit.HOURS.toMillis(12);
    private final Object lock = new Object();
    private final Map<String, Memo> memory = new LinkedHashMap<String, Memo>(128, 0.75f, true) { // from class: net.anubis.client.util.spotify.LyricsService.1
        @Override // java.util.LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry<String, Memo> eldest) {
            return size() > 64;
        }
    };
    private final Set<String> pending = new HashSet();
    private volatile boolean online = true;
    private int writesSincePrune = WRITES_PER_PRUNE;

    @Environment(EnvType.CLIENT)
    /* loaded from: classes-only.jar:net/anubis/client/util/spotify/LyricsService$Memo.class */
    private static final class Memo extends Record {
        private final Lyrics lyrics;
        private final long freshUntil;

        private Memo(Lyrics lyrics, long freshUntil) {
            this.lyrics = lyrics;
            this.freshUntil = freshUntil;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Memo.class), Memo.class, "lyrics;freshUntil", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Memo;->lyrics:Lnet/anubis/client/util/spotify/Lyrics;", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Memo;->freshUntil:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Memo.class), Memo.class, "lyrics;freshUntil", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Memo;->lyrics:Lnet/anubis/client/util/spotify/Lyrics;", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Memo;->freshUntil:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Memo.class, Object.class), Memo.class, "lyrics;freshUntil", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Memo;->lyrics:Lnet/anubis/client/util/spotify/Lyrics;", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Memo;->freshUntil:J").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public Lyrics lyrics() {
            return this.lyrics;
        }

        public long freshUntil() {
            return this.freshUntil;
        }
    }

    @Environment(EnvType.CLIENT)
    /* loaded from: classes-only.jar:net/anubis/client/util/spotify/LyricsService$KeyMemo.class */
    private static final class KeyMemo extends Record {
        private final LyricsQuery query;
        private final String key;

        private KeyMemo(LyricsQuery query, String key) {
            this.query = query;
            this.key = key;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, KeyMemo.class), KeyMemo.class, "query;key", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$KeyMemo;->query:Lnet/anubis/client/util/spotify/LyricsQuery;", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$KeyMemo;->key:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, KeyMemo.class), KeyMemo.class, "query;key", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$KeyMemo;->query:Lnet/anubis/client/util/spotify/LyricsQuery;", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$KeyMemo;->key:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, KeyMemo.class, Object.class), KeyMemo.class, "query;key", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$KeyMemo;->query:Lnet/anubis/client/util/spotify/LyricsQuery;", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$KeyMemo;->key:Ljava/lang/String;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public LyricsQuery query() {
            return this.query;
        }

        public String key() {
            return this.key;
        }
    }

    @Environment(EnvType.CLIENT)
    /* loaded from: classes-only.jar:net/anubis/client/util/spotify/LyricsService$Found.class */
    private static final class Found extends Record {
        private final LrclibTrack track;
        private final boolean complete;

        private Found(LrclibTrack track, boolean complete) {
            this.track = track;
            this.complete = complete;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Found.class), Found.class, "track;complete", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Found;->track:Lnet/anubis/client/util/spotify/LrclibTrack;", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Found;->complete:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Found.class), Found.class, "track;complete", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Found;->track:Lnet/anubis/client/util/spotify/LrclibTrack;", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Found;->complete:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Found.class, Object.class), Found.class, "track;complete", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Found;->track:Lnet/anubis/client/util/spotify/LrclibTrack;", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Found;->complete:Z").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public LrclibTrack track() {
            return this.track;
        }

        public boolean complete() {
            return this.complete;
        }
    }

    @Environment(EnvType.CLIENT)
    /* loaded from: classes-only.jar:net/anubis/client/util/spotify/LyricsService$BusyException.class */
    private static final class BusyException extends IOException {
        BusyException(String message) {
            super(message);
        }
    }

    public LyricsService() {
        Path cacheDir = defaultCacheDir();
        this.disk = cacheDir == null ? null : new LyricsDiskCache(cacheDir, DISK_MAX_FILES, DISK_MAX_BYTES);
        this.userAgent = defaultUserAgent();
        this.worker = new ThreadPoolExecutor(1, 1, 30000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), daemonThreads("Anubis-Lyrics"));
        this.worker.allowCoreThreadTimeOut(true);
    }

    public Lyrics lookup(LyricsQuery query) {
        if (query == null || !query.searchable()) {
            return Lyrics.NOT_FOUND;
        }
        String key = keyOf(query);
        this.wanted = key;
        long now = System.currentTimeMillis();
        synchronized (this.lock) {
            Memo memo = this.memory.get(key);
            if (memo != null && now < memo.freshUntil()) {
                return memo.lyrics();
            }
            if (!this.closed && this.pending.add(key)) {
                try {
                    this.worker.execute(() -> {
                        resolve(key, query);
                    });
                } catch (RejectedExecutionException e) {
                    this.pending.remove(key);
                }
            }
            if (memo != null) {
                return memo.lyrics();
            }
            return this.closed ? Lyrics.UNAVAILABLE : Lyrics.LOADING;
        }
    }

    public void setOnline(boolean online) {
        if (this.online == online) {
            return;
        }
        this.online = online;
        synchronized (this.lock) {
            this.mode++;
            this.memory.clear();
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.closed = true;
        this.worker.shutdownNow();
        synchronized (this.lock) {
            this.pending.clear();
        }
        HttpClient client = this.http;
        if (client != null) {
            client.shutdownNow();
        }
        ExecutorService executor = this.httpExecutor;
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    private void resolve(String key, LyricsQuery query) {
        LyricsDiskCache.Entry stored = null;
        int startMode = this.mode;
        try {
            try {
                if (this.closed || !key.equals(this.wanted)) {
                    synchronized (this.lock) {
                        this.pending.remove(key);
                    }
                    return;
                }
                LyricsDiskCache.Entry stored2 = this.disk == null ? null : this.disk.read(key);
                if (stored2 != null) {
                    long freshUntil = stored2.fetchedAt() + (stored2.found() ? FOUND_TTL_MS : NOT_FOUND_TTL_MS);
                    if (System.currentTimeMillis() < freshUntil) {
                        remember(key, Lyrics.of(stored2.track()), freshUntil);
                        synchronized (this.lock) {
                            this.pending.remove(key);
                        }
                        return;
                    }
                }
                if (!this.online) {
                    Lyrics offline = stored2 != null ? Lyrics.of(stored2.track()) : Lyrics.NOT_FOUND;
                    synchronized (this.lock) {
                        if (this.mode == startMode) {
                            this.memory.put(key, new Memo(offline, System.currentTimeMillis() + FAILURE_RETRY_MS));
                        }
                    }
                    synchronized (this.lock) {
                        this.pending.remove(key);
                    }
                    return;
                }
                Found found = fetch(query);
                long fetchedAt = System.currentTimeMillis();
                Lyrics lyrics = Lyrics.of(found.track());
                if (!found.complete()) {
                    remember(key, lyrics, retryAt());
                    synchronized (this.lock) {
                        this.pending.remove(key);
                    }
                    return;
                }
                LrclibTrack keep = lyrics.status() == Lyrics.Status.NOT_FOUND ? null : found.track();
                remember(key, lyrics, fetchedAt + (keep == null ? NOT_FOUND_TTL_MS : FOUND_TTL_MS));
                if (this.disk != null && !this.closed) {
                    this.disk.write(key, keep, fetchedAt);
                    int i = this.writesSincePrune + 1;
                    this.writesSincePrune = i;
                    if (i >= WRITES_PER_PRUNE) {
                        this.writesSincePrune = 0;
                        this.disk.prune();
                    }
                }
                synchronized (this.lock) {
                    this.pending.remove(key);
                }
            } catch (IOException failure) {
                LOGGER.debug("[Anubis] Lyrics lookup for \"{}\" failed: {}", query.title(), failure.toString());
                remember(key, 0 != 0 ? Lyrics.of(stored.track()) : Lyrics.UNAVAILABLE, retryAt());
                synchronized (this.lock) {
                    this.pending.remove(key);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                synchronized (this.lock) {
                    this.pending.remove(key);
                }
            } catch (RuntimeException failure2) {
                LOGGER.debug("[Anubis] Lyrics lookup for \"" + query.title() + "\" failed", failure2);
                remember(key, Lyrics.UNAVAILABLE, retryAt());
                synchronized (this.lock) {
                    this.pending.remove(key);
                }
            }
        } catch (Throwable th) {
            synchronized (this.lock) {
                this.pending.remove(key);
                throw th;
            }
        }
    }

    private Found fetch(LyricsQuery query) throws InterruptedException, IOException, HttpTimeoutException {
        LrclibTrack exact = get(query, true);
        if (exact == null && !query.album().isEmpty()) {
            exact = get(query, false);
        }
        if (exact != null && (exact.hasSynced() || (exact.instrumental() && !exact.hasPlain()))) {
            return new Found(exact, true);
        }
        LrclibTrack fallback = (exact == null || !exact.usable()) ? null : exact;
        try {
            for (String[] terms : LyricsMatcher.searchTerms(query)) {
                LrclibTrack best = LyricsMatcher.best(search(terms[0], terms[1]), query);
                if (best != null) {
                    if (best.hasSynced()) {
                        return new Found(best, true);
                    }
                    if (fallback == null) {
                        fallback = best;
                    }
                }
            }
            return new Found(fallback, true);
        } catch (IOException failure) {
            if (fallback == null) {
                throw failure;
            }
            return new Found(fallback, false);
        }
    }

    private LrclibTrack get(LyricsQuery query, boolean withAlbum) throws InterruptedException, IOException, HttpTimeoutException {
        String[] strArr = new String[8];
        strArr[0] = "track_name";
        strArr[1] = query.title();
        strArr[2] = "artist_name";
        strArr[3] = query.artist();
        strArr[4] = "album_name";
        strArr[5] = withAlbum ? query.album() : "";
        strArr[6] = "duration";
        strArr[7] = query.hasDuration() ? Integer.toString(query.durationSeconds()) : "";
        Response response = send(withQuery("api/get", strArr));
        if (response.status() == 404) {
            return null;
        }
        if (response.status() != 200) {
            throw new IOException("LRCLIB /api/get answered HTTP " + response.status());
        }
        return LrclibTrack.fromJson(json(response.body()));
    }

    private List<LrclibTrack> search(String title, String artist) throws InterruptedException, IOException, HttpTimeoutException {
        Response response = send(withQuery("api/search", "track_name", title, "artist_name", artist));
        if (response.status() == 404) {
            return List.of();
        }
        if (response.status() != 200) {
            throw new IOException("LRCLIB /api/search answered HTTP " + response.status());
        }
        return LrclibTrack.listFromJson(json(response.body()));
    }

    @Environment(EnvType.CLIENT)
    /* loaded from: classes-only.jar:net/anubis/client/util/spotify/LyricsService$Response.class */
    private static final class Response extends Record {
        private final int status;
        private final String body;

        private Response(int status, String body) {
            this.status = status;
            this.body = body;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Response.class), Response.class, "status;body", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Response;->status:I", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Response;->body:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Response.class), Response.class, "status;body", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Response;->status:I", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Response;->body:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Response.class, Object.class), Response.class, "status;body", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Response;->status:I", "FIELD:Lnet/anubis/client/util/spotify/LyricsService$Response;->body:Ljava/lang/String;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public int status() {
            return this.status;
        }

        public String body() {
            return this.body;
        }
    }

    private Response send(String pathAndQuery) throws InterruptedException, IOException, HttpTimeoutException {
        int status;
        long waitMs;
        URI uri = LRCLIB.resolve(pathAndQuery);
        int attempt = 1;
        while (true) {
            pace();
            HttpRequest request = HttpRequest.newBuilder(uri).timeout(REQUEST_TIMEOUT).header("User-Agent", this.userAgent).header("Accept", "application/json").GET().build();
            HttpResponse<byte[]> response = exchange(request);
            this.nextRequestAt = System.currentTimeMillis() + REQUEST_GAP_MS;
            status = response.statusCode();
            if (status != 429 && status != 503) {
                return new Response(status, new String((byte[]) response.body(), StandardCharsets.UTF_8));
            }
            waitMs = retryAfterMillis((String) response.headers().firstValue("Retry-After").orElse(null));
            if (waitMs * attempt > MAX_INLINE_WAIT_MS || attempt >= 3) {
                break;
            }
            this.nextRequestAt = System.currentTimeMillis() + (waitMs * attempt);
            attempt++;
        }
        this.busyUntil = System.currentTimeMillis() + waitMs;
        throw new BusyException("LRCLIB answered HTTP " + status + ", retry in " + waitMs + " ms");
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.net.http.HttpTimeoutException */
    private HttpResponse<byte[]> exchange(HttpRequest request) throws InterruptedException, IOException, HttpTimeoutException {
        CompletableFuture<HttpResponse<byte[]>> call = http().sendAsync(request, info -> {
            return new CappedBody(MAX_BODY_BYTES);
        });
        try {
            return call.get(REQUEST_TIMEOUT.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException closing) {
            call.cancel(true);
            throw closing;
        } catch (ExecutionException failed) {
            Throwable cause = failed.getCause();
            if (!(cause instanceof IOException)) {
                throw new IOException("LRCLIB request failed", cause);
            }
            IOException io = (IOException) cause;
            throw io;
        } catch (TimeoutException e) {
            call.cancel(true);
            throw new HttpTimeoutException("LRCLIB did not answer within " + REQUEST_TIMEOUT.toMillis() + " ms");
        }
    }

    @Environment(EnvType.CLIENT)
    /* loaded from: classes-only.jar:net/anubis/client/util/spotify/LyricsService$CappedBody.class */
    private static final class CappedBody implements HttpResponse.BodySubscriber<byte[]> {
        private final CompletableFuture<byte[]> result = new CompletableFuture<>();
        private final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        private final int limit;
        private Flow.Subscription subscription;

        CappedBody(int limit) {
            this.limit = limit;
        }

        public CompletionStage<byte[]> getBody() {
            return this.result;
        }

        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(Long.MAX_VALUE);
        }

        public void onNext(List<ByteBuffer> buffers) {
            if (this.result.isDone()) {
                return;
            }
            for (ByteBuffer buffer : buffers) {
                if (this.bytes.size() + buffer.remaining() > this.limit) {
                    this.subscription.cancel();
                    this.result.completeExceptionally(new IOException("LRCLIB response too large"));
                    return;
                } else {
                    byte[] chunk = new byte[buffer.remaining()];
                    buffer.get(chunk);
                    this.bytes.write(chunk, 0, chunk.length);
                }
            }
        }

        public void onError(Throwable failure) {
            this.result.completeExceptionally(failure);
        }

        public void onComplete() {
            this.result.complete(this.bytes.toByteArray());
        }
    }

    private void pace() throws InterruptedException, IOException {
        long now = System.currentTimeMillis();
        long busy = this.busyUntil;
        if (busy - now > MAX_INLINE_WAIT_MS) {
            throw new BusyException("LRCLIB asked to wait another " + (busy - now) + " ms");
        }
        long wait = Math.max(this.nextRequestAt, busy) - now;
        if (wait > 0) {
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
        ExecutorService executor = Executors.newCachedThreadPool(daemonThreads("Anubis-Lyrics-Http"));
        HttpClient client2 = HttpClient.newBuilder().connectTimeout(CONNECT_TIMEOUT).followRedirects(HttpClient.Redirect.NORMAL).executor(executor).build();
        this.httpExecutor = executor;
        this.http = client2;
        if (this.closed) {
            client2.shutdownNow();
            executor.shutdownNow();
            throw new InterruptedException("lyrics service closed");
        }
        return client2;
    }

    private void remember(String key, Lyrics lyrics, long freshUntil) {
        synchronized (this.lock) {
            this.memory.put(key, new Memo(lyrics, freshUntil));
        }
    }

    private long retryAt() {
        return Math.max(System.currentTimeMillis() + FAILURE_RETRY_MS, this.busyUntil);
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

    private static String withQuery(String path, String... namesAndValues) {
        StringBuilder uri = new StringBuilder(path);
        char separator = '?';
        for (int i = 0; i + 1 < namesAndValues.length; i += 2) {
            String value = namesAndValues[i + 1];
            if (value != null && !value.isEmpty()) {
                uri.append(separator).append(namesAndValues[i]).append('=').append(URLEncoder.encode(value, StandardCharsets.UTF_8));
                separator = '&';
            }
        }
        return uri.toString();
    }

    private static JsonElement json(String body) throws IOException {
        try {
            return LrclibTrack.parse(body);
        } catch (JsonParseException malformed) {
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
            millis = Long.parseLong(value) * 1000;
        } catch (NumberFormatException e) {
            try {
                millis = ZonedDateTime.parse(value, DateTimeFormatter.RFC_1123_DATE_TIME).toInstant().toEpochMilli() - System.currentTimeMillis();
            } catch (DateTimeParseException e2) {
                millis = 30000;
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
            return FabricLoader.getInstance().getConfigDir().resolve(AddonManager.ENTRYPOINT).resolve("lyrics-cache");
        } catch (LinkageError | RuntimeException e) {
            return null;
        }
    }

    static String defaultUserAgent() {
        String version;
        try {
            version = (String) FabricLoader.getInstance().getModContainer(MOD_ID).map(container -> {
                return container.getMetadata().getVersion().getFriendlyString();
            }).orElse("dev");
        } catch (LinkageError | RuntimeException e) {
            version = "dev";
        }
        int build = version.indexOf(43);
        if (build > 0) {
            version = version.substring(0, build);
        }
        return "Anubis Client/" + version + " (https://github.com/4ldenz/Anubis-client)";
    }
}
