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
        echo 'Enviando .war para a fin-family-backend-01'
        sh 'scp -o StrictHostKeyChecking=no  -i /var/lib/jenkins/keys/fin-family-backend-new.pem FinFamily/build/outputs/apk/release/FinFamily-release.apk ubuntu@34.231.187.221:/tmp/'
        echo 'Realizando deploy do .war na fin-family-01'
        sh 'ssh -o StrictHostKeyChecking=no  -i /var/lib/jenkins/keys/fin-family-backend-new.pem ubuntu@34.231.187.221 \'/home/ubuntu/scripts/apk.sh\''
        echo 'Enviando .war para a fin-family-backend-02'
        sh 'scp -o StrictHostKeyChecking=no  -i /var/lib/jenkins/keys/fin-family-backend-new.pem FinFamily/build/outputs/apk/release/FinFamily-release.apk ubuntu@34.237.168.116:/tmp/'
        echo 'Realizando deploy do .war na fin-family-02'
        sh 'ssh -o StrictHostKeyChecking=no  -i /var/lib/jenkins/keys/fin-family-backend-new.pem ubuntu@34.237.168.116 \'/home/ubuntu/scripts/apk.sh\''
      }
    }

    stage('Send Email') {
      steps {
        emailext(subject: 'finfamilyapp@gmail.com', attachLog: true, compressLog: true, body: 'oi')
      }
    }

  }
}