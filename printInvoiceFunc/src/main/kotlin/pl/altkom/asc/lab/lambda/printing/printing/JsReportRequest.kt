package pl.altkom.asc.lab.lambda.printing.printing

import pl.altkom.asc.lab.lambda.printing.invoicing.Invoice

data class JsReportRequest(
        val template: Template,
        val templateOptions: TemplateOptions,
        val data: Invoice
)

data class Template(
        val name: String
)

class TemplateOptions

