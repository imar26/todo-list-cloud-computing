gcloud beta bigtable instances create google-cloud-bigtable --cluster=cluster-google-cloud-bigtable --cluster-zone=us-east1-c --description=Google-Cloud-Big-Table --instance-type=PRODUCTION --cluster-num-nodes=3 --async --cluster-storage-type=SSD
echo -e "project = csye6225finalpresentation\n instance = google-cloud-bigtable" > ~/.cbtrc
cbt createtable csyetable 10,20
cbt createfamily csyetable csyefamily
cbt setgcpolicy csyetable csyefamily maxage=1200s
gcloud deployment-manager deployments create finalpresentation --config deployment-manager.yaml