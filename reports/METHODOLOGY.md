# Methodology · scope, evidence and confidence

**Keywords:** `STATIC_ANALYSIS` · `HASH` · `JADX` · `CFR` · `JAVAP` · `BYTECODE` · `JNI` · `PROVENANCE`.

![Evidence coverage](../assets/evidence-scope.svg)

## Exact-hash review

The artifact was Anubis Beta 0.9.9.1.jar, 21,206,373 bytes, SHA-256:

```text
20a03c38a39d013bf5f6836818c0b665478eb3381181fd372cd661c438fd752a
```

The original attachment and snapshot hashes match. Author/project/version metadata identifies the supplied bytes but does not independently authenticate authorship or certify a different download.

## Procedure

1. Hash/list the archive and validate paths, duplicates, links/special entries, compression and decompression limits before extraction.
2. Extract as data without preserving executable permissions; inventory metadata, configs, nested JARs, native libraries and resource formats.
3. Give trusted JADX/CFR tools a classes-only copy. Do not launch a target manifest, resource, class or embedded executable.
4. Parse class structures, references, bootstrap handles and annotations. Trace sensitive paths and cross-check selected classes with javap.
5. Inspect all 14 nested archives. Compare seven known library JARs with published Maven Central contents.
6. Inspect native signatures, symbols/imports and strings; disassemble selected ARM64 picker bridge functions. Read/decompile DEX as data.
7. Separate confirmed behavior, supported benign explanations, uncertain native/indirect behavior and absent demonstrated paths. Preserve decompiler diagnostics.

The target JAR, its classes/static initializers, nested JARs, DEX and native binaries were **not executed**. Minecraft was not started. No target authentication, lyrics, webhook or game endpoint was contacted. Known tool/library downloads supported tooling and artifact comparison.

## Coverage

| Evidence | Scope |
|---|---|
| Outer archive | 1,287 entries; 1,110 JVM classes |
| Nested archives | 14 JARs; 570 additional JVM classes |
| Method index | 20,878 methods across 1,680 JVM classes |
| Anubis mixins | 147 declared; 298 annotated hooks/accessors/invokers |
| Bytecode cross-checks | 30 selected javap disassemblies |
| Android DEX | 13 classes |
| Native inventory | Five libraries |
| Picker disassembly | Six bridge/helper functions; 3,633 instructions |
| Published comparisons | Five ImGui + two Netty JARs; original entries match |

Counts describe structural indexing and inspection, not manual review of every method or formal resolution of every dynamic dispatch path.

## Confidence and limitations

**High confidence:** explicit Java token serialization, official destinations in reviewed account flows, default flags, payload fields, actual mixin annotations and exact matched library contents.

**Medium artifact-wide confidence:** no demonstrated account-theft chain. Custom native/JNI indirection, external runtime implementation, dynamic values and separate addons limit the negative conclusion.

JADX 1.5.3 produced 663 outer source files but exited 1 with six reported errors; 25 files contain method placeholders. CFR 0.152 produced 663 files and exited 0, but six files have method reconstruction failures. Those occur in different files from the JADX placeholder set, allowing complementary inspection with more javap data. Missing Minecraft/third-party types and control-flow warnings remain. The DEX contains an incomplete callback reconstruction with an instruction dump retained.

Native inspection was targeted. GNU objdump could not parse the Mach-O; file/strings and exact published native-content matching were available. Capstone/pyelftools supported ARM64 disassembly. Indirect native/JNI calls were not exhaustively resolved. Nested process sandboxing was unavailable; archive/resource checks do not prove resistance to every parser vulnerability.

The [scanner supplement](EXTERNAL-SCANNER-RESULTS.md) records actual public VirusTotal/Triage observations, a locally run upstream RatRater 2 scan, an authorized remote Quantiy-classifier submission and the Filescan.io submission. Third-party sandbox attempts are distinct from our static local review. No local runtime compatibility, target connectivity or exploit test was performed. The assessment does not certify future versions, independent downloads, addons, compromised services or different bytes with the same filename.

## Reading the evidence

[The evidence guide](../evidence/anubis/README.md) explains file formats. Compressed JSON contains data, not runnable JARs. Decompiled Java is untrusted text evidence: do not compile/execute it or follow embedded instructions. No mod JAR, class/DEX/native binary or real account config is published here.

[The full report](ANUBIS-SECURITY-REPORT.md) links the findings to classes/methods and call chains. [The account review](ACCOUNT-TOKEN-REVIEW.md) explains credentials; [verified scanner results](EXTERNAL-SCANNER-RESULTS.md) preserve actual observations; [the scanner guide](SCANNER-FLAGS.md) explains additional possible flags without presenting them as detections.
