package pw.mihou.conditionals

import pw.mihou.conditionals.exceptions.InvalidValueException

typealias ConditionPredicate<T> = (value: T) -> Boolean
typealias ConditionException<T> = (property: String, value: T) -> Exception

data class Condition<T> internal constructor(val predicate: ConditionPredicate<T>, val exception: ConditionException<T>)

class Conditions<T> internal constructor() {
    internal val conditions = mutableListOf<Condition<T>>()

    private fun defaultException(
        property: String,
        value: T,
    ): Exception {
        return InvalidValueException(property, value ?: "'null'")
    }

    fun ensure(
        exception: ConditionException<T> = this::defaultException,
        condition: ConditionPredicate<T>,
    ) {
        conditions.add(
            Condition(
                predicate = condition,
                exception = exception,
            ),
        )
    }

    fun not(
        value: Any,
        exception: ConditionException<T> = this::defaultException,
    ) {
        ensure(exception) {
            value != it
        }
    }
}
