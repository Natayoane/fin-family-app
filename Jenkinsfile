pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo 'Gerando APK assinado'
        sh './gradlew assembleRelease'
        echo 'Verificando se o APK foi gerado e assinado'
        sh 'ls FinFamily/build/outputs/apk/release'
      }
    }

    stage('Deploy') {
      steps {
        echo 'Enviar apk para os servidores de backend'
      }
    }

  }
}