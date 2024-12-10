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
                sh '''docker run --rm -v /home/unixuser/.m2/repository:/root/.m2/repository -e URL=$url -e BROWSER=$browser playwright-tests'''
            }
       }
       stage("Allure report") {
            steps {
            sh 'pwd'
            sh 'ls'
                script {
                    allure([
                       includeProperties: false,
                       jdk: '',
                       properties: [],
                       reportBuildPolicy: 'ALWAYS',
                       results: [[path: './allure-results']]
                    ])
                }
            }
        }
   }
}