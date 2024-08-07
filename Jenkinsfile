pipeline{

    agent any
    parameters{
        choice(name: 'action', choices: ['create', 'destroy'], description: 'Choose Create/ Destroy?')
            string(
                name: 'AWS_ACCOUNT_ID', 
                description: " AWS Account ID", 
                defaultValue: '471112935543')
            string(
                name: 'REGION',
                description: "Region of ECR", 
                defaultValue: 'us-east-1')
             string(
                name: 'ECR_REPOSITORY', 
                description: "name of the ECR", 
                defaultValue: 'demo_app')
    }
    environment{
        // ACCESS_KEY = credentials('AWS_ACCESS_KEY_ID')
        // SECRET_KEY = credentials('AWS_SECRET_KEY_ID')
        // AWS_ACCOUNT_ID = { params.awsAccountId }
        // REGION = { params.Region }
        // ECR_REPOSITORY = { params.EcrRepoName }
        ecrUrl = "${AWS_ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com"
    }
    stages{
        stage('Git CheckOut'){
            when { expression { params.action == 'create' } }
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/muskanchaurasia24/EKS_Jenkins_deployment.git']])
            }
        }
        stage('Unit Test Maven'){
            when { expression { params.action == 'create' } }
            steps{
                script{
                    sh 'mvn -f Java_app/pom.xml test'
                }
            }
        }
        stage('Static Code Analysis: SonarQube'){
            when{ expression { params.action == 'create' } }
            steps{
                script{
                    withSonarQubeEnv("sonarqube-api"){
                    sh 'mvn -f Java_app/pom.xml clean verify sonar:sonar -Dsonar.coverage.jacoco.xmlReportPaths=target/jacoco/jacoco.xml'
                    }
                }
            }
        }
        stage('Quality Gate Status check : Sonarqube'){
            when { expression { params.action == 'create' } }
            steps{
                script{
                    // withCredentials([string(credentialsId: 'jenkins-sonar-token', variable: 'sonar-token')])
                    waitForQualityGate abortPipeline: true
                }
            }
            post {
                failure {
                    emailext (
                        to: 'muskan.chaurasia@nagarro.com',
                        subject: "Jenkins: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER} - Quality Gate Failed",
                        body: """
                        <p>Hi Team,</p>
                        <p>The Jenkins build <b>${env.JOB_NAME}</b> with build number <b>${env.BUILD_NUMBER}</b> has failed due to the Quality Gate not passing.</p>
                        <p>Please review the SonarQube results and address any issues.</p>
                        <p>Regards,<br>DevOps Team</p>
                        """,
                        mimeType: 'text/html'
                    )
                    error("Pipeline aborted due to failing Quality Gate")
                }
            }
        }
        stage("Upload artifacts to jfrog artifactory")
        {
            steps
            {
                rtUpload(
                    serverId: 'artifactory-server',
                    spec:'''{
                        "files":[
                        {
                            "pattern": "*.war",
                            "target": "demo-project-local/"
                        }
                    ]
                    }''',
                    )
                    rtPublishBuildInfo( serverId:"artifactory-server" )
                    
            }
        }
        stage("Maven Build: maven"){
            when { expression { params.action == 'create' } }
            steps{
                script{
                    sh 'mvn -f Java_app/pom.xml clean install'
                }
            }
        }  
        stage('Docker Image Build: ECR'){
            when { expression { params.action == 'create' } }
            steps{
                script{
                    
                    sh """
                      docker build -t ${ECR_REPOSITORY} .
                      docker tag ${ECR_REPOSITORY}:latest ${ecrUrl}/${ECR_REPOSITORY}:latest                      
                    """
                }
            }
        }
        stage('Docker Image Push : ECR'){
            when { expression { params.action == 'create' } }
            steps{
                script{
                    withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'aws-credentials', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')]) {
                    sh """
                     
                      /usr/local/bin/aws ecr get-login-password --region ${REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com
                      
                      docker push ${ecrUrl}/${ECR_REPOSITORY}:latest
                    """
                }
                }
            }
        }

    }
}
