package com.kotato.context.ecommerce.modules.payment.domain.view

class PaymentViewNotFound(id: String): RuntimeException("Payment view with id <$id> was not found.")