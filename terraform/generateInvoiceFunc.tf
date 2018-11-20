resource "aws_s3_bucket_object" "generateInvoiceFunc_s3"{
  bucket = "${aws_s3_bucket.lambdas.bucket}"
  key = "generateInvoiceFunc.jar"
  source = "generateInvoiceFunc/target/generateInvoiceFunc-0.1.jar"
  etag = "${md5(file("generateInvoiceFunc/target/generateInvoiceFunc-0.1.jar"))}"
}

resource "aws_lambda_function" "generateInvoiceFunc" {
  function_name = "GenerateInvoiceFunc"
  handler = "io.micronaut.function.aws.MicronautRequestStreamHandler"
  role = "${aws_iam_role.lambda_role.arn}"
  runtime = "java8"
  s3_bucket = "${aws_s3_bucket.lambdas.bucket}"
  s3_key = "${aws_s3_bucket_object.generateInvoiceFunc_s3.key}"

  timeout = "40"
  memory_size = "448"
  source_code_hash = "${base64sha256(file("generateInvoiceFunc/target/generateInvoiceFunc-0.1.jar"))}"

  environment {
    variables {
      INVOICE_PRINT_REQUEST_QUEUE = "${aws_sqs_queue.invoice_print_request_queue.name}"
      INVOICE_NOTIFY_REQUEST_QUEUE = "${aws_sqs_queue.invoice_notify_request_queue.name}"
    }
  }
}

resource "aws_lambda_event_source_mapping" "invoice_generation_request_mapping" {
  batch_size        = 10
  event_source_arn  = "${aws_sqs_queue.invoice_generation_request_queue.arn}"
  enabled           = "${var.active}"
  function_name     = "${aws_lambda_function.generateInvoiceFunc.arn}"
}
