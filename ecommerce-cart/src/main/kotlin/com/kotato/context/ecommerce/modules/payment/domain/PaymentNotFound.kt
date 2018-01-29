package com.kotato.context.ecommerce.modules.payment.domain

class PaymentNotFound(id: String): RuntimeException("Payment with id <$id> was not found.")