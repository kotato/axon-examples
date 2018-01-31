package com.kotato.shared

import com.nhaarman.mockito_kotlin.mock
import org.mockito.Mockito
import org.mockito.stubbing.Answer
import org.mockito.stubbing.Stubber
import java.util.Arrays

class MockitoHelper {
    companion object {
        val THROW_ON_UNDEFINED_ARGS: Answer<Any> = Answer { invocation ->
            throw IllegalArgumentException(
                    "Calling a mock <${invocation.method.declaringClass.simpleName}.${invocation.method.name}> with undefined arguments: ${Arrays.toString(invocation.arguments)}")
        }

        inline fun <reified T : Any> mockObject(): T {
            return mock<T>(defaultAnswer = THROW_ON_UNDEFINED_ARGS)
        }

        fun doThrow(toBeThrown: Throwable): Stubber = Mockito.doThrow(toBeThrown)!!
    }
}