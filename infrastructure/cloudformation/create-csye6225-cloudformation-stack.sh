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

#Get hosted zone id

echo "Get hosted zone id"

HOSTED_ZONE_ID=$(aws route53 list-hosted-zones --query HostedZones[0].Id --output text)
ZONE_ID=${HOSTED_ZONE_ID:12}
echo $ZONE_ID

echo "Got the domain name"

#Get Domain Name

echo "Getting the domain name"

DOMAIN_NAME=$(aws route53 list-hosted-zones --query HostedZones[0].Name --output text)
echo $DOMAIN_NAME

echo "Got the domain name"


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
        \"GroupDescription\": \"Enable HTTP access via port 80, SSH access via port 22, HTTPS access via port 443\",
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
          },
          {
            \"IpProtocol\": \"tcp\",
            \"FromPort\": \"443\",
            \"ToPort\": \"443\",
             \"CidrIp\": \"0.0.0.0/0\"
          }
        ]
      }
    },
    \"MyDNSRecord\": {
    \"Type\":\"AWS::Route53::RecordSet\",
    \"Properties\" : {
    \"Comment\" : \"DNS name for my instance.\",
    \"Name\" : \"ec2.$DOMAIN_NAME\",
    \"HostedZoneId\" : \"$ZONE_ID\",
    \"Type\" : \"A\",
    \"TTL\" : \"60\",
    \"ResourceRecords\" :
        [
            {
             \"Fn::GetAtt\" :
                [
                \"EC2Instance\", \"PublicIp\"
                 ]
             }
        ]
       }
    }
  }
}"

echo "Successfully created cloudformation"