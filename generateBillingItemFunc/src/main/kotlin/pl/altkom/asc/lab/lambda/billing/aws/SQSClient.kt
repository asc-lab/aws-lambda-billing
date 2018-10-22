package pl.altkom.asc.lab.lambda.billing.aws

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder

open class SQSClient {

    open fun amazonSQS(): AmazonSQS = AmazonSQSClientBuilder.defaultClient()

}