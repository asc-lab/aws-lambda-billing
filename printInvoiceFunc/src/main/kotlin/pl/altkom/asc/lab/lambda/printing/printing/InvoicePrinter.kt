package pl.altkom.asc.lab.lambda.printing.printing

import com.amazonaws.util.json.Jackson
import pl.altkom.asc.lab.lambda.printing.invoicing.Invoice
import sun.misc.BASE64Encoder
import java.io.InputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InvoicePrinter @Inject constructor() {

    val jsReportUrl: String = System.getenv("JS_REPORT_URL") ?: ""
    val invoiceTemplateName: String = System.getenv("INVOICE_TEMPLATE_NAME") ?: ""
    val username: String = System.getenv("JS_REPORT_USERNAME") ?: ""
    val password: String = System.getenv("JS_REPORT_PASSWORD") ?: ""

    private val log = Logger.getLogger(this.javaClass.name)!!

    fun print(invoice: Invoice): InputStream {

        val start = System.currentTimeMillis()

        val request = JsReportRequest(Template(invoiceTemplateName), TemplateOptions(), invoice)
        val mURL = URL(jsReportUrl)


        with(mURL.openConnection() as HttpURLConnection) {

            val enc = BASE64Encoder()
            val encodedAuthorization = enc.encode("$username:$password".toByteArray())
            setRequestProperty("Authorization", "Basic $encodedAuthorization")
            setRequestProperty("Content-Type", "application/json")

            requestMethod = "POST"
            doOutput = true

            val wr = OutputStreamWriter(outputStream)
            wr.write(Jackson.toJsonPrettyString(request))
            wr.flush()

            log.info("PDF generated in ${System.currentTimeMillis() - start}ms")

            return inputStream
        }

    }
}