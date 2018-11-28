package pl.altkom.asc.lab.lambda.billing

import com.amazonaws.services.lambda.runtime.Context
import io.micronaut.context.ApplicationContext
import io.micronaut.function.aws.MicronautRequestStreamHandler

class CachingRequestHandler : MicronautRequestStreamHandler() {

    override fun buildApplicationContext(context: Context?): ApplicationContext? {

        if (cachedContext == null) {
            cachedContext = super.buildApplicationContext(context);
        }
        return cachedContext
    }

    companion object {
        var cachedContext: ApplicationContext? = null
    }
}