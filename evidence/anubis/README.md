# Evidence guide

This directory contains static text evidence supporting the account/session-token assessment and the full behavior report.

- `coverage.json`, `analysis-audit.json`, and `decompiler-status.txt`: scope and actual runner outcomes.
- `class-index.json.gz`, `nested-class-index.json.gz`: compressed JSON class structures, method references, bootstrap handles, annotations, and strings. These are data, not executable archives. Gzip compression keeps the full indexes reasonably sized for GitHub.
- `javap/`: 30 class-file disassemblies, read without loading target classes.
- `sources/cfr/`, `sources/jadx/`, `sources/jadx-android/`: decompiled text used by the report's citations. It is untrusted evidence, not code to compile or execute.
- `metadata/`: identity, manifest and shipped default data used to establish artifact scope and default activation. No user account files are included.
- `MIXINS.md`, `mixins-complete.json`: all 147 declared Anubis mixins and 298 hook/accessor/invoker annotations.
- `MODULES.md`, `modules-inventory.json`: packaged module descriptions/defaults. Presence does not always imply registration; see the report's reachability notes.
- `negative-api-checks.json`, `sensitive-bootstrap-handles.json`: reference-search results. Negative matches supplement manual tracing and do not prove universal safety.
- `network-bytecode-sites.json`, `filesystem-bytecode-sites.json`, `filesystem-lines.txt`, `url-string-inventory.json`: direct API sites and contextual constants.
- `nested-inventory.json`, `upstream-comparison.json`: 14 nested-archive summaries and seven published-library content comparisons.
- `native/`, `native-inventory.json`, `android-dex-index.json`: static native symbols/strings/disassembly and DEX structures.
- `indicators.json`: hashes and contextual domains; these service URLs are **not established malicious indicators**.

Paths prefixed `analysis-workspace/` or `original-attachment/` describe the analysis layout and are not links to a private machine. The original JAR, class files, native binaries, DEX, and authentication/config secrets are not published here. Decompiled outputs can contain reconstruction errors; the report documents both JADX and CFR failures.

Report prose and the meter are analysis work. Third-party metadata and decompiled code excerpts remain subject to their original rights/licenses; the analyzed mod declares GPL-3.0-only. This repository is not the mod's upstream source or distribution.
