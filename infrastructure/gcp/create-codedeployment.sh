gcloud beta bigtable instances create google-cloud-bigtable --cluster=cluster-google-cloud-bigtable --cluster-zone=us-east1-c --description=Google-Cloud-Big-Table --instance-type=PRODUCTION --cluster-num-nodes=3 --async --cluster-storage-type=SSD
echo -e "project = csye6225finalpresentation\n instance = google-cloud-bigtable" > ~/.cbtrc
cbt createtable csyetable 10,20
cbt createfamily csyetable csyefamily
cbt setgcpolicy csyetable csyefamily maxage=1200s
gcloud deployment-manager deployments create finalpresentation --config deployment-manager.yaml
gcloud dns record-sets transaction start -z=create-instances-managed-zone
export ip=$(gcloud compute forwarding-rules describe create-instances-global-forwarding-rule --global | grep IPAddress)
export ip=$(cut -d ":" -f 2 <<< "$ip")
echo $ip
gcloud dns record-sets transaction add -z=create-instances-managed-zone --name=$1 --type=A --ttl=60 $ip
gcloud dns record-sets transaction execute -z=create-instances-managed-zone