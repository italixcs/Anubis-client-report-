# Scanner flags · signals, context and false positives

**Keywords:** `HEURISTIC` · `SIGNATURE` · `CAPABILITY` · `FALSE_POSITIVE` · `REACHABILITY` · `SOURCE → SINK`.

A scanner may recognize an API, string, binary or code pattern without tracing its purpose. An account-stealing conclusion needs a reachable path from credentials to an unauthorized use or recipient.

**Actual scanner results are now documented in [Verified scanner results](EXTERNAL-SCANNER-RESULTS.md), with links and genuine screenshots.** That supplement distinguishes VirusTotal's 0/62 result, Filescan's Low Risk verdict and proxyserver/AutoClose string heuristics, RatRater's specific manifest false positive, accurate coverage/unsigned-JAR warnings and Triage telemetry attributed to other processes. The general matrix below describes possible heuristic matches, not additional claimed vendor detections. A new alert should be evaluated against its exact label, artifact hash and evidence; this guide is not a blanket dismissal.

![Evidence scope](../assets/evidence-scope.svg)

## Interpretation labels

| Label | Meaning |
|---|---|
| **Benign explanation supported** | Reviewed callers, data and destinations support an ordinary purpose; a malware interpretation lacks the needed chain |
| **Accurate capability; misleading theft label** | The operation exists, but calling it account theft is unsupported |
| **Real concern** | Demonstrated exposure/behavior that should not be dismissed |
| **Limited assurance** | A purpose is supported, but native/indirect/external code prevents complete certification |

## Keyword and behavior matrix

| Keywords / possible trigger | Possible inference | Reviewed behavior | Assessment |
|---|---|---|---|
| access_token, refresh_token, Bearer, MicrosoftLogin | Token stealer from authentication strings | Deliberate account actions use official Microsoft/Xbox/Minecraft services | **Benign authentication explanation supported**; storage is a separate concern |
| accounts.json, token, newBufferedWriter | Sensitive credential storage | Accounts.save writes reusable tokens without encryption | **Real concern**; not proof of theft or unrelated transmission |
| HttpClient, POST, URI, external URLs | Exfiltration or command-and-control | Authentication, lyrics, skin lookups, configured notifications and proxy checking | Network capability is true; **account-exfiltration interpretation unsupported** |
| Discord, webhook, SET_ACTIVITY | Discord credential theft | Local Presence IPC and optional configured game notifications; no Discord credential-store scan established | **Accurate capability; misleading theft label**; privacy behavior is retained |
| DLL/SO/dylib, System.load, JNI, JNA | Native payload | ImGui GUI, Android picker and Windows media/overlay integration | Expected functionality supported; **limited assurance for custom native code** |
| DexClassLoader, classes.dex | Downloaded executable payload | Loads an image-picker DEX already bundled in the JAR; no network download/load chain found | **Accurate dynamic loading; remote-payload interpretation unsupported** |
| Class.forName, MethodHandles, LambdaMetafactory | Hidden reflective loader | Known Iris API lookup, event-listener adaptation and installed Fabric addon entrypoints | **Benign explanation supported**; outside addons remain outside scope |
| Mixin, Inject, Redirect, Accessor | Code injection | Alters Minecraft rendering/input/gameplay/packet behavior | **Accurate game modification**, not demonstrated OS/process injection or token theft |
| Base64, Signature, JWK, compression | Encrypted malware | Authentication proofs, texture metadata, share/profile codes and model data | **Benign explanation supported**; encoding alone is insufficient |
| EnumWindows, OpenProcess, QueryFullProcessImageName | Process spying or credential scraping | Identifies spotify.exe window owners before using media/title information; no reviewed process-list/credential upload | **Accurate local inspection; theft interpretation unsupported** |
| SendInput, key polling | Keylogger | Optional Windows media commands and Minecraft-window input; no input-recording/upload chain found | **Accurate input interaction; keylogging interpretation unsupported** |
| SetWindowDisplayAffinity, WDA_EXCLUDEFROMCAPTURE | Stealth or screenshot malware | NoInvLeak creates a capture-excluded inventory overlay; no screen acquisition/upload demonstrated | **Accurate concealment capability**; its existence is not a false positive |
| Runtime.getRuntime, ProcessHandle | Shell execution | Worker CPU count, current PID and start time; no reviewed direct Java process-spawn route | **Benign explanation supported**; delegated browser/dialog behavior is separately documented |
| class_####, method_####, decompiler warnings | Obfuscation/evasion | Fabric intermediary mappings and compiler/type/control-flow reconstruction issues | Names/warnings alone **do not establish malicious obfuscation** |
| KillAura, Xray, Fly, ESP, packet cancellation | HackTool/Cheat | Those gameplay features are implemented | Classification may be **accurate**, not false; it does not establish account theft |

## Authentication: destination and trigger matter

Bearer indicates how a token authenticates a request. The meaningful questions are **which token, from where, to whom and under what trigger**. Accounts.addSession accepts deliberate user input; SessionLogin uses the official Minecraft profile API. Microsoft login follows official Microsoft/Xbox/Minecraft endpoints. Finding the word token is insufficient to establish stealing.

Plaintext persistence remains a real issue: the selected account's secret is serialized into a local file. Someone with file access or a shared backup could obtain it, even if no malicious sender exists.

Evidence: [account call chains](ACCOUNT-TOKEN-REVIEW.md), [AuthHttp bytecode](../evidence/anubis/javap/net.anubis.client.account.AuthHttp.txt), [Accounts source](../evidence/anubis/sources/cfr/net/anubis/client/account/Accounts.java).

## Networking: keep payload families separate

| Network family | Data shown in reviewed requests | Account tokens sent to an unrelated recipient? |
|---|---|---|
| Official authentication | Selected credentials, tokens/proofs and profile data | No unrelated recipient identified; tokens do reach legitimate auth services |
| LRCLIB lyrics | Title, artist, album/duration where available, User-Agent | Not identified |
| Skin lookup | Selected username/UUID and response-defined texture URL | Not identified |
| Configured game webhooks | Player/server names, coordinates/event details, optional mention/payment context | Not identified |
| Discord Presence | Local RPC PID/status/server address/timestamps | Not identified |
| SOCKS connectivity probe | Proxy authentication; tunnel to 1.1.1.1:443 | No game-account token identified |

Status/location disclosures can be real privacy behavior. They do not become false merely because account theft is the priority. They remain documented without being relabeled as token exfiltration.

Evidence: [full external network inventory](ANUBIS-SECURITY-REPORT.md#11-external-network-communication), [network sites](../evidence/anubis/network-bytecode-sites.json), [WebhookService source](../evidence/anubis/sources/cfr/net/anubis/client/webhook/WebhookService.java).

## Native loading: provenance strengthens the explanation

Every original non-directory entry in **five ImGui and two Netty JARs** matches the published Maven Central counterpart. Only fabric.mod.json was added. Whole-JAR hashes differ because the ZIP wrapper changed; this is a file-content match, not an exact archive hash match.

This supports the expected library explanation for those specific classes/desktop natives. It does not authenticate the two custom Android libraries or certify the entire mod. The custom Java/DEX/native path fits an image picker, and targeted native inspection found runtime-bridge/mapping operations rather than a demonstrated network or shell-spawn chain. Indirect JNI/native calls leave limits.

Evidence: [content comparisons](../evidence/anubis/upstream-comparison.json), [native inventory](../evidence/anubis/native-inventory.json), [picker disassembly](../evidence/anubis/native/android-picker-disassembly.txt), [DEX picker source](../evidence/anubis/sources/jadx-android/net/anubis/picker/AndroidImagePicker.java).

## Reflection, mixins and decompiler artifacts

Reflection can indirectly invoke code. The reviewed Iris/event/addon paths have known mod purposes. Mixin hooks really change gameplay/rendering/input/networking; the appendix records the actual declarations. Their existence does not establish a credential source or unrelated sink.

Decompiler placeholders and awkward record/bootstrap output are analysis artifacts, not automatically original mod instructions. The review used complementary sources and selected bytecode checks, preserving failures rather than assuming a zero exit code or plausible Java formatting means perfect recovery.

Evidence: [mixins](../evidence/anubis/MIXINS.md), [JADX failures](../evidence/anubis/jadx-incomplete-methods.json), [CFR failures](../evidence/anubis/cfr-incomplete-methods.json), [bootstrap handles](../evidence/anubis/sensitive-bootstrap-handles.json).

## What would change the conclusion

A demonstrated path reading existing launcher/browser/Discord credentials without the user's action, putting an account token into an unrelated request, or downloading/loading attacker-controlled executable code would materially change the assessment. A keyword match without that chain would not.

An alert is a reason to inspect evidence. This report supports specific benign explanations and retains specific real concerns. It does **not** recommend disabling security tools or broadly whitelisting future files or versions.

**Current finding: no account/session-token stealing path identified; plaintext saved-token exposure confirmed.**
