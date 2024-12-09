pipeline {
   agent any
   parameters {
        string(name: "base.url", defaultValue: "https://demoqa.com", trim: true, description: "Введите урл для запуска тестов")
        string(name: "browser", defaultValue: "chrome", description: "Введите тип браузера")
        }
   stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
       stage("Run test") {
            environment {
                       BASE_URL = "${params.'base.url'}"
                       BROWSER = "${params.browser}"
                   }
            steps {
                echo 'Running Playwright tests...'
                script {
                   sh '''
                      docker run --rm \
                      -v $(pwd):/home/unixuser/ui_tests \
                      -w /home/unixuser/ui_tests \
                      mcr.microsoft.com/playwright/java:v1.49.0-noble \
                      mvn clean test -Dbase.url=$BASE_URL -Dbrowser=$BROWSER
                      '''
                }
            }
       }
   }
}