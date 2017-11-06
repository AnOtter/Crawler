pipeline {
  agent any
  stages {
    stage('compile') {
      steps {
        git(url: 'https://github.com/otter2017/crawler', branch: 'master', poll: true)
      }
    }
  }
}