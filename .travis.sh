#!/bin/bash

if [ ${TRAVIS_PULL_REQUEST} = 'false' ] && [ ${TRAVIS_BRANCH} = 'master' ]; then
  ./gradlew -Psonatype.username="${SONATYPE_USERNAME}" -Psonatype.password="${SONATYPE_PASSWORD}" build publish
else
  ./gradlew build
fi
