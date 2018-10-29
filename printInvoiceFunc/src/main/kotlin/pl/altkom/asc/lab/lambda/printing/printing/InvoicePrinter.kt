package pl.altkom.asc.lab.lambda.printing.printing

import com.amazonaws.util.json.Jackson
import org.slf4j.LoggerFactory
import pl.altkom.asc.lab.lambda.printing.invoicing.Invoice
import java.io.InputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import sun.misc.BASE64Encoder

class InvoicePrinter(
        val jsReportUrl: String = System.getenv("JS_REPORT_URL") ?: "",
        val invoiceTemplateName: String = System.getenv("INVOICE_TEMPLATE_NAME") ?: "",
        val username: String = System.getenv("JS_REPORT_USERNAME") ?: "",
        val password: String = System.getenv("JS_REPORT_PASSWORD") ?: ""
) {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

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

            log.info("PDF generated in ${System.currentTimeMillis()-start}ms")

            return inputStream
        }

    }
}