## ZADANIE 1
Korzystając z metod oferowanych przez kolekcje iterowalne napisz funkcję
```scala
def stworzAutomat(
    A: Set[Char],
    Q: Set[String],
    q0: String,
    F: Set[String],
    d: Map[(String, Char), String]
): String => Boolean
```
tworzącą deterministyczny automat skończony. Nie musisz sprawdzać poprawności podanych danych.

```scala
val automat = stworzAutomat(
    Set('a', 'b'),
    Set("q0", "q1", "q2"),
    "q0",
    Set("q2"),
    Map(
        ("q0", 'a') -> "q2",
        ("q0", 'b') -> "q1",
        ("q1", 'a') -> "q1",
        ("q1", 'b') -> "q1",
        ("q2", 'a') -> "q2",
        ("q2", 'b') -> "q1"
    )
)
println(automat("")) // false
println(automat("a")) // true
println(automat("b")) // false
println(automat("aa")) // true
println(automat("ab")) // false
println(automat("ba")) // false
println(automat("bb")) // false
println(automat("aaaaa")) // true
println(automat("aaaaabaa")) // false
```