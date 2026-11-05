
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
                withCredentials([string(
                    credentialsId: 'dockerhub-pat',
                    variable: 'DOCKERHUB_TOKEN'
                )]) {
                    powershell '''
                        $docker = "C:/Users/Darshan/AppData/Local/Programs/DockerDesktop/resources/bin/docker.exe"
                        $dockerConfig = Join-Path $env:WORKSPACE ".docker-config"

                        New-Item -ItemType Directory -Force -Path $dockerConfig | Out-Null

                        $token = $env:DOCKERHUB_TOKEN

                        if ([string]::IsNullOrWhiteSpace($token)) {
                            throw "Docker Hub token is empty"
                        }

                        $token | & $docker --config $dockerConfig login -u darshan1103 --password-stdin

                        if ($LASTEXITCODE -ne 0) {
                            throw "Docker Hub login failed"
                        }

                        & $docker --config $dockerConfig push darshan1103/football-manager:1.0

                        if ($LASTEXITCODE -ne 0) {
                            throw "Docker Hub push failed"
                        }
                    '''
                }
            }
        }

    }
}
