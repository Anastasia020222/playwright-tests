pipeline {
   agent { label 'maven-slave' }
   parameters {
        string(name: "url", defaultValue: "https://demoqa.com", trim: true, description: "Введите урл для запуска тестов")
        string(name: "browser", defaultValue: "chrome", trim: true, description: "Введите тип браузера")
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
                sh 'docker build -t playwright-tests .'
                sh 'ls /home/jenkins/workspace/web-tests'
                sh '''docker run --rm -e BASE_URL=$url -e BROWSER=$browser playwright-tests'''

            }
       }
   }
}