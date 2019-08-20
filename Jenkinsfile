pipeline {
  agent any
  stages {
    stage('Build') {
      agent {
        docker {
          image 'gradle:jdk10'
        }
      }
      steps {
        sh ' gradlew build'
      }
    }
  }
}