#!/bin/bash
VERSIONS=$(./mvnw exec:exec -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive -q | sed 's/\"//g' | sed 's/version=//g')
for v in $VERSIONS; do
  VERSION=$v
done
echo "$VERSION"
