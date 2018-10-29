package pl.altkom.asc.lab.lambda.notification.sendgrid

import com.sendgrid.*
import org.slf4j.LoggerFactory
import pl.altkom.asc.lab.lambda.notification.notification.InvoiceNotificationRequest
import java.io.IOException


class EmailSender(val apiKey:String = System.getenv("SENDGRID_API_KEY") ?: "") {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    fun send(request: InvoiceNotificationRequest) {

        val start = System.currentTimeMillis()

        val from = Email("asc-lab@altkom.pl")
        val to = Email("kamil@helman.pl")
        val subject = "New Invoice - ${request.invoice?.invoiceNumber}"
        val content = Content("text/plain", "You have new invoice ${request.invoice?.invoiceNumber} for ${request.invoice?.totalCost}.")
        val mail = Mail(from, subject, to, content)

        val sg = SendGrid(apiKey)
        val sgRequest = Request()
        try {
            sgRequest.method = Method.POST
            sgRequest.endpoint = "mail/send"
            sgRequest.body = mail.build()
            val response = sg.api(sgRequest)
            log.info("Sendgrid response code ${response.statusCode}")
        } catch (ex: IOException) {
            throw ex
        }

        log.info("Email sent in ${System.currentTimeMillis()-start}ms")

    }
}