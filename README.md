# AWS Labda Billing PoC

## Compilation and deployment

### Preparation
Deployment requires AWS CLI and Terraform

Before first deployment you need to initialize Terraform with  
```
init.sh
```

### Building

```
build.sh
```

### Deployment
Terraform should create all necessary infrastructure (S3 bucket, ...)
and deploy lambdas code.

```
buildAndDeploy.sh
```
