pipeline {
    agent any
    
    tools {
        maven 'Maven3'   // Name configured under "Global Tool Configuration"
        jdk 'JavaHome'      // Name configured under "Global Tool Configuration"
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Adityajadhav003/EventManagement.git'
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        
        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
    }
    
    post {
        always {
            echo "Pipeline completed!"
        }
    }
}
