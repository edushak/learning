# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A personal learning project for Java & Groovy fundamentals — file I/O, collections, concurrency, PDF processing, CSV parsing, SQLite, and AI integration via LangChain4J + Google Gemini.

## Build & Test Commands

```bash
# Build
./gradlew build          # Full build with tests
./gradlew assemble       # Build JAR without running tests
./gradlew clean build    # Clean rebuild

# Test
./gradlew test                                                    # All tests
./gradlew test --tests "com.learning.basics.TextAnalyzerSpec"     # Single spec class
./gradlew test --tests "com.learning.basics.TextAnalyzerSpec.testGetLines"  # Single method
./gradlew test --info    # Verbose output
./gradlew test -S        # Full stack traces on failure
```

## Architecture

### Languages & Build
- **Groovy** (primary) and **Java** (secondary), built with Gradle 9.7.0
- Versions pinned in `gradle.properties`: `groovy_ver`, `langchain_ver`

### Source Layout
- `src/main/groovy/com/learning/basics/` — domain models (House, Room, Street, Town, Window) and a custom exception
- `src/main/groovy/com/learning/util/` — utilities: `IOHelper` (file reading), `PdfHelper` (PDFBox text extraction), `LRUCache` (thread-safe, ConcurrentHashMap-based)
- `src/main/groovy/com/learning/concurrent/` — `BulkVirtualThreads.java` (virtual threads experimentation)
- `src/main/groovy/com/learning/loaders/` — CSV loaders for domain objects
- `src/main/java/com/learning/basics/` — `TextAnalyzer.java` with factory/singleton caching pattern, and its interface `ITextAnalyzer`

### Tests
- Primary framework: **Spock** (Groovy BDD, `*Spec.groovy` files) — uses JUnit Platform runner
- Secondary framework: **TestNG** (Java, `*Test.java`) — available but not actively used
- Test resources in `src/test/resources/`: sample text, CSV (`data/`), and a PDF
- `SpockConfig.groovy` configures Spock runner (full stack traces enabled, run order optimized)

### AI Integration
- `GoogleAiGeminiChatModelITSpec` — integration tests for Google Gemini via LangChain4J
- API key loaded from `src/test/resources/.uncommitted` (git-ignored file, must be created locally)
- Tests are skipped when the key file is absent; model is `gemini-3.7-flash`
- `py/test_key.py` — standalone Python script to validate a Gemini API key

### Key Dependencies
| Purpose     | Library                                        |
|-------------|------------------------------------------------|
| Concurrency | GPars 1.2.1                                    |
| AI/LLM      | LangChain4J 1.8.0, Google AI Gemini, Vertex AI |
| PDF         | Apache PDFBox 3.0.6                            |
| CSV         | OpenCSV 5.12.0                                 |
| Database    | SQLite JDBC 3.50.3.0                           |
| Testing     | Spock 2.4-M6, TestNG 7.11.0, AssertJ 3.27.6    |

## AI Integration Setup

To run `GoogleAiGeminiChatModelITSpec`, create `src/test/resources/.uncommitted` containing just the Gemini API key (no newline). Without this file, those tests are automatically skipped via `@IgnoreIf`.
