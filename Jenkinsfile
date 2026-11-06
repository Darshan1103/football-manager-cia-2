
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

                stage('Verify Docker Credential') {
            steps {
                withCredentials([string(
                    credentialsId: 'dockerhub-pat-new',
                    variable: 'DOCKERHUB_TOKEN'
                )]) {
                    powershell '''
                        $token = $env:DOCKERHUB_TOKEN.Trim()
                        $bytes = [System.Text.Encoding]::UTF8.GetBytes($token)
                        $sha = [System.Security.Cryptography.SHA256]::Create()
                        $hash = $sha.ComputeHash($bytes)
                        $fingerprint = [BitConverter]::ToString($hash).Replace("-", "")
                        Write-Host "Jenkins credential fingerprint: $fingerprint"
                    '''
                }
            }
        }


        stage('Docker Hub Push') {
            steps {
                withCredentials([string(
                    credentialsId: 'dockerhub-pat-new',
                    variable: 'DOCKERHUB_TOKEN'
                )]) {
                    powershell '''
                        $docker = "C:/Users/Darshan/AppData/Local/Programs/DockerDesktop/resources/bin/docker.exe"

                        $dockerConfig = Join-Path $env:WORKSPACE ".docker-config-test"

                        New-Item -ItemType Directory -Force -Path $dockerConfig | Out-Null

                        $token = $env:DOCKERHUB_TOKEN

                        Write-Host "Token length: $($token.Length)"
                        Write-Host "Token starts with whitespace: $($token -match '^\\s')"
                        Write-Host "Token ends with whitespace: $($token -match '\\s$')"

                        $token = $token.Trim()

                        $token | & $docker --config $dockerConfig login -u darshan1103 --password-stdin

                        if ($LASTEXITCODE -ne 0) {
                            throw "Docker Hub authentication failed"
                        }

                        Write-Host "Docker Hub authentication successful"

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
