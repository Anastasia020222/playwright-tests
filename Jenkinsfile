pipeline {
   agent { label 'maven-slave' }
   parameters {
        string(name: "base.url", defaultValue: "https://demoqa.com", trim: true, description: "Введите урл для запуска тестов")
        string(name: "browser", defaultValue: "chrome", description: "Введите тип браузера")
        }
        environment {
             BASE_URL = "${params.'base.url'}"
             BROWSER = "${params.'browser'}"
             }
   stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
       stage("Run test") {

            steps {
                echo 'Running Playwright tests...'
                echo "урл $BASE_URL"
                echo "браузер $BROWSER"
                script {
                sh 'pwd'
                  sh
                   sh 'docker -v'
                   sh 'java -version'
                   sh '''
                      docker run --rm \
                      -v $(pwd):/home/unixuser/ui_tests \
                      -w /home/unixuser/ui_tests \
                      mcr.microsoft.com/playwright/java:v1.49.0-noble \
                      mvn clean test -Dbrowser=env.BROWSER -Dbase.url=$BASE_URL
                      '''
                }
            }
       }
   }
}