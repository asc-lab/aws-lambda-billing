resource "aws_dynamodb_table" "PriceList" {
  name = "PriceList"
  read_capacity = 1
  write_capacity = 1
  hash_key = "customerCode"

  attribute {
    name = "customerCode"
    type = "S"
  }
}

resource "aws_dynamodb_table_item" "ASC_prices" {
  table_name = "${aws_dynamodb_table.PriceList.name}"
  hash_key = "${aws_dynamodb_table.PriceList.hash_key}"
  item = <<ITEM
{
  "customerCode": {"S": "ASC"},
  "prices": { "L":[
                    { "M" : {
                        "ageFrom" : { "N" : "0" },
                        "ageTo" : { "N" : "65" },
                        "gender" : { "S" : "MALE" },
                        "price" : { "N" : "200" },
                        "productCode" : { "S" : "A" }
                        }
                     },
                     { "M" : {
                        "ageFrom" : { "N" : "66" },
                        "ageTo" : { "N" : "199" },
                        "gender" : { "S" : "MALE" },
                        "price" : { "N" : "250" },
                        "productCode" : { "S" : "A" }
                        }
                     },
                     { "M" : {
                        "ageFrom" : { "N" : "0" },
                        "ageTo" : { "N" : "65" },
                        "gender" : { "S" : "FEMALE" },
                        "price" : { "N" : "190" },
                        "productCode" : { "S" : "A" }
                        }
                     },
                     { "M" : {
                        "ageFrom" : { "N" : "66" },
                        "ageTo" : { "N" : "199" },
                        "gender" : { "S" : "FEMALE" },
                        "price" : { "N" : "240" },
                        "productCode" : { "S" : "A" }
                        }
                     },
                     { "M" : {
                        "ageFrom" : { "N" : "0" },
                        "ageTo" : { "N" : "65" },
                        "gender" : { "S" : "MALE" },
                        "price" : { "N" : "200" },
                        "productCode" : { "S" : "B" }
                        }
                     },
                     { "M" : {
                        "ageFrom" : { "N" : "66" },
                        "ageTo" : { "N" : "199" },
                        "gender" : { "S" : "MALE" },
                        "price" : { "N" : "250" },
                        "productCode" : { "S" : "B" }
                        }
                     },
                     { "M" : {
                        "ageFrom" : { "N" : "0" },
                        "ageTo" : { "N" : "65" },
                        "gender" : { "S" : "FEMALE" },
                        "price" : { "N" : "190" },
                        "productCode" : { "S" : "B" }
                        }
                     },
                     { "M" : {
                        "ageFrom" : { "N" : "66" },
                        "ageTo" : { "N" : "199" },
                        "gender" : { "S" : "FEMALE" },
                        "price" : { "N" : "240" },
                        "productCode" : { "S" : "B" }
                        }
                     }
                     ]
        }
}
ITEM
}