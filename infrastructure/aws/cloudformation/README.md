# CSYE 6225 - FALL 2017

# Team Members

Aadesh Randeria   001224139  randeria.a@husky.neu.edu

Bhumika Khatri   001284560  khatri.bh@husky.neu.edu

Siddhant Chandiwal 001286480  chandiwal.s@husky.neu.edu

Yashodhan Prabhune 001220710  prabhune.y@husky.neu.edu


# Steps to run the cloud formation scripts

## Run create-policy-role.sh

Go to your root folder of the project - /csye6225-fall2017

Go to the scripts folder: cd infrastructure/aws/cloudformation/

Run Create Cloud Formation Stack: ./create-policy-role.sh {StackName1}

This stack creates a stack which contains all the policies and role that are needed to deploy the application.

## Run create-csye6225-cloudformation-stack.sh

Go to your root folder of the project - /csye6225-fall2017

Go to the scripts folder: cd infrastructure/aws/cloudformation/

Run Create Cloud Formation Stack: ./create-csye6225-cloudformation-stack.sh {StackName}

## Run delete-csye6225-cloudformation-stack.sh

Go to your root folder of the project - /csye6225-fall2017

Go to the scripts folder: cd infrastructure/aws/cloudformation/

Run Delete Cloud Formation Stack: ./delete-csye6225-cloudformation-stack.sh {BucketName} {StackName}

##Install Java on EC2 instance

Ssh to the EC2 instance: ssh ubuntu@{Public IP of EC2 Instance}

Commands to run: sudo apt-get update 
                 sudo apt-get install openjdk-8-jdk -y
                 
##Install and start Tomcat8 on EC2 instance

Ssh to the EC2 instance: ssh ubuntu@{Public IP of EC2 Instance}

Commands to run: sudo apt-get install tomcat8 -y
                 sudo service tomcat8 start

## Install codedeploy agent on EC2 instance

Ssh to the EC2 instance: ssh ubuntu@{Public IP of EC2 Instance}

Install Ruby: sudo apt-get install ruby -y

Install wget: sudo apt-get install wget

Navigate to home directory of ubuntu in EC2 instance: cd /home/ubuntu

Install and start codedeploy: wget https://aws-codedeploy-us-east-1.s3.amazonaws.com/latest/install
                              chmod +x ./install
                              sudo ./install auto
                              sudo service codedeploy-agent start

## Install MySQL on EC2 instance
Ssh to the EC2 instance: ssh ubuntu@{Public IP of EC2 Instance}

Commands to run: sudo apt-get install mysql-server
                 mysql -h {endpoint of rds instance} -P {port number} -u {database user with access to rds} -p
                 
                 
## Check if Java is installed on EC2 instance

Ssh to the EC2 instance: ssh ubuntu@{Public IP of EC2 Instance}

Command to check if Java is installed: java -version

##Check if Tomcat is installed on EC2 instance

sudo service tomcat8 status

##Check if codedeploy in installed on EC2 instance

sudo service codedeploy-agent status


