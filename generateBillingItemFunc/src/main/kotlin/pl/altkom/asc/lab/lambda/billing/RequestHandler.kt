package pl.altkom.asc.lab.lambda.billing

import com.amazonaws.services.s3.event.S3EventNotification

class RequestHandler {

    private val appComponent = DaggerAppComponent.builder().build()
    private val function = appComponent.provideFunction()

    fun apply(event: S3EventNotification): String {
        return function.apply(event)
    }

}