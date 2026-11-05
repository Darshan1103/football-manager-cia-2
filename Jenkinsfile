
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

        stage('Gitleaks Security Scan') {
            steps {
                bat '''
                    "C:/Users/Darshan/AppData/Local/Programs/DockerDesktop/resources/bin/docker.exe" run --rm -v "%WORKSPACE%:/repo" zricethezav/gitleaks:latest detect --source=/repo --redact
                '''
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
                    usernameVariable: 'DOCKERHUB_USERNAME',
                    passwordVariable: 'DOCKERHUB_TOKEN'
                )]) {
                    powershell '''
                        $ErrorActionPreference = "Stop"

                        $env:DOCKERHUB_TOKEN |
                            & "C:/Users/Darshan/AppData/Local/Programs/DockerDesktop/resources/bin/docker.exe" login --username $env:DOCKERHUB_USERNAME --password-stdin

                        if ($LASTEXITCODE -ne 0) {
                            exit $LASTEXITCODE
                        }
                    '''

                    bat '"C:/Users/Darshan/AppData/Local/Programs/DockerDesktop/resources/bin/docker.exe" push darshan1103/football-manager:1.0'
                }
            }
        }
    }
}
