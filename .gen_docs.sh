#!/bin/bash
./gradlew javadoc  --include-build "./Pantry-Logging"
rm -rf docs/
mkdir docs
cp -r build/docs/javadoc/* docs/