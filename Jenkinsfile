#!groovy

// Info https://jenkins.io/doc/book/pipeline/syntax/

pipeline {
  agent {
    docker {
      image 'maven:3.5.0-jdk-8'
    }
  }

  stages {

    stage('version info') {
      steps {
        sh 'java -version'
        sh 'mvn --version'
      }
    }

    stage('build') {
      steps {
        sh 'mvn clean install'
      }
    }

  }

  post {
    always {
      junit '**/target/**/*.xml'
    }
  }

}
