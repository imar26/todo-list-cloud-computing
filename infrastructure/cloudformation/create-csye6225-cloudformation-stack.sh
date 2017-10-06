#!/bin/bash

echo "getting stack name from command line"

STACK_NAME=$1
echo $STACK_NAME

echo "got stack name"

# Get VPC ID
export VPC_ID=$(aws ec2 describe-vpcs --query "Vpcs[0].VpcId" --output text)

echo $VPC_ID

# Use the VPC ID to get subnet id
export SUBNET_ID=$(aws ec2 describe-subnets --filters "Name=vpc-id, Values=$VPC_ID" --query "Subnets[0].SubnetId" --output text)

echo $SUBNET_ID


aws cloudformation create-stack --stack-name $1 --template-body "{
\"AWSTemplateFormatVersion\": \"2010-09-09\",
  \"Description\": \"Sample CloudFormation Template for CSYE 6225 - Fall 2017\",
  \"Resources\": {
    \"EC2Instance\": {
      \"Type\": \"AWS::EC2::Instance\",
      \"Properties\": {
        \"ImageId\": \"ami-cd0f5cb6\",
        \"InstanceType\": \"t2.micro\",
        \"DisableApiTermination\":\"False\",
        \"SecurityGroupIds\": [
          {
            \"Fn::GetAtt\": [
              \"WebServerSecurityGroup\",
              \"GroupId\"
            ]
          }
        ],
        \"KeyName\": \"csye6225-aws\",
        \"BlockDeviceMappings\":[
           {
            \"DeviceName\":\"/dev/sda1\",
            \"Ebs\":
            {
                \"VolumeSize\":16,
                \"VolumeType\":\"gp2\"
                }
            }
          ],
        \"SubnetId\": \"$SUBNET_ID\"
      }
    },
    \"WebServerSecurityGroup\": {
      \"Type\": \"AWS::EC2::SecurityGroup\",
      \"Properties\": {
        \"GroupName\":\"csye6225-fall2017-$STACK_NAME-webapp\",
        \"GroupDescription\": \"Enable HTTP access via port 80, SSH access via port 22\",
        \"VpcId\": \"$VPC_ID\",
        \"SecurityGroupIngress\": [
          {
            \"IpProtocol\": \"tcp\",
            \"FromPort\": \"80\",
            \"ToPort\": \"80\",
            \"CidrIp\": \"0.0.0.0/0\"
          },
          {
            \"IpProtocol\": \"tcp\",
            \"FromPort\": \"22\",
            \"ToPort\": \"22\",
            \"CidrIp\": \"0.0.0.0/0\"
          }
        ]
      }
    }
  }
}"

echo "Successfully created cloudformation"