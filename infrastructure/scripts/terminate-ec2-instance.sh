#!/bin/bash

echo "getting instance id from command line"

INSTANCE_ID=$1
echo $INSTANCE_ID

echo "got instance id"

echo "get security group id"

SECURITY_GROUP=$(aws ec2 describe-instances --instance-ids $INSTANCE_ID --query "Reservations[*].Instances[*].SecurityGroups[*].GroupId" --output text)
echo $SECURITY_GROUP

echo "got security group id"

echo "modify instance attribute"

aws ec2 modify-instance-attribute --instance-id $INSTANCE_ID --no-disable-api-termination

echo "instance attribute modified"

echo "delete instance"

aws ec2 terminate-instances --instance-ids $INSTANCE_ID

echo "instance deleted"

echo "Wait for instance to be terminated"

aws ec2 wait instance-terminated --instance-ids $INSTANCE_ID

echo "Instance is terminated"

echo "delete security group"

aws ec2 delete-security-group --group-id $SECURITY_GROUP

echo "security group deleted"