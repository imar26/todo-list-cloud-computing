
#Yashodhan Prabhune,001220710,prabhune.y@husky.neu.edu
#Bhumika Khatri,001284560,khatri.bh@husky.neu.edu
#Aadesh Randeria,001224139,randeria.a@husky.neu.edu
#Siddhant Chandiwal,001286480,chandiwal.s@husky.neu.edu

export TravisCIUploadS3=Travis-Upload-To-S3
export TravisCICodeDeploy=Travis-Code-Deploy
export CODE_DEPLOY_APPLICATION_NAME=csye6225
export MyCodeDeployServiceRole=CodeDeployServiceRole
export TravisUser=travis

aws cloudformation create-stack --stack-name $1 --capabilities "CAPABILITY_NAMED_IAM" --template-body file://create-policies-roles.json --parameters ParameterKey=TravisCIUploadS3,ParameterValue=$TravisCIUploadS3 ParameterKey=TravisCICodeDeploy,ParameterValue=$TravisCICodeDeploy ParameterKey=CodeDeployApplicationName,ParameterValue=$CODE_DEPLOY_APPLICATION_NAME ParameterKey=MyCodeDeployServiceRole,ParameterValue=$MyCodeDeployServiceRole ParameterKey=TravisUser,ParameterValue=$TravisUser