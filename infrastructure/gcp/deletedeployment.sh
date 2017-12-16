cbt deletefamily csyetable csyefamily
cbt deletetable csyetable
gcloud beta bigtable instances delete google-cloud-bigtable
gcloud dns record-sets transaction start -z=create-instances-managed-zone
export ip=$(gcloud compute forwarding-rules describe create-instances-global-forwarding-rule --global | grep IPAddress)
export ip=$(cut -d ":" -f 2 <<< "$ip")
echo $ip
gcloud dns record-sets transaction remove -z create-instances-managed-zone --name $1 --ttl 60 --type A $ip
gcloud dns record-sets transaction remove -z create-instances-managed-zone --name $1 --ttl 60 --type TXT "csye6225"
gcloud dns record-sets transaction execute -z=create-instances-managed-zone
gcloud deployment-manager deployments delete finalpresentation
gsutil rm -r gs://$2
gcloud beta functions delete helloGET