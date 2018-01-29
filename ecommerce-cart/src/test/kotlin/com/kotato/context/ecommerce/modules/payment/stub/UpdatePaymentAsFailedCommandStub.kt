package com.kotato.context.ecommerce.modules.payment.stub

import com.kotato.context.ecommerce.modules.payment.domain.update.status.failed.UpdatePaymentAsFailedCommand

class UpdatePaymentAsFailedCommandStub {
    companion object {
        fun random() = UpdatePaymentAsFailedCommand(PaymentIdStub.random().asString())
    }
}