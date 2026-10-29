
pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                bat 'mvnw.cmd clean package'
            }
        }

        stage('Docker Build') {
            steps {
                bat '"C:/Users/Darshan/AppData/Local/Programs/DockerDesktop/resources/bin/docker.exe" build -t football-manager:1.0 .'
            }
        }
    }
}
