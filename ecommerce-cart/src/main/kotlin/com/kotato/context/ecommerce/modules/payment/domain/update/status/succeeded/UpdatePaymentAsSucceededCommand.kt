package com.kotato.context.ecommerce.modules.payment.domain.update.status.succeeded

import com.kotato.cqrs.domain.command.Command

data class UpdatePaymentAsSucceededCommand(val paymentId: String) : Command