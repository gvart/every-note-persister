#!/bin/sh
set -e

JAVA_VERSION=java17
GRAAL_VERSION=22.3.0
GRADLE_VERSION=7.6

docker build --build-arg GRADLE_VERSION=$GRADLE_VERSION \
  --build-arg JAVA_VERSION=$JAVA_VERSION \
  --build-arg GRAAL_VERSION=$GRAAL_VERSION \
  -t al2-graalvm:gradle .