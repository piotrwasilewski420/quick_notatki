## ZADANIE 2
Korzystając z metod oferowanych przez kolekcje iterowalne napisz funkcję
```scala
def zliczPodane(tekst: String)(znaki: Set[Char]): Map[Char, Int]
```
zliczającą podane znaki w podanym tekście.

```scala
println(zliczPodane("spaghetti")(Set('a', 'b', 'u', 't', 'p'))) // Map(t -> 2, u -> 0, a -> 1, b -> 0, p -> 1)
```

## ZADANIE 3
Korzystając z rekurencji ogonowej i dopasowywania wzorców napisz funkcję
```scala
def onp(tokeny: List[String]): Double
```
obliczającą podane wyrażenie arytmetyczne zapisane w odwrotnej notacji polskiej.
Rozwiązanie musi obsługiwać następujące operatory:
- mnożenie (`*`)
- dodawanie (`+`)
- odejmowanie (`-`)
- dzielenie (`/`)
- wartość absolutna (`abs`)

```scala
println(onp(List("2", "3", "+"))) // 5.0
println(onp(List("6", "abs"))) // 6.0
println(onp(List("-6", "abs"))) // 6.0
println(onp(List("2", "2", "-3", "abs", "+", "*"))) // 10.0
println(onp(List("4", "2", "/", "3", "-"))) // -1.0
```