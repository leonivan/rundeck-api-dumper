pipeline {
    agent {
        node {
            label 'master'
        }
    }
    environment {
        NAME = "Vladimir"
        NICKNAME = "vk"
    }
    parameters {
        string(
                name: 'directory',
                defaultValue:"/home/vktest",
                description: "Where to put the build!")
    }
    stages {
        stage ('checkout') {
            steps {
            git credentialsId: '9de0eba3-649c-4ec6-9e8b-035e3c7788ae', url: 'https://github.com/leonivan/rundeck-api-dumper.git'
            }
        }
        stage('build') {
            steps {
                   sh '''
                   ./gradlew build
                   '''
           }
        }
        stage ('package') {
            steps {
                sh '''
                mkdir docker_source
                cp build/libs/rundeck-api-dumper-0.0.1-SNAPSHOT.jar docker_source/rundeck-api-dumper-0.0.1-SNAPSHOT.jar
                cp Dockerfile docker_source/Dockerfile
                '''
                stash includes:'docker_source/*' , name: 'docker_source'
            }
        }
        stage('Docker-Stage') {
            parallel {
                stage ('build image') {
                    agent {
                        node {
                            label 'dockerclient'
                        }
                    }
                    steps {
                        unstash 'docker_source'
                        sh '''
                        cd docker_source
                        docker build -t rundeck-api .
                        cd /home/user/nexuscli
                        ./nexus-cli image delete -name rundeck-api -tag latest
                        docker tag rundeck-api 172.21.3.220:8083/rundeck-api
                        docker push 172.21.3.220:8083/rundeck-api
                        docker rmi rundeck-api:latest
                        docker rmi 172.21.3.220:8083/rundeck-api
                        '''
                        //deleteDir()
                    }
                 }
            }
        }
    }
    post {
        success {
            archive 'docker_source/*.jar' 
            deleteDir()
        }
        failure {
            sh 'echo "A notification will be sent"'
        }
    }
}
