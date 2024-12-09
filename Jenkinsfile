pipeline {
   agent {
        docker { image 'mcr.microsoft.com/playwright/java:v1.49.0-noble' } }
   parameters {
        string(name: "url", defaultValue: "https://demoqa.com", trim: true, description: "Введите урл для запуска тестов")
        string(name: "browser", defaultValue: "chrome", description: "Введите тип браузера")
        }
   stages {
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
       stage("Test") {
            steps {
                echo "Test stage."
            }
       }
   }
}