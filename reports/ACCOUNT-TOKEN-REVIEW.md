# Account tokens · what is confirmed

**Keywords:** `SESSION_TOKEN` · `MICROSOFT_AUTH` · `REFRESH_TOKEN` · `LOCAL_STORAGE` · `EXFILTRATION` · `CALL_CHAIN`.

**No account/session-token stealing path was identified in the reviewed code. Plaintext saved-token storage is confirmed.** These describe different things and can both be true.

![Reviewed token flow](../assets/token-flow.svg)

## Storage, authentication and theft

| Behavior | Evidence | Interpretation |
|---|---|---|
| User pastes a session token | ConnectionsPanel → Accounts.addSession → SessionLogin.signIn | Deliberate input, not demonstrated harvesting |
| User completes Microsoft login | Accounts.addMicrosoft → device-code flow → official APIs | Obtains credentials for the selected account |
| Authentication request contains a token | AuthHttp uses documented official endpoints | Transmission is present; unrelated exfiltration was not identified |
| Account secret saved under `token` | Accounts.remember → Accounts.save | Confirmed plaintext credential exposure |
| Account token sent to developer/Discord/webhook | No reviewed chain established | Do not infer theft from unrelated HTTP code |

## Session-token login

```text
User account-panel action
  → Accounts.addSession(token)
  → signIn worker
  → SessionLogin.signIn(accessToken)
  → AuthHttp.get(official Minecraft profile endpoint, accessToken)
  → selected Minecraft session applied
  → Accounts.remember / save
```

The profile endpoint is `https://api.minecraftservices.com/minecraft/profile`, using an Authorization Bearer token. The call chain was checked through source and disassembly; the request was not sent during analysis.

Evidence: [Accounts source](../evidence/anubis/sources/cfr/net/anubis/client/account/Accounts.java), [SessionLogin bytecode](../evidence/anubis/javap/net.anubis.client.account.SessionLogin.txt), [AuthHttp bytecode](../evidence/anubis/javap/net.anubis.client.account.AuthHttp.txt).

## Microsoft login and refresh tokens

```text
User Microsoft sign-in action
  → Accounts.addMicrosoft / microsoftBrowserSignIn
  → requestDeviceCode + awaitApproval at login.live.com
  → MicrosoftLogin.minecraft
  → Xbox device/SISU authorization
  → Minecraft launcher login + profile request
  → selected credentials / session
  → Accounts.remember / save(refreshToken)
```

Saved-account login calls `MicrosoftLogin.refresh(saved.secret())` at the official Microsoft token endpoint. MSA/device/XSTS/Minecraft tokens have different roles; the graphic does not imply every token goes to every service.

The hardcoded Microsoft client ID is a public application identifier, not a password or stolen account. ECDSA/JWK/Base64 logic implements an authentication proof. No reviewed path exports those tokens to an unrelated recipient.

Evidence: [MicrosoftLogin disassembly](../evidence/anubis/javap/net.anubis.client.account.MicrosoftLogin.txt), [Accounts disassembly](../evidence/anubis/javap/net.anubis.client.account.Accounts.txt), [complete network inventory](ANUBIS-SECURITY-REPORT.md#11-external-network-communication).

## The real credential-protection weakness

From Accounts.save:

```java
if (!account.secret().isEmpty()) {
    entry.addProperty("token", account.secret());
}
```

The path is Fabric's config directory plus `anubis/accounts.json`. The save routine contains no encryption/keyring integration or explicit private file-permission policy; permissions inherit the environment defaults. A local reader or exposed backup can reveal reusable tokens. This is **Medium local credential exposure, High confidence**, not proof that the mod steals another application's credentials or exports them.

Account.toString returns type/name. The observed “Switched session to {}” log therefore does not establish token logging. Saved SOCKS passwords have a similar plaintext-storage issue in proxies.json, separately from account-token theft.

## Practical interpretation

No reviewed path was found harvesting existing launcher/browser/Discord credentials or sending account tokens in game webhooks. The broader negative theft conclusion has Medium confidence, bounded by native/JNI indirection, outside runtime implementation and addons.

Protect the saved file and copies, keep them out of public repositories, and avoid saving tokens if plaintext storage is unacceptable. If another event may have exposed a token, use the relevant provider's revocation/sign-out controls. This review does not establish that exposure has occurred.

![Account safety meter](../assets/account-safety-meter.svg)
