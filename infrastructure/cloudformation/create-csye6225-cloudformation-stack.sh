#!/bin/bash
# Get VPC ID
export VPC_ID=$(aws ec2 describe-vpcs --query "Vpcs[0].VpcId" --output text)

echo $VPC_ID

# Use the VPC ID to get subnet id
export SUBNET_ID=$(aws ec2 describe-subnets --filters "Name=vpc-id, Values=$VPC_ID" --query "Subnets[0].SubnetId" --output text)

echo $SUBNET_ID

aws cloudformation create-stack --stack-name $1 --template-body file://simple-ec2-instance-securitygroup-cloudformation-stack.json --parameters '[{"parameter_key":"SubnetId","paramter_value":$SUBNET_ID}]'S