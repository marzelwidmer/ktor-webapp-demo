pipeline {
  agent any
  environment {
      JAVA_OPTION = '-Djsse.enableSNIExtension=false'
  }
  stages {
    stage('Build') {
      steps {
        sh ' ./gradlew build'
      }
    }
  }
}