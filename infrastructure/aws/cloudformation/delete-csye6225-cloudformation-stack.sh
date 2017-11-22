
#Yashodhan Prabhune,001220710,prabhune.y@husky.neu.edu
#Bhumika Khatri,001284560,khatri.bh@husky.neu.edu
#Aadesh Randeria,001224139,randeria.a@husky.neu.edu
#Siddhant Chandiwal,001286480,chandiwal.s@husky.neu.edu


aws s3 rm s3://$1 --recursive
aws cloudformation delete-stack --stack-name $2