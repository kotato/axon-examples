package com.kotato.context.ecommerce.modules.cart.stub

import com.kotato.context.ecommerce.modules.cart.domain.view.find.FindCartQuery

class FindCartQueryStub {
    companion object {
        fun random() = FindCartQuery(CartIdStub.random().asString())
    }
}