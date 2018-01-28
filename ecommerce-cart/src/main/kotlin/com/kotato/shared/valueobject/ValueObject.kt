package com.kotato.shared.valueobject

import com.kotato.shared.noarg.NoArgsConstructor
import javax.persistence.Embeddable

@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@NoArgsConstructor
@Embeddable
annotation class ValueObject