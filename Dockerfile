FROM playwright/java:v1.49.0-noble

RUN mkdir -p /home/jenkins/workspace/web-tests

WORKDIR /home/jenkins/workspace/web-tests

COPY . /home/jenkins/workspace/web-tests

ENTRYPOINT ["/bin/bash", "entrypoint.sh"]