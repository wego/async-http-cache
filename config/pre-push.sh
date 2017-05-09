#!/bin/bash

# Returns 0 if none of the gradle checkstyle fail.

# Quietly perform tests. On fail, this will print info about failed checkstyle.
./gradlew -q clean check -x test

# If ./gradlew returned anything other than 0, the checkstyle failed...
if [ $? -ne 0 ] ; then
    echo "Aborting push due to failed checkstyle."
    exit 1
fi
