package pl.altkom.asc.lab.lambda.billing.aws

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.amazonaws.util.json.Jackson
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import kotlin.system.measureTimeMillis

@Singleton
class SQSPublisherIntroduction(
        private val sqs: AmazonSQS
) : MethodInterceptor<Any, Any> {

    private val queueUrlCache: MutableMap<String, String> = HashMap()

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    override fun intercept(context: MethodInvocationContext<Any, Any>?): Any? {

        val clientAnnotation = context!!.findAnnotation(SQSPublisher::class.java)
                .orElseThrow { IllegalStateException("SQSPublisher advice called from type that is not annotated with @SQSPublisher: $context") }

        if (context.targetMethod.returnType != Void.TYPE) {
            IllegalStateException("SQS Publisher method shouldn't return any results")
        }

        if (context.targetMethod.returnType != Void.TYPE) {
            IllegalStateException("SQS Publisher method shouldn't return any results")
        }

        val queueName = clientAnnotation.get("value", String::class.java).orElseThrow {
            IllegalStateException("Queue name is mandatory")
        }

        val queueUrl = queueUrlCache.getOrPut(queueName) {sqs.getQueueUrl(queueName).queueUrl}

        val time = measureTimeMillis {

            context.parameterValues.forEach {
                sqs.sendMessage(SendMessageRequest(queueUrl, Jackson.toJsonPrettyString(it)))
            }
        }

        log.info("Message(s) to $queueName published in $time ms")

        return null
    }
}