package pl.altkom.asc.lab.lambda.billing

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun provideFunction():GenerateBillingItemFunction

}