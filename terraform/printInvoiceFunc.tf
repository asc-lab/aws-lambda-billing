resource "aws_s3_bucket_object" "printInvoiceFunc_s3"{
  bucket = "${aws_s3_bucket.lambdas.bucket}"
  key = "printInvoiceFunc.jar"
  source = "printInvoiceFunc/target/printInvoiceFunc-0.1.jar"
  etag = "${md5(file("printInvoiceFunc/target/printInvoiceFunc-0.1.jar"))}"
}

resource "aws_lambda_function" "printInvoiceFunc" {
  function_name = "PrintInvoiceFunc"
  handler = "pl.altkom.asc.lab.lambda.printing.PrintInvoiceFunction::apply"
  role = "${aws_iam_role.lambda_role.arn}"
  runtime = "java8"
  s3_bucket = "${aws_s3_bucket.lambdas.bucket}"
  s3_key = "${aws_s3_bucket_object.printInvoiceFunc_s3.key}"

  timeout = "40"
  memory_size = "320"
  source_code_hash = "${base64sha256(file("printInvoiceFunc/target/printInvoiceFunc-0.1.jar"))}"

  environment {
    variables {
      JS_REPORT_URL = "${var.jsreport_url}",
      JS_REPORT_USERNAME = "${var.jsreport_username}",
      JS_REPORT_PASSWORD = "${var.jsreport_password}",
      INVOICE_TEMPLATE_NAME = "Invoice"
      INVOICE_PRINTOUT_BUCKET = "${aws_s3_bucket.printout.bucket}"
    }
  }
}

resource "aws_lambda_event_source_mapping" "invoice_print_request_mapping" {
  batch_size        = 10
  event_source_arn  = "${aws_sqs_queue.invoice_print_request_queue.arn}"
  enabled           = true
  function_name     = "${aws_lambda_function.printInvoiceFunc.arn}"
}
