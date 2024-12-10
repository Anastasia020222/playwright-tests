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
                sh 'pwd'
                //sh("rm -rf /web-allure/*")
                sh 'ls'
                sh 'docker build -t playwright-tests .'
                sh '''docker run --rm -v /home/unixuser/.m2/repository:/root/.m2/repository -v web-allure:/home/jenkins/workspace/web-tests/allure-results -e URL=$url -e BROWSER=$browser playwright-tests'''
            }
       }
       stage("Allure report") {
            steps {
            sh 'pwd'
            sh("mkdir ./allure-results")
            sh("ls -l /home/jenkins/workspace/web-tests")
            sh("cp -r /home/jenkins/allure-results ./allure-results")
            sh("ls -l /home/jenkins/workspace/web-tests/allure-results")
            sh("ls /home/jenkins/allure-results")
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