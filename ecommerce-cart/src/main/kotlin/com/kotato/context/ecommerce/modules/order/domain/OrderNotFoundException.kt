package com.kotato.context.ecommerce.modules.order.domain

class OrderNotFoundException(id: String) : RuntimeException("Order for id <$id> was not found.")