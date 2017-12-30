package com.kotato.shared.transaction

import org.springframework.transaction.annotation.Transactional

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Transactional("readModelPlatformTransactionManager")
annotation class ReadModelTransaction