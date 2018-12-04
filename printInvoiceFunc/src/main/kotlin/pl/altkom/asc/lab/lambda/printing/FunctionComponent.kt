package pl.altkom.asc.lab.lambda.printing

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FunctionModule::class])
interface FunctionComponent {

    fun provideFunction():PrintInvoiceFunction

}