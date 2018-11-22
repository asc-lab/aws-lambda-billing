package pl.altkom.asc.lab.lambda.invoice

import io.micronaut.function.client.FunctionClient
import io.micronaut.http.annotation.Body
import io.reactivex.Single
import javax.inject.Named

@FunctionClient
interface GenerateInvoiceFuncClient {

    @Named("generate-invoice-func")
    fun apply(@Body event: Event): Single<String>

}
