pipeline {
  agent any
  stages {
    stage('Build') {
      agent {
        docker {
          image 'gradle'
        }

      }
      steps {
        sh ' ./gradlew build'
      }
    }
  }
}