package pl.altkom.asc.lab.lambda.billing.billing

import io.micronaut.context.annotation.Replaces
import javax.inject.Singleton

@Replaces(bean = BillingItemRepository::class)
@Singleton
class BillingItemRepositoryMock : BillingItemRepository() {

    override fun save(billingItem: BillingItem) {

    }
}