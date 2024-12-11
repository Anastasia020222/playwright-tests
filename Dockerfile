FROM mcr.microsoft.com/playwright/java:v1.49.0-noble

# Создаем группу и пользователя jenkins с UID 1002 и GID 1002
RUN groupadd -g 1002 jenkins && \
    useradd -m -u 1002 -g 1002 -s /bin/bash jenkins

# Копируем файлы в контейнер
COPY . /home/jenkins/workspace/web-tests

# Меняем владельца директорий и файлов на пользователя jenkins
RUN chown -R jenkins:jenkins /home/jenkins/workspace/web-tests

# Даем права на запись, чтение и удаление для пользователя jenkins
RUN chmod -R 775 /home/jenkins/workspace/web-tests

# Меняем владельца и даем права на кеш Maven
RUN chown -R jenkins:jenkins /home/jenkins/.m2
RUN chmod -R 775 /home/jenkins/.m2

# Меняем владельца для Allure-результатов
RUN chown -R jenkins:jenkins /home/jenkins/workspace/web-tests/allure-results
RUN chmod -R 775 /home/jenkins/workspace/web-tests/allure-results

# Переключаемся на пользователя jenkins
USER jenkins

WORKDIR /home/jenkins/workspace/web-tests

RUN chmod +x entrypoint.sh

ENTRYPOINT ["/bin/bash", "entrypoint.sh"]
