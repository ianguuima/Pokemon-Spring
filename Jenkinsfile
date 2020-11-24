pipeline {
    agent any
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Test Version') {
            withMaven {
                sh 'mvn -v'
            }
        }
        stage('Build') {
            withMaven {
                sh 'mvn -Dmaven.test.failure.ignore=true install'
            }
        }
        stage('Test') {
            withMaven {
                sh 'mvn -Dmaven.test.failure.ignore=true test'
            }
        }
    }
}