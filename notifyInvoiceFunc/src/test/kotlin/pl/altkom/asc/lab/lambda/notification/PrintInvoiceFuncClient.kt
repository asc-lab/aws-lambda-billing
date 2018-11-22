package pl.altkom.asc.lab.lambda.notification

import io.micronaut.function.client.FunctionClient
import io.micronaut.http.annotation.Body
import io.reactivex.Single
import javax.inject.Named

@FunctionClient
interface PrintInvoiceFuncClient {

    @Named("notify-invoice-func")
    fun apply(@Body event: Event): Single<String>

}
