package com.kotato.context.ecommerce.modules.payment.domain.view.create

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.PaymentStatus
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentView
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentViewRepository
import com.kotato.shared.money.Money
import com.kotato.shared.transaction.ReadModelTransaction
import java.time.ZonedDateTime
import javax.inject.Named

@Named
open class PaymentViewCreator(private val repository: PaymentViewRepository) {

    @ReadModelTransaction
    open operator fun invoke(id: PaymentId, createdOn: ZonedDateTime, price: Money) {
        repository.save(PaymentView(id, createdOn, price, PaymentStatus.PENDING))
    }

}