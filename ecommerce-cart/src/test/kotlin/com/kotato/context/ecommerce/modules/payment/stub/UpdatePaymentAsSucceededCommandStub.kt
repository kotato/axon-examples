package com.kotato.context.ecommerce.modules.payment.stub

import com.kotato.context.ecommerce.modules.payment.domain.update.status.succeeded.UpdatePaymentAsSucceededCommand

class UpdatePaymentAsSucceededCommandStub {
    companion object {
        fun random() = UpdatePaymentAsSucceededCommand(PaymentIdStub.random().asString())
    }
}