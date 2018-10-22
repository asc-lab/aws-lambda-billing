resource "aws_sqs_queue" "invoice_generation_request_queue_deadletter" {
  name = "invoice_generation_request_queue_deadletter"
  delay_seconds = 90
  max_message_size = 2048
  message_retention_seconds = 86400
  receive_wait_time_seconds = 10

  tags {
    Environment = "production"
  }
}

resource "aws_sqs_queue" "invoice_generation_request_queue" {
  name = "invoice_generation_request_queue"
  delay_seconds = 90
  max_message_size = 2048
  message_retention_seconds = 86400
  receive_wait_time_seconds = 10
  redrive_policy = "{\"deadLetterTargetArn\":\"${aws_sqs_queue.invoice_generation_request_queue_deadletter.arn}\",\"maxReceiveCount\":4}"

  tags {
    Environment = "production"
  }
}