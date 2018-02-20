#!/bin/bash
./gradlew javadoc  --include-build "./Pantry-Logging"
rm -rf docs/
cp build/docs/javadoc/* docs/
