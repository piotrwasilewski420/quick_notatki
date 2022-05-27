## ZADANIE 2
Korzystając z metod oferowanych przez kolekcje iterowalne napisz funkcję
```scala
def zliczPodane(tekst: String)(znaki: Set[Char]): Map[Char, Int]
```
zliczającą podane znaki w podanym tekście.

```scala
println(zliczPodane("spaghetti")(Set('a', 'b', 'u', 't', 'p'))) // Map(t -> 2, u -> 0, a -> 1, b -> 0, p -> 1)
```