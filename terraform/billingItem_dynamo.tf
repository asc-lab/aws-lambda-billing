resource "aws_dynamodb_table" "BillingItem" {
  name = "BillingItem"
  read_capacity = 5
  write_capacity = 5
  hash_key = "key"
  range_key = "billingKey"

  attribute {
    name = "key"
    type = "S"
  }

  attribute {
    name = "billingKey"
    type = "S"
  }
}
