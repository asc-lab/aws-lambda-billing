package pl.altkom.asc.lab.lambda.billing

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FunctionModule::class])
interface FunctionComponent {

    fun provideFunction():GenerateBillingItemFunction

}