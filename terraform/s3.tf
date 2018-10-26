resource "aws_s3_bucket" "input" {
  bucket = "asc-lab-serverless-input"
}

resource "aws_s3_bucket" "lambdas" {
  bucket = "asc-lab-serverless-lambdas"
}
