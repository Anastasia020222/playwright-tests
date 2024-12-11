pipeline {
    agent { label 'maven-slave' }
    parameters {
        string(name: "url", defaultValue: "https://demoqa.com", trim: true, description: "Введите урл для запуска тестов")
        string(name: "browser", defaultValue: "chrome", trim: true, description: "Введите тип браузера")
    }
    stages {
        stage("Checkout") {
            steps {
                checkout scm
            }
        }
        stage("Build images playwright-tests") {
            steps {
                sh 'docker build -t playwright-tests .'
            }
        }
        try {
            stage("Running Playwright tests") {
                steps {
                    sh '''docker run --rm -v /home/unixuser/.m2/repository:/home/jenkins/.m2/repository -v web-allure:/home/jenkins/workspace/web-tests/allure-results -e URL=$url -e BROWSER=$browser playwright-tests'''
                }
            }
       }
       finally {
            stage("Allure report") {
                steps {
                    sh("mkdir ./allure-results")
                    sh("cp -r /home/jenkins/allure-results/* ./allure-results/")
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
            }
        }
        stage("Delete resources") {
            steps {
                sh("rm -rf /home/jenkins/allure-results/*")
            }
        }
    }
}