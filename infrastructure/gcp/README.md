# CSYE 6225 - FALL 2017

# Team Members

Aadesh Randeria   001224139  randeria.a@husky.neu.edu

Bhumika Khatri   001284560  khatri.bh@husky.neu.edu

Siddhant Chandiwal 001286480  chandiwal.s@husky.neu.edu

Yashodhan Prabhune 001220710  prabhune.y@husky.neu.edu


# gcloud sdk command to create a virtual machine

gcloud compute instances create {{INSTANCE_NAME}} --machine-type {{MACHINE_TYPE_NAME}} --description {{DESCRIPTION}} --async --boot-disk-auto-delete --boot-disk-type pd-standard --can-ip-forward --zone {{REGION}}

For eg:- gcloud compute instances create my-new-instance --machine-type n1-standard-1 --description GoogleCloudInstance --async --boot-disk-auto-delete --boot-disk-type pd-standard --can-ip-forward --zone us-east1-c

# gcloud sdk command to view list of instances

gcloud compute instances list

# gcloud sdk command to delete a virtual machine

gcloud compute instances delete {{INSTANCE_NAME}} --zone {{REGION}}

For eg:- gcloud compute instances delete my-new-instance --zone us-east1-c
