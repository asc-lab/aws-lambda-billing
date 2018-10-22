package pl.altkom.asc.lab.lambda.billing.aws

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.event.S3EventNotification
import java.io.BufferedReader
import javax.inject.Singleton

@Singleton
open class S3Client {

    private val s3Client = AmazonS3Client.builder().build()

    open fun getReader(record: S3EventNotification.S3EventNotificationRecord): BufferedReader =
            s3Client.getObject(record.s3.bucket.name, record.s3.`object`.key).objectContent.bufferedReader()
}
