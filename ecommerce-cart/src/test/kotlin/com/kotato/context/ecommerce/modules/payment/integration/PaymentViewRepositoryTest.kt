package com.kotato.context.ecommerce.modules.payment.integration

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentViewRepository
import com.kotato.context.ecommerce.modules.payment.stub.PaymentViewStub
import com.kotato.shared.ContextStarterTest
import com.kotato.shared.TransactionalWrapper
import org.junit.jupiter.api.Test
import javax.inject.Inject

open class PaymentViewRepositoryTest : ContextStarterTest() {

    @Inject private lateinit var repository: PaymentViewRepository
    @Inject private lateinit var wrapper: TransactionalWrapper

    @Test
    open fun `it should save order view`() {
        val view = PaymentViewStub.random()

        wrapper.wrap { repository.save(view) }

        assertSimilar(view, repository.search(view.id))
    }

}