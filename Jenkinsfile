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
                echo "Build triggered by: ${BUILD_USER}"
                echo "User email: ${BUILD_USER_EMAIL}"
                sh 'docker build -t playwright-tests .'
            }
        }
        stage("Running Playwright tests") {
            steps {
                sh '''
                    docker run --rm \
                    -v /home/unixuser/.m2/repository:/home/jenkins/.m2/repository \
                    -v web-allure:/home/jenkins/workspace/web-tests/allure-results \
                    -v ms-playwright:/ms-playwright \
                    -e URL=$url -e BROWSER=$browser \
                    playwright-tests
                '''
            }
        }
    }
    post {
        always {
            script {
                echo "Publication of the report"
                sh("mkdir -p ./allure-results")
                sh("cp -r /home/jenkins/allure-results/* ./allure-results/")
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: './allure-results']]
                ])
            }
            sh("rm -rf /home/jenkins/allure-results/*")
        }
    }
}