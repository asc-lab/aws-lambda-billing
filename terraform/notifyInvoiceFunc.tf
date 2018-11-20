resource "aws_s3_bucket_object" "notifyInvoiceFunc_s3" {
  bucket = "${aws_s3_bucket.lambdas.bucket}"
  key = "notifyInvoiceFunc.jar"
  source = "notifyInvoiceFunc/target/notifyInvoiceFunc-0.1.jar"
  etag = "${md5(file("notifyInvoiceFunc/target/notifyInvoiceFunc-0.1.jar"))}"
}

resource "aws_lambda_function" "notifyInvoiceFunc" {
  function_name = "NotifyInvoiceFunc"
  handler = "pl.altkom.asc.lab.lambda.notification.NotifyInvoiceFunction::apply"
  role = "${aws_iam_role.lambda_role.arn}"
  runtime = "java8"
  s3_bucket = "${aws_s3_bucket.lambdas.bucket}"
  s3_key = "${aws_s3_bucket_object.notifyInvoiceFunc_s3.key}"

  timeout = "40"
  memory_size = "320"
  source_code_hash = "${base64sha256(file("notifyInvoiceFunc/target/notifyInvoiceFunc-0.1.jar"))}"

  environment {
    variables {
      SENDGRID_API_KEY = "${var.sendgrid_api_key}",
      TWILIO_ACCOUNT_SID = "${var.twilio_account_sid}",
      TWILIO_AUTH_TOKEN = "${var.twilio_auth_token}"
    }
  }
}

resource "aws_lambda_event_source_mapping" "invoice_notify_request_mapping" {
  batch_size = 10
  event_source_arn = "${aws_sqs_queue.invoice_notify_request_queue.arn}"
  enabled           = "${var.active}"
  function_name = "${aws_lambda_function.notifyInvoiceFunc.arn}"
}
