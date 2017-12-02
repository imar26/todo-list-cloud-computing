# CSYE 6225 - FALL 2017

# Team Members                

Aadesh Randeria   001224139  randeria.a@husky.neu.edu

Bhumika Khatri   001284560  khatri.bh@husky.neu.edu

Siddhant Chandiwal 001286480  chandiwal.s@husky.neu.edu

Yashodhan Prabhune 001220710  prabhune.y@husky.neu.edu


# Steps to Configure SSL Certificate on Load Balancer
1. Purchase positive SSL certificate from Namecheap. Alternatively, you can also use Let's Encrypt.
2. Activate your certificate by filling out the required details such as Name, Organization, Address, etc.
3. Once the details have been entered you will receive CSR, Primary Key and dummy certificate from Namecheap.Save all the information as it will be required later.
4. You can now see this certificates on the Product List of your Namecheap dashboard.
5. To complete the activation process, click on the certificate and enter your CSR and Primary Key along with other details that are asked in the form. Then click on Done.
6. A pop-up appears which says your activation is been processed.
7. Click on the See details button next to the certificate. You will receive 3 values such as Host and Target Values.Note these down as you will need them to create a DNS record in AWS.
8. You will receive an email from COMODO which will include your certificate information as an attachment.

# Steps to Import Certificate in AWS and to configure DNS in Route 53
1. Login to AWS Console and go to Route 53.
2. Create a new CNAME resource record with the following values:
   -- Enter the value of host that was received from namecheap in the "name" section.
   -- Set TTL as 1m, ALIAS as No
   -- Enter target value obtained from Namecheap in the value section and click OK.
3. Go to AWS ACM(Amazon Certificate Manager), click on import certificate.
4. Enter the certificate private key, body and chain received from the email.
5. Click OK. Certificate has now been imported.
