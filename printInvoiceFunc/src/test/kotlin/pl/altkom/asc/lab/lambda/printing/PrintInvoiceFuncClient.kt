package pl.altkom.asc.lab.lambda.printing

import io.micronaut.function.client.FunctionClient
import io.micronaut.http.annotation.Body
import io.reactivex.Single
import pl.altkom.asc.lab.lambda.printing.Event
import javax.inject.Named

@FunctionClient
interface PrintInvoiceFuncClient {

    @Named("print-invoice-func")
    fun apply(@Body event: Event): Single<String>

}
