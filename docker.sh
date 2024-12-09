#!/bin/bash

chmod +x entrypoint.sh

docker run --rm \
  -v $(pwd):/home/unixuser/ui_tests \
  -w /home/unixuser/ui_tests \
  mcr.microsoft.com/playwright/java:v1.49.0-noble \
  bash -c "./entrypoint.sh"