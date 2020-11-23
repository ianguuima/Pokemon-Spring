pipeline {
    agent any
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true test'
            }
        }
    }
}