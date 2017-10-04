#!/bin/bash  
# Get VPC ID
export VPC_ID=$(aws ec2 describe-vpcs --query "Vpcs[0].VpcId" --output text)

echo $VPC_ID

# Use the VPC ID to get subnet id
export SUBNET_ID=$(aws ec2 describe-subnets --filters "Name=vpc-id, Values=$VPC_ID" --query "Subnets[0].SubnetId" --output text)

echo $SUBNET_ID

#Create Security Group
echo "creating security group"

SECURITY_GROUP=$(aws ec2 create-security-group --group-name my-sg --description "My security group" --vpc-id $VPC_ID --output text)

echo $SECURITY_GROUP

echo "security group created"

#Configure Security Group
echo "configure security group"
echo "configure tcp"

aws ec2 authorize-security-group-ingress --group-name my-sg --protocol tcp --port 22 --cidr 0.0.0.0/0

echo "tcp done"

echo "configure http"

aws ec2 authorize-security-group-ingress --group-name my-sg --protocol tcp --port 80 --cidr 0.0.0.0/0

echo "http done"

echo "configure https"

aws ec2 authorize-security-group-ingress --group-name my-sg --protocol tcp --port 443 --cidr 0.0.0.0/0

echo "https done"

echo "security group configured"

#Launch EC2 Instance
echo "Launching EC2 Instance"

aws ec2 run-instances --image-id ami-cd0f5cb6 --count 1 --instance-type t2.micro --key-name csye6225-aws --security-group-ids $SECURITY_GROUP --subnet-id $SUBNET_ID

echo "EC2 Instance created"


