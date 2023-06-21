pipeline{
    agent any
    tools{
        maven "maven"
    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/cristowo/Tingeso_Evaluacion_2']])
                dir("Backend/config-service"){
                        bat "mvn clean install -DskipTests=true"
                }
                dir("Backend/eureka-service"){
                        bat "mvn clean install -DskipTests=true"
                }
                dir("Backend/proveedor-service"){
                        bat "mvn clean install -DskipTests=true"
                }
                dir("Backend/llegada-service"){
                        bat "mvn clean install -DskipTests=true"
                }
                dir("Backend/resultado-service"){
                        bat "mvn clean install -DskipTests=true"
                }
                dir("Backend/pago-service"){
                        bat "mvn clean install -DskipTests=true"
                }
                dir("Backend/gateway-service"){
                        bat "mvn clean install -DskipTests=true"
                }                
            }
        }
        stage("Build Docker Image"){
            steps{
                dir("Backend/config-service"){
                        bat "docker build -t cristowo/tingeso2config:latest ."
                }
                dir("Backend/eureka-service"){
                        bat "docker build -t cristowo/tingeso2eureka:latest ."
                }
                dir("Backend/proveedor-service"){
                        bat "docker build -t cristowo/tingeso2proveedor:latest ."
                }                
                dir("Backend/llegada-service"){
                        bat "docker build -t cristowo/tingeso2llegada:latest ."
                }
                dir("Backend/resultado-service"){
                        bat "docker build -t cristowo/tingeso2resultado:latest ."
                }
                dir("Backend/pago-service"){
                        bat "docker build -t cristowo/tingeso2pago:latest ."
                }
                dir("Backend/gateway-service"){
                        bat "docker build -t cristowo/tingeso2gateway:latest ."
                }
                dir("Frontend"){
                        bat "docker build -t cristowo/tingeso2frontend:latest ."
                }
            }
        }
        stage("Push Docker Image"){
            steps{
                withCredentials([string(credentialsId: 'dockerpass', variable: 'dckpass')]) {
                        bat "docker login -u cristowo -p ${dckpass}"
                        }
                dir("Backend/config-service"){
                        bat "docker push cristowo/tingeso2config"
                }
                dir("Backend/eureka-service"){
                        bat "docker push cristowo/tingeso2eureka"
                }
                dir("Backend/proveedor-service"){
                        bat "docker push cristowo/tingeso2proveedor"
                }
                dir("Backend/llegada-service"){
                        bat "docker push cristowo/tingeso2llegada"
                }
                dir("Backend/resultado-service"){
                        bat "docker push cristowo/tingeso2resultado"
                }
                dir("Backend/pago-service"){
                        bat "docker push cristowo/tingeso2pago"
                }
                dir("Backend/gateway-service"){
                        bat "docker push cristowo/tingeso2gateway"
                }
                dir("Frontend/tingeso2"){
                        bat "docker push cristowo/tingeso2frontend"
                }
            }
        }
    }
    post{
        always{
            dir("Tingeso_Evaluacion_2"){
                bat "docker logout"
            }
        }
    }
}