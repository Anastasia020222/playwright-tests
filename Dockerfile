FROM mcr.microsoft.com/playwright/java:v1.49.0-noble

RUN groupadd -g 1000 jenkins && \
    useradd -m -u 1000 -g 1000 -s /bin/bash jenkins

USER jenkins

RUN mkdir -p /home/jenkins/workspace/web-tests

WORKDIR /home/jenkins/workspace/web-tests

COPY . /home/jenkins/workspace/web-tests

RUN chmod +x entrypoint.sh

ENTRYPOINT ["/bin/bash", "entrypoint.sh"]