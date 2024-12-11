FROM mcr.microsoft.com/playwright/java:v1.49.0-noble

RUN groupadd -g 1002 jenkins && \
    useradd -m -u 1002 -g 1002 -s /bin/bash jenkins

RUN mkdir -p /home/jenkins/workspace/web-tests

WORKDIR /home/jenkins/workspace/web-tests

COPY . /home/jenkins/workspace/web-tests

RUN chmod +x entrypoint.sh

USER jenkins

ENTRYPOINT ["/bin/bash", "entrypoint.sh"]