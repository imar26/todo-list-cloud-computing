
#Yashodhan Prabhune,001220710,prabhune.y@husky.neu.edu
#Bhumika Khatri,001284560,khatri.bh@husky.neu.edu
#Aadesh Randeria,001224139,randeria.a@husky.neu.edu
#Siddhant Chandiwal,001286480,chandiwal.s@husky.neu.edu

#CLOUDFORMATIONSTACK -- stack name

echo "Getting Stack name as input from Terminal"

export VPC_ID=$(aws ec2 describe-vpcs --query "Vpcs[0].VpcId" --output text)
echo $VPC_ID

export SUBNET_ID=$(aws ec2 describe-subnets --filters "Name=vpc-id, Values=$VPC_ID" --query "Subnets[0].SubnetId" --output text)
export SUBNET_ID_2=$(aws ec2 describe-subnets --filters "Name=vpc-id, Values=$VPC_ID" --query "Subnets[1].SubnetId" --output text)
echo $SUBNET_ID_2

export z_id=$(aws route53 list-hosted-zones --query 'HostedZones[0].Id' --output text)
z_id=${z_id#*e/}
export NAME=$(aws route53 list-hosted-zones --query "HostedZones[0].Name" --output text)
echo $NAME
export NEW_NAME=${NAME%.}
echo $NEW_NAME

export GROUP_NAME=csye6225-webapp
echo $GROUP_NAME

export RDS_GROUP_NAME=csye6225-rds
echo $RDS_GROUP_NAME

export ALLOCATED_STORAGE=10
export DB_INSTANCE_CLASS=db.t2.medium

export ENGINE=MySQL
export ENGINE_VERSION=5.6.35
export DB_INSTANCE_IDENTIFIER=csye6225-fall2017
export DB_USER=dbuser
export DB_PASSWORD=dbpassword
export DB_NAME=csye6225
export PRIMARY_KEYNAME=username
export USER_NAME=username
export TTL_NAME=ttl
export TABLE_NAME=csye6225
export S3_BUCKET_NAME=code-deploy.$NEW_NAME
export SNS_NAME=password_reset
export TagKey=Name
export TagValue=csye6225
export CodeDeployEC2S3=CodeDeploy-EC2-S3
export MyCodeDeployEC2ServiceRole=CodeDeployEC2ServiceRole

aws cloudformation create-stack --stack-name $1 --capabilities "CAPABILITY_NAMED_IAM" --template-body file://ec2-instance-securitygroup-cloudformation-stack.json --parameters ParameterKey=ImageId,ParameterValue=ami-cd0f5cb6 ParameterKey=VpcId,ParameterValue=$VPC_ID ParameterKey=SubnetId,ParameterValue=$SUBNET_ID ParameterKey=HostedZoneId,ParameterValue=$z_id ParameterKey=InstanceType,ParameterValue=t2.micro ParameterKey=KeyName,ParameterValue=csye6225-aws ParameterKey=GroupName,ParameterValue=$GROUP_NAME ParameterKey=Name,ParameterValue=$NAME ParameterKey=RDSGroupName,ParameterValue=$RDS_GROUP_NAME ParameterKey=AllocatedStorage,ParameterValue=$ALLOCATED_STORAGE ParameterKey=DBInstanceClass,ParameterValue=$DB_INSTANCE_CLASS ParameterKey=Engine,ParameterValue=$ENGINE ParameterKey=EngineVersion,ParameterValue=$ENGINE_VERSION ParameterKey=DBInstanceIdentifier,ParameterValue=$DB_INSTANCE_IDENTIFIER ParameterKey=DBUser,ParameterValue=$DB_USER ParameterKey=DBPassword,ParameterValue=$DB_PASSWORD ParameterKey=DBName,ParameterValue=$DB_NAME ParameterKey=SubnetId2,ParameterValue=$SUBNET_ID_2 ParameterKey=PrimaryKeyName,ParameterValue=$PRIMARY_KEYNAME ParameterKey=TTLName,ParameterValue=$TTL_NAME ParameterKey=UserName,ParameterValue=$USER_NAME ParameterKey=TableName,ParameterValue=$TABLE_NAME ParameterKey=S3BucketName,ParameterValue=$S3_BUCKET_NAME ParameterKey=SNSName,ParameterValue=$SNS_NAME ParameterKey=TagKey,ParameterValue=$TagKey ParameterKey=TagValue,ParameterValue=$TagValue ParameterKey=MyCodeDeployEC2ServiceRole,ParameterValue=$MyCodeDeployEC2ServiceRole ParameterKey=CodeDeployEC2S3,ParameterValue=$CodeDeployEC2S3