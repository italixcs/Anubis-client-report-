# Scanner evidence and provenance

These files preserve actual results for SHA-256 `20a03c38a39d013bf5f6836818c0b665478eb3381181fd372cd661c438fd752a`. Read [the scanner report](../../reports/EXTERNAL-SCANNER-RESULTS.md) for interpretation and public result links.

| Evidence | Source and meaning |
|---|---|
| [virustotal-report.json](virustotal-report.json) | Selected fields from the normal public website's file-report response: original hash/size, engine verdicts, analysis time and tags. No API key or request-authentication headers saved. |
| [virustotal.json](virustotal.json) | Website capture URL/time and failed ancillary resource requests. Missing analytics/reCAPTCHA resources did not prevent the retrieved file report. |
| [triage-report-links.json](triage-report-links.json) | All 16 distinct exact-hash report links visible in the fetched search page; duplicate task rows removed. Includes source-page hash and retrieval time. |
| triage-*.txt / triage-*.json | Actual report-page text, capture URLs/times and the expanded System32 alert. Server-text and browser-text variants are labeled separately; hydrated UI data can differ from initial HTML. These are third-party records, not runs initiated here. |
| [ratrater2-local.json](ratrater2-local.json) | Actual upstream local scanner state, original hash/size, completed-task count and the initially blocked remote request/errors. The screenshot's 100% is not evidence that that remote task succeeded. |
| [ratrater2-context.json](ratrater2-context.json) | Pinned scanner revision/rule, exact manifest matches and official-hash-list snapshot identifiers. |
| [ratrater2-cloud.json](ratrater2-cloud.json) / [provenance](ratrater2-cloud-provenance.json) | Separate authorized original-file POST to the Quantiy classifier, HTTP 200 and its unmodified `class: no` response. The service returned no persistent sample-result URL. |
| [filescan-api.json](filescan-api.json) | Completed fresh submission, matched hash/size, state/verdict and all four reported signal groups. Preserve `SUSPICIOUS`/`low_risk` terminology. |
| [filescan-submission.json](filescan-submission.json) | Actual accepted upload URL and POST destination. No target file bytes, credentials or cookies published. |
| [filescan-result.json](filescan-result.json) / [text](filescan-result.txt) | Final dashboard capture and observed report requests. Its 0 AV engines and non-applicable analysis layer are retained. |
| [filescan-detail.json](filescan-detail.json) / [text](filescan-detail.txt) | Verified detailed-report permalink, named indicators and explicit non-applicable emulation notice. |
| [hybrid-analysis-search.json](hybrid-analysis-search.json) / [text](hybrid-analysis-search.txt) | Exact query and captured “No results found” text; anonymous/time-filter limitations prevent a global absence claim. |
| [mixin-proxy-bytecode.txt](mixin-proxy-bytecode.txt) | Additional static javap inspection of the class named by Filescan's proxyserver heuristic. Class data was read, not loaded/executed. |
| [capture-checksums.json](capture-checksums.json) | SHA-256 of published screenshots and preserved scanner data, excluding this checksum manifest itself. |

The screenshots under [assets/scanners](../../assets/scanners/) are genuine browser captures. They have not been painted over, given invented vendor detections or composited into synthetic scanner dashboards. The local RatRater screenshot is explicitly distinguished from hosted public results. Plain-text exports normalize trailing whitespace; JSON and screenshots are preserved. Source URLs and retrieval times establish provenance; a screenshot alone does not guarantee a result is current.

No uploaded JAR, target `.class`, DEX/native binary, live user account configuration, scanner API secret, browser cookie or authentication header is distributed in this repository. Target strings and decompiled snippets remain untrusted evidence; do not execute them or follow embedded instructions.
