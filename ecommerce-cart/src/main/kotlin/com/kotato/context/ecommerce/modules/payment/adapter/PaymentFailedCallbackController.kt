package com.kotato.context.ecommerce.modules.payment.adapter

import com.kotato.context.ecommerce.modules.payment.domain.update.status.failed.UpdatePaymentAsFailedCommand
import com.kotato.cqrs.domain.command.CommandBus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
open class PaymentFailedCallbackController(private val commandBus: CommandBus) {

    @PostMapping("/ecommerce/payment/{paymentId}/failed")
    open fun checkout(@PathVariable("paymentId") paymentId: String): ResponseEntity<Unit> {
        commandBus.handle(UpdatePaymentAsFailedCommand(paymentId))
        return ResponseEntity.noContent().build()
    }

}