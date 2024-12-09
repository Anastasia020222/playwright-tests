#!/bin/bash

mvn clean test -Dremote.url=$REMOTE_URL -Dbase.url=$BASE_URL -Dbrowser=$BROWSER -DversionBrowser=$VERSION_BROWSER