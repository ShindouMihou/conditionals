package pw.mihou.conditionals.exceptions

class InvalidValueException(property: String, value: Any) : RuntimeException(
    "Cannot set the value of '$property' to '$value' because it does not " +
        "meet the conditions required by the mutable property.",
)
