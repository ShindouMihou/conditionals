# Conditionals

A simple Kotlin library that simply adds constraints to mutable properties without the 
need to use getters or setters. This library isn't meant to be used in production, and 
was built out of boredom.

## Demo
```kotlin
fun main() {
   var test by mutable("Hello, World!") {
       not("Hello, Galaxy!")
       ensure { it.length > 5 }
   }
    
    test = "Hello, Galaxy" // Throws an InvalidValueException
    test = "Hi" // Throws an InvalidValueException
    test = "Hello, Universe!" // Works
}
```