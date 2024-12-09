FROM maven:3.9.0

RUN mkdir -p /home/unixuser/mobile_tests

WORKDIR /home/unixuser/mobile_tests

COPY . /home/unixuser/mobile_tests

ENTRYPOINT ["/bin/bash", "entrypoint.sh"]