package com.kotato.context.ecommerce.modules.order.domain.view

class OrderViewNotFoundException(id: String) : RuntimeException("Order for id <$id> was not found.")