package pl.altkom.asc.lab.lambda.notification

import com.amazonaws.services.lambda.runtime.events.SQSEvent


class RequestHandler {

    private val appComponent = DaggerFunctionComponent.builder().build()
    private val function = appComponent.provideFunction()

    fun apply(event: SQSEvent) : String {
        return function.apply(event)
    }
}