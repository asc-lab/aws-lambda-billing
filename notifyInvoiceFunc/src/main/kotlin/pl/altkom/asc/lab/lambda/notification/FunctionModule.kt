package pl.altkom.asc.lab.lambda.notification

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import dagger.Module
import dagger.Provides


@Module
open class FunctionModule {

    @Provides
    open fun provideAmazonSQS(): AmazonSQS = AmazonSQSClientBuilder.defaultClient()

}