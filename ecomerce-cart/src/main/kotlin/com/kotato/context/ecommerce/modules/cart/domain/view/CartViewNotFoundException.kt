package com.kotato.context.ecommerce.modules.cart.domain.view

class CartViewNotFoundException(id: String): RuntimeException("Cart view with id <$id> was not found.")