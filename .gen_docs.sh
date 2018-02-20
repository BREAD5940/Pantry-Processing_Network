#!/bin/bash
git checkout master
./gradlew javadoc  --include-build "./Pantry-Logging"
rm -rf docs/
mkdir docs
cp -r build/docs/javadoc/* docs/
git add docs/
git commit -m "I generated the docs! -The Oven"
git push https://${GH_TOKEN}@github.com/BREAD5940/Pantry-Processing_Network.git
