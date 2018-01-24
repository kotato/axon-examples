package com.kotato.context.ecommerce.modules.cart.domain.checkout

class CartAlreadyCheckoutException : RuntimeException("Cart item checkout has already been done.")