
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
                bat '"C:/Users/Darshan/AppData/Local/Programs/DockerDesktop/resources/bin/docker.exe" build -t darshan1103/football-manager:1.0 .'
            }
        }

        stage('Docker Hub Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'darshan',
                    passwordVariable: 'PAssword'
                )]) {
                    bat '''
                        @echo off
                        echo %DOCKERHUB_TOKEN% | "C:/Users/Darshan/AppData/Local/Programs/DockerDesktop/resources/bin/docker.exe" login -u "%DOCKERHUB_USERNAME%" --password-stdin
                        "C:/Users/Darshan/AppData/Local/Programs/DockerDesktop/resources/bin/docker.exe" push darshan1103/football-manager:1.0
                    '''
                }
            }
        }
    }
}
