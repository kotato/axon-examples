package com.kotato.shared

import com.kotato.shared.transaction.ReadModelTransaction
import javax.inject.Named

@Named
open class ReadModelTransactionWrapper {

    @ReadModelTransaction
    open operator fun <T> invoke(lambda: () -> T) = lambda()

}