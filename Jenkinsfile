pipeline {
   agent any
   parameters {
        string(name: "base.url", defaultValue: "https://demoqa.com", trim: true, description: "Введите урл для запуска тестов")
        string(name: "browser.type", defaultValue: "chrome", description: "Введите тип браузера")
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
                       BROWSER = "${params.'browser.type'}"
                   }
            steps {
                echo 'Running Playwright tests...'
                echo "урл $BASE_URL"
                echo "браузер $BROWSER"
                script {
                sh 'pwd'
                   sh 'docker -v'
                   sh 'java -version'
                   sh '''
                      docker run --rm \
                      -v $(pwd):/home/unixuser/ui_tests \
                      -w /home/unixuser/ui_tests \
                      mcr.microsoft.com/playwright/java:v1.49.0-noble \
                      mvn clean test -Dbrowser.type=$BROWSER -Dbase.url=$BASE_URL
                      '''
                }
            }
       }
   }
}