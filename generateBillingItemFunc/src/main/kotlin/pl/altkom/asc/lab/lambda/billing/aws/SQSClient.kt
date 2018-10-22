package pl.altkom.asc.lab.lambda.billing.aws

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory

@Factory
open class SQSClient {

    @Bean
    open fun amazonSQS():AmazonSQS = AmazonSQSClientBuilder.defaultClient()

}