package pl.altkom.asc.lab.lambda.invoice.aws

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.GetQueueUrlResult
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import pl.altkom.asc.lab.lambda.billing.aws.SQSClient

@Replaces(factory = SQSClient::class)
@Factory
class SQSClientMock : SQSClient() {

    @Bean
    override fun amazonSQS(): AmazonSQS {
        val mock = Mockito.mock(AmazonSQS::class.java)
        Mockito.`when`(mock.getQueueUrl(ArgumentMatchers.anyString())).thenReturn(GetQueueUrlResult().withQueueUrl("test"))
        return mock
    }
}
