package com.kotato.context.ecommerce.modules.cart.domain

class CartNotFoundException(id: String): RuntimeException("Cart with id <$id> was not found.")