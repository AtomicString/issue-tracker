pipeline {
  agent any

  environment {
    POSTGRES_USER = credentials('POSTGRES_USER')
    POSTGRES_PASSWORD = credentials('POSTGRES_PASSWORD')
    POSTGRES_DB = credentials('POSTGRES_DB')
    DOCKER_BUILDKIT = '1'
  }

  stages {
    stage('Checkout') {
      when { branch 'deploy' }
      steps {
        checkout scm
      }
    }

    stage('Maven Package') {
      steps {
        dir('tracker') {
          sh 'mvn clean package'
        }
      }
    }

    stage('Docker Build') {
      steps {
        sh 'docker compose build --no-cache'
      }
    }

    stage('Deploy') {
      steps {
        // Kill old containers cleanly if needed
        sh 'docker compose down || true'
        // Start in detached mode
        sh 'docker compose up -d'
      }
    }

    stage('Verify') {
      steps {
        // Sanity check: is server responding?
        sh 'curl --fail http://localhost:8000 || echo "server not responding"'
      }
    }
  }
}

