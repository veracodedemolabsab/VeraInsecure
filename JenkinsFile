pipeline {
    agent any
    stages {
        stage("Checkout") {
            steps{
                git url: 'https://github.com/m-browntown/VeraInsecure.git'
            }
        }

        //SourceClear does a scan of open source libraries used by the app and provides feedback for vulnerabilities 
        stage("Veracode SrcClr Scan") {
            steps {
                script {
                    if(isUnix()) {
                        withCredentials([string(credentialsId: 'SRCCLR_API_TOKEN', variable: 'SRCCLR_API_TOKEN')]) {
                            sh label: '', script: 'curl https://download.sourceclear.com/ci.sh | sh'
                        }
                    } else {
                        withCredentials([string(credentialsId: 'SRCCLR_API_TOKEN', variable: 'SRCCLR_API_TOKEN')]) {
                            powershell label: '', script: 'Set-ExecutionPolicy AllSigned -Scope Process -Force;'
                            powershell label: '', script: 'iex ((New-Object System.Net.WebClient).DownloadString("https://download.srcclr.com/ci.ps1")); srcclr scan'
                        }
                    }
                } 
            }
        }
        
        stage("Build") {
            steps {
                script {
                    if(isUnix()) {
                        withMaven(maven: 'Maven') {
                            sh label: '', script: 'mvn clean package'
                        }
                    } else {
                        withMaven(maven: 'Maven') {
                            bat label: '', script: 'mvn clean package'
                        }
                    }
                }
            }   
        }
        
        stage("Veracode Pipeline Scan") {
            steps {
                withCredentials([usernamePassword(credentialsId: 'veracode-credentials', passwordVariable: 'veracode_key', usernameVariable: 'veracode_id')]) {
                    sh label: '', script: '''curl -O https://downloads.veracode.com/securityscan/pipeline-scan-LATEST.zip'''
                    sh label: '', script: '''unzip pipeline-scan-LATEST.zip -d pipeline-scan-LATEST'''
                    sh label: '', script: 'java -jar "pipeline-scan-LATEST/pipeline-scan.jar" -vid $veracode_id -vkey $veracode_key --fail_on_severity "Very High" -f "target/verainsecure.war"'
                }
            }
        }
        
        stage("Veracode Static Analysis") {
            steps {
                withCredentials([usernamePassword(credentialsId: 'veracode-credentials', passwordVariable: 'veracode_key', usernameVariable: 'veracode_id')]) {
                    veracode applicationName: 'VeraInsecure', criticality: 'Medium', debug: true, scanName: 'Jenkins - Build $buildnumber', uploadIncludesPattern: '**/**.war', useIDkey: true, vid: env.veracode_id, vkey: env.veracode_key
                }
            }
        }
    }
}
