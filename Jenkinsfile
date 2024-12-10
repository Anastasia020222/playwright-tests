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
        stage("Build images playwright") {
            steps {
            script {
                def images = sh 'docker images -qf reference=playwright-tests'
                echo "images $images"
                }
            }
        }
        stage("Run test") {
            steps {
                echo 'Running Playwright tests...'
                sh 'docker build -t playwright-tests .'
                sh '''docker run --rm -e URL=$url -e BROWSER=$browser playwright-tests'''
            }
       }
   }
}