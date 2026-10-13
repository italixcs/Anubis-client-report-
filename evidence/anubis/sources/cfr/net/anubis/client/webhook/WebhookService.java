/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package net.anubis.client.webhook;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import net.anubis.client.AnubisClientMod;
import net.anubis.client.webhook.WebhookConfig;
import net.anubis.client.webhook.WebhookPayloadBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public final class WebhookService {
    private final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8L)).build();

    public boolean send(WebhookConfig config, WebhookPayloadBuilder payloadBuilder) {
        JsonObject payload;
        try {
            payload = payloadBuilder.build(config);
        }
        catch (RuntimeException exception) {
            AnubisClientMod.LOGGER.warn("[Anubis] Failed to build webhook payload", (Throwable)exception);
            return false;
        }
        HttpRequest request = HttpRequest.newBuilder(URI.create(config.url())).timeout(Duration.ofSeconds(10L)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(payload.toString())).build();
        try {
            HttpResponse<Void> response = this.httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            int status = response.statusCode();
            if (status >= 200 && status < 300) {
                return true;
            }
            AnubisClientMod.LOGGER.warn("[Anubis] Webhook request failed with status {}", (Object)status);
        }
        catch (IOException | InterruptedException exception) {
            if (exception instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            AnubisClientMod.LOGGER.warn("[Anubis] Webhook request failed", (Throwable)exception);
        }
        return false;
    }
}

