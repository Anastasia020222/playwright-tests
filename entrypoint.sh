#!/bin/bash

DEBUG=${logs:-"pw:browser,pw:page"}

DEBUG=$DEBUG mvn clean test -Durl=$URL -Dbrowser=$BROWSER