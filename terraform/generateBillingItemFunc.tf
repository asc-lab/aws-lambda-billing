resource "aws_s3_bucket_object" "generateBillingItemFunc_s3"{
  bucket = "${aws_s3_bucket.lambdas.bucket}"
  key = "generateBillingItemFunc.jar"
  source = "generateBillingItemFunc/target/generateBillingItemFunc-0.1.jar"
  etag = "${md5(file("generateBillingItemFunc/target/generateBillingItemFunc-0.1.jar"))}"
}

resource "aws_lambda_function" "generateBillingItemFunc" {
  function_name = "GenerateBillingItemFunc"
  handler = "pl.altkom.asc.lab.lambda.billing.GenerateBillingItemFunction::apply"
  role = "${aws_iam_role.lambda_role.arn}"
  runtime = "java8"
  s3_bucket = "${aws_s3_bucket.lambdas.bucket}"
  s3_key = "${aws_s3_bucket_object.generateBillingItemFunc_s3.key}"
  timeout = "40"
  memory_size = "448"
  source_code_hash = "${base64sha256(file("generateBillingItemFunc/target/generateBillingItemFunc-0.1.jar"))}"

  environment {
    variables {
      INVOICE_GENERATION_REQUEST_QUEUE = "${aws_sqs_queue.invoice_generation_request_queue.name}"
    }
  }

}



resource "aws_lambda_permission" "allow_terraform_bucket" {
  statement_id = "AllowExecutionFromS3Bucket"
  action = "lambda:InvokeFunction"
  function_name = "${aws_lambda_function.generateBillingItemFunc.arn}"
  principal = "s3.amazonaws.com"
  source_arn = "${aws_s3_bucket.input.arn}"
}

resource "aws_s3_bucket_notification" "bucket_terraform_notification" {
  bucket = "${aws_s3_bucket.input.id}"
  lambda_function {
    lambda_function_arn = "${aws_lambda_function.generateBillingItemFunc.arn}"
    events = ["s3:ObjectCreated:*"]
  }
}
