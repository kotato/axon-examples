package com.kotato.context.ecommerce.modules.payment.domain.update.status.failed

import com.kotato.cqrs.domain.command.Command

data class UpdatePaymentAsFailedCommand(val paymentId: String) : Command