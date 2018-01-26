package com.kotato.shared

import com.kotato.shared.transaction.ReadModelTransaction
import javax.inject.Named

@Named
open class TransactionalWrapper {

    @ReadModelTransaction
    open fun <T> wrap(lambda: () -> T) = lambda()

}