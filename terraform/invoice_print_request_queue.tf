resource "aws_sqs_queue" "invoice_print_request_queue_deadletter" {
  name = "invoice_print_request_queue_deadletter"
  max_message_size = 2048
  message_retention_seconds = 86400
  receive_wait_time_seconds = 10

  tags {
    Environment = "production"
  }
}

resource "aws_sqs_queue" "invoice_print_request_queue" {
  name = "invoice_print_request_queue"
  max_message_size = 2048
  message_retention_seconds = 86400
  receive_wait_time_seconds = 10
  visibility_timeout_seconds = 60
  redrive_policy = "{\"deadLetterTargetArn\":\"${aws_sqs_queue.invoice_print_request_queue_deadletter.arn}\",\"maxReceiveCount\":1}"

  tags {
    Environment = "production"
  }
}