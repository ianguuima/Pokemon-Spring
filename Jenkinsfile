pipeline {
    tools {
        maven 'M3'
    }
    agent any
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Test Version') {
            steps {
                sh 'mvn -v'
            }
        }
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