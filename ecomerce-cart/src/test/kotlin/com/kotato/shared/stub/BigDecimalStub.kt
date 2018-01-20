package com.kotato.shared.stub

import com.github.javafaker.Faker
import java.math.BigDecimal

class BigDecimalStub {
    companion object {
        fun random() = BigDecimal(Faker().number().randomDouble(2, 0, 100)).setScale(2, BigDecimal.ROUND_HALF_EVEN)
    }
}