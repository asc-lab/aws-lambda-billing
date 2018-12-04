package pl.altkom.asc.lab.lambda.printing.printing

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import java.io.InputStream
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InvoiceStore @Inject constructor(){

    private val log = Logger.getLogger(this.javaClass.name)!!

    private val s3Client = AmazonS3Client.builder().build()
    private val bucketName: String = System.getenv("INVOICE_PRINTOUT_BUCKET") ?: ""

    fun store(stream: InputStream, name: String) {
        val start = System.currentTimeMillis()

        s3Client.putObject(bucketName, name, stream, ObjectMetadata())

        log.info("PDF stored in ${System.currentTimeMillis()-start}ms")
    }
}