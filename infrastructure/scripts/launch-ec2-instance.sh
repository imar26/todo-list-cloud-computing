#!/bin/bash
# Get VPC ID
export VPC_ID=$(aws ec2 describe-vpcs --query "Vpcs[0].VpcId" --output text)

echo $VPC_ID

# Use the VPC ID to get subnet id
export SUBNET_ID=$(aws ec2 describe-subnets --filters "Name=vpc-id, Values=$VPC_ID" --query "Subnets[0].SubnetId" --output text)

echo $SUBNET_ID

#Create Security Group
echo "creating security group"

SECURITY_GROUP=$(aws ec2 create-security-group --group-name csye6225-fall2017-webapp --description "My security group" --vpc-id $VPC_ID --output text)

echo $SECURITY_GROUP

echo "security group created"

#Configure Security Group
echo "configure security group"
echo "configure tcp"

aws ec2 authorize-security-group-ingress --group-name csye6225-fall2017-webapp --protocol tcp --port 22 --cidr 0.0.0.0/0

echo "tcp done"

echo "configure http"

aws ec2 authorize-security-group-ingress --group-name csye6225-fall2017-webapp --protocol tcp --port 80 --cidr 0.0.0.0/0

echo "http done"

echo "configure https"

aws ec2 authorize-security-group-ingress --group-name csye6225-fall2017-webapp --protocol tcp --port 443 --cidr 0.0.0.0/0

echo "https done"

echo "security group configured"

#Launch EC2 Instance
echo "Launching EC2 Instance"

INSTANCE_ID=$(aws ec2 run-instances --image-id ami-cd0f5cb6 --count 1 --instance-type t2.micro --key-name csye6225-aws --security-group-ids $SECURITY_GROUP --subnet-id $SUBNET_ID --disable-api-termination --block-device-mappings "[ { \"DeviceName\": \"/dev/sda1\", \"Ebs\": { \"VolumeSize\": 16, \"VolumeType\": \"gp2\" } } ]" --query "Instances[0].InstanceId" --output text)
echo $INSTANCE_ID

echo "EC2 Instance created"

#Wait for instance to be in running state

echo "Wait for instance to be in running state"

aws ec2 wait instance-running --instance-ids $INSTANCE_ID

echo "Instance is in running state"

#Retrieving instance’s public IP address

echo "Retrieving instance’s public IP address"

IP_ADDRESS=$(aws ec2 describe-instances --instance-ids $INSTANCE_ID --query "Reservations[*].Instances[*].[PublicIpAddress]" --output text)
echo $IP_ADDRESS

echo "Retrieved IP Address"

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

#Add/Update type A resource record set

echo "Add/Update type A resource record set"

aws route53 change-resource-record-sets --hosted-zone-id $ZONE_ID --change-batch "{
  \"Comment\": \"Upsert record set\",
  \"Changes\": [
    {
      \"Action\": \"UPSERT\",
      \"ResourceRecordSet\": {
        \"Name\": \"ec2.$DOMAIN_NAME\",
        \"Type\": \"A\",
        \"TTL\": 60,
        \"ResourceRecords\": [
          {
            \"Value\": \"$IP_ADDRESS\"
          }
        ]
      }
    }
  ]
}"

echo "Done setting A record set"