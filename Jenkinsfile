pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://your-repo-url.git', branch: 'main'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t online-banking-backend:latest .'
            }
        }

        // Optional Deploy stage if you have a server
        // stage('Deploy') {
        //     steps {
        //         // your deploy steps here
        //     }
        // }
    }
}