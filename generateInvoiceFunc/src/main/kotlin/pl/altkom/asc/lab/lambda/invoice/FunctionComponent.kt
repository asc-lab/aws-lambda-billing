package pl.altkom.asc.lab.lambda.invoice

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FunctionModule::class])
interface FunctionComponent {

    fun provideFunction():GenerateInvoiceFunction

}