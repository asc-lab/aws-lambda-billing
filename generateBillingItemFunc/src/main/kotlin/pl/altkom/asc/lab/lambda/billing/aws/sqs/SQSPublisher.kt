package pl.altkom.asc.lab.lambda.billing.aws.sqs

import io.micronaut.aop.Introduction
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Parameter
import io.micronaut.context.annotation.Type
import javax.inject.Scope


@Scope
@Bean
@Introduction
@Type(SQSPublisherIntroduction::class)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.CLASS)
annotation class SQSPublisher(@Parameter("queueName") val value: String)