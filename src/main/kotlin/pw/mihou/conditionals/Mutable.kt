package pw.mihou.conditionals

import kotlin.reflect.KProperty

class Mutable<T> internal constructor(defaultValue: T, private val conditions: Conditions<T>) {
    private var value: T = defaultValue

    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): T {
        return value
    }

    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: T,
    ) {
        for (condition in conditions.conditions) {
            if (!condition.predicate(value)) {
                throw condition.exception(property.name, value)
            }
        }
        this.value = value
    }
}

/**
 * Creates a [Mutable] that is delegatable to a property to ensure that any values that will be set to
 * it will always be validated with the given conditions provided in the modifier.
 *
 * @param defaultValue the default value, this is not validated by the conditions.
 * @param conditionsModifier the modifier used to construct the conditions needed to follow for the
 * following mutations after.
 * @return a [Mutable] that is delegatable to a property that will validate any values set to it.
 */
@Suppress("UNUSED")
fun <T> mutable(
    defaultValue: T,
    conditionsModifier: Conditions<T>.() -> Unit,
): Mutable<T> {
    val conditions = Conditions<T>()
    conditionsModifier(conditions)
    return Mutable(defaultValue, conditions)
}
