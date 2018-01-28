package com.kotato.context.ecommerce.modules.payment.stub

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.PaymentStatus
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentView
import com.kotato.shared.money.Money
import com.kotato.shared.stub.MoneyStub
import java.time.ZonedDateTime

class PaymentViewStub {
    companion object {
        fun random(id: PaymentId = PaymentIdStub.random(),
                   createdOn: ZonedDateTime = ZonedDateTime.now(),
                   price: Money = MoneyStub.random(),
                   status: PaymentStatus = PaymentStatusStub.random())
                = PaymentView(id, createdOn, price, status)
    }
}