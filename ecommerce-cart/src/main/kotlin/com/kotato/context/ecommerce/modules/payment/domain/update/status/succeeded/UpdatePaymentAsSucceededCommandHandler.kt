package com.kotato.context.ecommerce.modules.payment.domain.update.status.succeeded

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import org.axonframework.commandhandling.CommandHandler
import javax.inject.Named

@Named
open class UpdatePaymentAsSucceededCommandHandler(private val updater: PaymentAsSucceededUpdater) {

    @CommandHandler
    open fun on(command: UpdatePaymentAsSucceededCommand) {
        updater(PaymentId.fromString(command.paymentId))
    }

}