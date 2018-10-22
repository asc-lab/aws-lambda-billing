package pl.altkom.asc.lab.lambda.billing

import com.amazonaws.services.s3.event.S3EventNotification
import io.micronaut.function.client.FunctionClient
import io.micronaut.http.annotation.Body
import io.reactivex.Single
import javax.inject.Named

@FunctionClient
interface GenerateBillingItemFuncClient {

    @Named("generate-billing-item-func")
    fun apply(@Body event: S3EventNotification): Single<String>

}
