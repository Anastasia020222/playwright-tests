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
                sh 'pwd'
                sh 'docker -v'
                sh 'java -version'
                sh 'whoami'
                sh 'id'
                sh '''
                docker build -t playwright-tests .
                '''
                sh '''
                   docker run --rm \
                   -e BASE_URL=$BASE_URL \
                   -e BROWSER=$BROWSER \
                   -v $(pwd):/home/unixuser/ui_tests \
                   playwright-tests
                   '''
            }
       }
   }
}