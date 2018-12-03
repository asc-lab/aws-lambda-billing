package pl.altkom.asc.lab.lambda.billing.billing

import javax.inject.Singleton

@Singleton
class BillingItemRepositoryMock : BillingItemRepository() {

    override fun save(billingItem: BillingItem) {

    }
}