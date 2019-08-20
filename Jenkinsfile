pipeline {
  agent any
  stages {
    stage('Build') {
      agent {
        docker {
          image 'gradle:jdk8'
        }
      }
      steps {
        sh ' gradlew build'
      }
    }
  }
}