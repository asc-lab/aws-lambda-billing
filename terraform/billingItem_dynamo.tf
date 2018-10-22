resource "aws_dynamodb_table" "BillingItem" {
  name = "BillingItem"
  read_capacity = 1
  write_capacity = 1
  hash_key = "key"

  attribute {
    name = "key"
    type = "S"
  }
}
