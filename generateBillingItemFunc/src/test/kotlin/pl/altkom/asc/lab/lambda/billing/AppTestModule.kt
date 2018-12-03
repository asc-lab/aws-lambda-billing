package pl.altkom.asc.lab.lambda.billing

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.GetQueueUrlResult
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import pl.altkom.asc.lab.lambda.billing.aws.S3Client
import pl.altkom.asc.lab.lambda.billing.aws.S3ClientMock

class AppTestModule : AppModule() {

    override fun provideAmazonS3(): S3Client {
        return S3ClientMock()
    }

    override fun provideAmazonSQS(): AmazonSQS {
        val mock = Mockito.mock(AmazonSQS::class.java)
        Mockito.`when`(mock.getQueueUrl(ArgumentMatchers.anyString())).thenReturn(GetQueueUrlResult().withQueueUrl("test"))
        return mock
    }

}