./build.sh
[ $? -eq 0 ] || exit 1

terraform apply -auto-approve -var-file="billing.tfvars" terraform