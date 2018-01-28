package com.kotato.context.ecommerce.modules.payment.infrastructure.view

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentView
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentViewRepository
import org.springframework.data.jpa.repository.JpaRepository
import javax.inject.Named

@Named
open class JpaPaymentViewRepository(private val persistenceRepository: JpaPaymentViewPersistenceRepository) : PaymentViewRepository {

    override fun save(view: PaymentView) {
        view.let(persistenceRepository::saveAndFlush)
    }

    override fun search(id: PaymentId): PaymentView? = id.let(persistenceRepository::findOne)

    interface JpaPaymentViewPersistenceRepository : JpaRepository<PaymentView, PaymentId>

}