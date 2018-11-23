# AWS Lambda Billing PoC

This example shows simplified billing system in serverless architecture.

<p align="center">
    <img alt="Architecture" src="https://raw.githubusercontent.com/asc-lab/aws-lambda-billing/master/readme-images/aws-lambda-architecture.png" />
</p>

1. User uploads CSV file (with name structure CLIENTCODE_YEAR_MONTH_activeList.txt.) with Beneficiaries (the sample file is located in the data-examples folder) to a specific data storage - asc-lab-serverless-input S3 Bucket.

2. The above action triggers a function (GenerateBillingItemsFunc) that is responsible for:

    * generating billing items (using prices from an external database - PriceList table in DynamoDB) and saving them in the table BillingItem;
    * sending message about the need to create a new invoice to invoice-generation-request-queue;

3. When a new message appears on the invoice-generation-request-queue, next function is triggered (GenerateInvoiceFunc). This function creates domain object Invoice and save this object in database (DynamoDB Invoices table) and send message to queues: invoice-print-request-queue and invoice-notification-request-queue.

4. When a new message appears on the invoice-print-request-queue, function PrintInvoiceFunc is triggered. This function uses external engine to PDF generation - JsReport and saves PDF file in asc-lab-serverless-printout S3 Bucket.

5. When a new message appears on the invoice-notification-request-queue, function NotifyInvoiceFunc is triggered. This function uses two external systems - SendGrid to Email sending and Twilio to SMS sending.

## Preparation
Deployment requires AWS CLI and Terraform

Before first deployment you need to initialize Terraform with  
```
init.sh
```

Also you need to create some external accounts and informations
1. jsreportonline (https://jsreportonline.net/) - just username and password
2. SendGrid (https://sendgrid.com/) - API key
3. Twilio (https://www.twilio.com/) - Account SID and Auth Token

As a last step, you need to create variables file to give all the above information to Terraform.
Create file billing.tfvars in main folder
insides should looks like this:
```properties
jsreport_url = "https://some_user.jsreportonline.net/api/report"
jsreport_username = "some_user"
jsreport_password = "some_password"
sendgrid_api_key = "SG.rePNAA_AAAAaAA1aAAAi6w.r5AAAUZhyaaaUo7w0030MFAauAaTWvXw-n11mpAaqe0"
twilio_account_sid = "ACd111feb1c11f11111111cd1dd1111c11"
twilio_auth_token = "1ed1a11111b1af1111b111d1111dd11d"
```


## Building and Deployment
Terraform should create all necessary infrastructure (S3 bucket, SQS Queues, DynamoDB Tables (with data))
and deploy lambdas code.

```
buildAndDeploy.sh
```

## Testing
You should see S3 Bucket called asc-lab-serverless-input. Put there sample file and enjoy logs in AWS CloudWatch. 

## Disabling
SQS triggers can actively check queues for new messages. If you don't want to exceed your free tier, you can disable them using our Terraform configuration. Just run:
```
terraform apply -auto-approve -var-file="billing.tfvars" -var "active=false" terraform
```
To re-enable triggers run:
```
terraform apply -auto-approve -var-file="billing.tfvars" -var "active=true" terraform
```    
