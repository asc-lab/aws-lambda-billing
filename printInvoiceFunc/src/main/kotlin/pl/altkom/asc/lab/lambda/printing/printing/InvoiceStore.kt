package pl.altkom.asc.lab.lambda.printing.printing

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import java.io.InputStream

class InvoiceStore {

    private val s3Client = AmazonS3Client.builder().build()
    private val bucketName: String = System.getenv("INVOICE_PRINTOUT_BUCKET") ?: ""


    fun store(stream: InputStream, name: String) {
        s3Client.putObject(bucketName, name, stream, ObjectMetadata())
    }
}