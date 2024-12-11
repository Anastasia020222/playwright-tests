FROM mcr.microsoft.com/playwright/java:v1.49.0-noble

USER jenkins

RUN mkdir -p /home/jenkins/workspace/web-tests

WORKDIR /home/jenkins/workspace/web-tests

COPY . /home/jenkins/workspace/web-tests

RUN chmod +x entrypoint.sh

ENTRYPOINT ["/bin/bash", "entrypoint.sh"]