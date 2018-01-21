package com.kotato.shared.stub

import com.kotato.shared.money.Money
import java.math.BigDecimal

class MoneyStub {
    companion object {
        fun random(price: BigDecimal = BigDecimalStub.random(),
                   currency: String = "EUR") = Money.of(price, currency)
    }
}