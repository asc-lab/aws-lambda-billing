package pl.altkom.asc.lab.lambda.billing

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import dagger.Module
import dagger.Provides
import pl.altkom.asc.lab.lambda.billing.aws.S3Client


@Module
open class FunctionModule {

    @Provides
    open fun provideAmazonS3(): S3Client = S3Client()


    @Provides
    open fun provideAmazonSQS(): AmazonSQS = AmazonSQSClientBuilder.defaultClient()

}