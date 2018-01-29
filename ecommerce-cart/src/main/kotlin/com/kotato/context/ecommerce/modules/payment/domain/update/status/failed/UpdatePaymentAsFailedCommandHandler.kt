package com.kotato.context.ecommerce.modules.payment.domain.update.status.failed

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import org.axonframework.commandhandling.CommandHandler
import javax.inject.Named

@Named
open class UpdatePaymentAsFailedCommandHandler(private val updater: PaymentAsFailedUpdater) {

    @CommandHandler
    open fun on(command: UpdatePaymentAsFailedCommand) {
        updater(PaymentId.fromString(command.paymentId))
    }

}