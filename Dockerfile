FROM mcr.microsoft.com/playwright/java:v1.49.0-noble

RUN mkdir -p /home/unixuser/ui_tests

WORKDIR /home/unixuser/ui_tests

COPY . /home/unixuser/ui_tests

COPY entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/bin/bash", "/entrypoint.sh"]