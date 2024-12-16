pipeline {
    agent { label 'maven-slave' }
    parameters {
        string(name: "url", defaultValue: "https://demoqa.com", trim: true, description: "Введите урл для запуска тестов")
        string(name: "browser", defaultValue: "chrome", trim: true, description: "Введите тип браузера")
        string(name: 'branch', defaultValue: 'main', description: 'Укажите ветку, из которой нужно взять изменения')
        string(name: "threads", defaultValue: "2", description: "Количество потоков")
    }
    stages {
        stage('Display User') {
            steps {
            sh "$branch"
                script {
                    wrap([$class: 'BuildUser']) {
                    currentBuild.description = """
                        User: ${env.BUILD_USER}
                        User email: ${env.BUILD_USER_EMAIL}
                        Branch: $branch
                    """
                    }
                }
            }
        }
        stage("Checkout") {
            steps {
                sh "env"
                checkout([$class: 'GitSCM',
                    branches: [[name: "*/${params.branch}"]],
                ])
            }
        }
        stage("Build images playwright-tests") {
            steps {
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
                    -e URL=$url -e BROWSER=$browser -e THREADS=$threads \
                    playwright-tests
                '''
            }
        }
    }
    post {
        always {
            script {
                echo "Publication of the report"
                preparationReportAllure()
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

def preparationReportAllure() {
    sh("mkdir -p ./allure-results")
    sh "echo URL=$url > ./allure-results/environment.properties"
    sh "echo BROWSER=$browser >> ./allure-results/environment.properties"
    sh("cp -r /home/jenkins/allure-results/* ./allure-results/")
}