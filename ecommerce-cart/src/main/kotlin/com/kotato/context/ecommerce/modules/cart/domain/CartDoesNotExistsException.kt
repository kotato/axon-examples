package com.kotato.context.ecommerce.modules.cart.domain

class CartDoesNotExistsException(id: String): RuntimeException("Cart with id <$id> does not exists.")