package com.kotato.context.ecommerce.modules.order.domain.view

class OrderViewByPaymentIdNotFoundException(id: String) : RuntimeException("Order for payment id <$id> was not found.")