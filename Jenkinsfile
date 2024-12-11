pipeline {
   agent { label 'maven-slave' }
   parameters {
        string(name: "url", defaultValue: "https://demoqa.com", trim: true, description: "Введите урл для запуска тестов")
        string(name: "browser", defaultValue: "chrome", trim: true, description: "Введите тип браузера")
        }
   options {
        ansiColor('xterm')
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
                sh '''docker run --rm -v /home/unixuser/.m2/repository:/home/jenkins/.m2/repository -v web-allure:/home/jenkins/workspace/web-tests/allure-results -e URL=$url -e BROWSER=$browser playwright-tests'''
            }
       }
       stage("Allure report") {
            steps {
            sh 'pwd'
            sh("whoami")
            sh("mkdir ./allure-results")
//             sh("ls -l /home/jenkins/workspace/web-tests")
             sh("cp -r /home/jenkins/allure-results/* ./allure-results/")
//             sh("ls -l /home/jenkins/workspace/web-tests/allure-results")
            sh("ls -al /home/jenkins/allure-results")
                script {
                    allure([
                       includeProperties: false,
                       jdk: '',
                       properties: [],
                       reportBuildPolicy: 'ALWAYS',
                       results: [[path: '/home/jenkins/allure-results']]
                    ])
                }
            }
            stage("Delete resources") {
                steps {
                    sh("whoami")
                    sh("ls -la /home/jenkins/allure-results")
                    sh("rm -rf /home/jenkins/allure-results/")
                 }
            }
        }
   }
}