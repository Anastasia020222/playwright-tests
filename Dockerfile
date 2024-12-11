FROM mcr.microsoft.com/playwright/java:v1.49.0-noble

RUN groupadd -g 1001 jenkins && \
    useradd -m -u 1001 -g 1001 -s /bin/bash jenkins

USER jenkins

RUN mkdir -p /home/jenkins/workspace/web-tests

WORKDIR /home/jenkins/workspace/web-tests

COPY . /home/jenkins/workspace/web-tests

RUN chmod +x entrypoint.sh

ENTRYPOINT ["/bin/bash", "entrypoint.sh"]