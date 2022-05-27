import scala.annotation.tailrec
import scala.io.Source._
/*
    Wykorzystując rekurencję (wyłącznie ogonową) zdefiniuj funkcję:

        def countResults[A,B,C](l1: List[A], l2: List[B])(f: (A, B) => C): Set[(C, Int)]

    która zaaplikuje funkcję „f” do elementów l1(i), l2(i), gdzie 0 <= i < min(l1.length, l2.length)
    oraz zwróci zbiór składający się z par:

        (wynik funkcji f, liczba par dla których f zwróciła dany wynik).

    Przykładowo dla:

        countResults(List(1,2,3), List(4,5,4,6))(_+_) == Set((5,1), (7,2))

    ponieważ: 1+4 = 5, 2+5 = 7, 4+3 = 7, 6 pomijamy bo to „nadmiarowy” element w drugiej z list.

    W rozwiązaniu należy skorzystać z mechanizmu dopasowania do wzorca (pattern matching).
    Nie używaj zmiennych ani „pętli” (while, for bez yield, foreach).
*/


@main
def zad1: Unit = {
    println(countResults(List(1,2,3), List(4,5,4,6))(_+_))
}

def countResults[A,B,C](l1: List[A], l2: List[B])(f: (A, B) => C): Set[(C, Int)] = {
    countResultsRec(l1,l2,List())(f)
    .groupBy(x => x) //output => HashMap(5 -> List(5), 7 -> List(7,7))
    .map(x => (x._1,x._2.length)) //output => HashMap((5 -> 1), (7 -> 2))
    .toSet // Set((5,1), (7,2))
}

@tailrec
def countResultsRec[A,B,C](l1: List[A], l2: List[B], acc: List[C])(f: (A, B) => C): List[C] = {
    (l1,l2) match {
        case (Nil,Nil) => acc
        case (h1::t1, Nil) => acc
        case (Nil,h2::t2) => acc
        case (h1::t1, h2::t2) => countResultsRec(t1,t2,f(h1,h2) :: acc)(f)
        // List(5,7,7) => Set((5,1),(7,2))
    }
}





/*
  Używając rekurencji ogonowej zdefiniuj funkcję:

    def pairwiseTest[A](l: List[A])(pred: (A, A) => Boolean)

  która sprawdzi, czy predykat „pred" jest spełniony dla wszystkich par
  elementów listy „l” o indeksach (i, długość(l) - i), dla i = 0.. długość(l)/2.

  Przykładowo, dla listy List(1,2,3,4,3,2,1) oraz predykatu równości, sprowadzi
  się to do następującej serii weryfikacji równości:

    l(0) == l(6)
    l(1) == l(5)
    l(2) == l(4)
    l(3) == l(3)

  Ogólnie, seria taka będzie miała postać:

    pred(l(0), l(l.length-1)) == true
    pred(l(1), l(l.length-2)) == true
    pred(l(2), l(l.length-3)) == true
    ...
    pred(l(l.length/2), l(l.length - l.length/2 - 1)) == true

  W przypadku pustej listy funkcja powinna zwrócić true

  Uwaga: w rozwiązaniu nie używaj zmiennych, ani mechanizmów imperatywnych jak pętle.
  Nie używaj też kolekcji języka Scala.
*/

@main
def zad2: Unit = {
    println(pairwiseTest(List(1,10,3,4,5,6))(_<_))
}

def pairwiseTest[A](l: List[A])(pred: (A, A) => Boolean): Boolean = {
    @tailrec
    def pairwiseTestRec[A](l: List[A],acc: Boolean=true)(pred: (A, A) => Boolean): Boolean = {
        (l,acc) match {
           case (Nil,true) => false
           case (head::Nil,true) => true
           case (head::tail,false) => false
           case (head::tail,true) => if(tail.length > 1) { 
           if(pred(head,tail.last)){
           pairwiseTestRec(tail.dropRight(1),true)(pred)
           } else {false}
        } else {
            true
        }
        }
    }
    pairwiseTestRec(l)(pred)
}

def findPairs(n: Int): Set[(Int,Int)] = {
        val res = for {
        p1 <- 1 to n*3
        p2 <- 1 to n*3
        if (Range(2, p1-1).filter(p1 % _ == 0).length == 0) // CHECK IF THE NUMBER IS PRIME !!!!!
        if (Range(2, p2-1).filter(p2 % _ == 0).length == 0) 
        if (p1 + p2 == 2 * n && p1 <= p2 )
         } yield (p1,p2)
        res.toSet
    }
    @main
    def main5(): Unit = {
        val res = findPairs(30)
        println(res)
    }


@main
def zad4: Unit = {
    val strefy: List[String] = java.util.TimeZone.getAvailableIDs.toList
    println(strefy)
}

@main
def zadYield: Unit = {
    println(yieldHelper)
}
val lista = List(1,2,3,4)
def yieldHelper: Any = {
    (for {
        i <- 1 until 3
    } yield lista(i))
}




/*
Plik temepratury.txt zawiera w pierwszej kolumnie rok oraz w kolejnych dwunastu kolumnach
średnią temperaturę za każdy miesiąc w danym roku (kolejno: styczeń, luty, marzec itd.).
Dane dotyczące każdego roku rozdzielone są pojedynczymi spacjami.

Przykładowo:

    1779 -4.9 2.2 3.8 9.5 15.4 16.4 17.9 19.5 14.7 9.3 4.1 1.4
    1780 -5.1 -4.3 4.4 5.9 14.2 17.2 19.4 17.9 13.1 9.4 2.8 -4.6
    1781 -4.0 -1.9 1.5 9.1 13.8 19.2 20.1 22.8 16.2 6.0 4.0 -3.6

Zdefiniuj funkcję:

    def maxAvgTemps(data: List[String]): Set[(Int, Double)]

która wyznaczy maksymalną średnią temperaturę dla każdego miesiąca, zwracając zbiór par w formacie (miesiąc, temperatura):

    Set((1,3.5), (2,5.1), (3,7.4), (4,13.2), (5,18.2), (6,22.4), (7,23.5), (8,23.8), (9,16.8), (10,12.6), (11,7.6), (12,3.9))

Rozwiąż to zadanie używając metod oferowanych przez kolekcje. Nie używaj zmiennych, kolekcji
mutowalnych, "pętli" (while, for bez yield, foreach) oraz nie definiuj żadnej funkcji rekurencyjnej.

*/

@main
def zad3: Unit = {
    val data = io.Source
            .fromFile("temperatury.txt")
            .getLines()
            .toList
    println(maxAvgTemps(data))
}

def maxAvgTemps(data: List[String]): Set[(Int, Double)] = {
    val temperatures = data.map(string => string.split(" ").toList.drop(1))
    val res = temperatures.foldLeft(temperatures(0))((acc,curr) => {
        acc.zip(curr).map(tuple => {
        if(tuple._1.toDouble > tuple._2.toDouble) {
            tuple._1
        } else {
            tuple._2
        }
        })
    })
    val zips = (1 to 12).zip(res).toSet
    zips.map(x => (x._1,x._2.toDouble))
}




/*
    Korzystając wyłącznie z mechanizmów kolekcji języka Scala znajdź kraj o najdłużej rosnącym wskaźniku LadderScore.
    Innymi słowy, korzystając z załączonych danych szukamy kraju, dla którego wskaźnik LadderScore najdłużej
    utrzymał „dobrą passę” (z roku na rok się zwiększał).

    Zwróć uwagę na to, że w danych mogą wystąpić „linie” z brakującymi danymi. Takie linie powinny zostać
    POMINIĘTE. Brakujące dane oznaczają, że w linii występują sekwencje postaci: ,,, przykładowo:

        Kosovo,2020,6.294,,0.792,,0.880,,0.910,0.726,0.201

    Linie takie, jako „niewiarygodne” należy pominąć (oczywiście nie zmieniając samego pliku danych)
    zanim program rozpocznie analizę.

    W razwiązaniu nie wolno uzywać zmiennych, ani konstrukcji imperatywnych, takich jak pętle
*/




// val data2 = io.Source.fromFile("world-happiness-report.csv").getLines.toList

// // def getGoodCountries(l: List[String]): List[String] ={
// def getGoodCountries(l: List[String]): Any ={

//   l
//   .map(string => {string.split(",").toList})
//   .map(list => {list.take(3)})
//   .groupBy(list => {list(0)})
//   .toList(0) //map here
//   ._2
//   .map(list => {(list(0), list(1), list(2))})
//   .sortBy(tuple => tuple._2)
//   .map(tuple => List(tuple))
//   .foldLeft((): (String,List[Double]))((acc,curr) => {
    
//   })
// }


// @main
// def zadKosovo: Unit ={
//   println(getGoodCountries(data2))
//   // println(data2(0))
// }












/*
    Korzystając wyłącznie z operacji na kolekcjach (w szczególności nie możesz uzyć rekurencji
    ani mechanizmów imperatywnych, takich jak zmienne i pętle) zdefiniuj funkcję:

        def findPairs(n: Int): Set[(Int,Int)]

    taką, że dowolnej liczby całkowitej N > 1

        findPairs(N)

    zawiera wszystkie pary postaci (p1, p2), gdzie p1 i p2 są liczbami
    pierwszymi takimi, że p1 + p2 = 2 * N oraz p1 <= p2.

*/



@main
def destutter: Unit = {
    println(destutterHelper(List('a', 'a', 'a', 'a', 'b', 'c', 'c', 'a', 'a', 'd', 'e', 'e', 'e', 'e'),List()))
}

@tailrec
def destutterHelper[A](l: List[A], acc: List[A]): List[A] = {
    (l,acc) match {
        case (Nil,acc) => acc.reverse
        case (h::t,Nil) => destutterHelper(t,h::acc)
        case (first::tail,acc) => if(first==acc(0)) {destutterHelper(tail,acc)} else {destutterHelper(tail,first::acc)}    
    }
}
// scala> compress(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))
// res0: List[Symbol] = List('a, 'b, 'c, 'a, 'd, 'e)









// scala> pack(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))
// res0: List[List[Symbol]] = List(List('a, 'a, 'a, 'a), List('b), List('c, 'c), List('a, 'a), List('d), List('e, 'e, 'e, 'e))
// P10 (*) Run-length encoding of a list.


@main
def pack: Unit = {
    val res = packHelper(List('a', 'a', 'a', 'a', 'b', 'c', 'c', 'a', 'a', 'd', 'e', 'e', 'e', 'e'),List(),List())
    println(res)

    val l1 = List(2,3,6,4,10,10,10)
    val l2 = List(1,2,3,4,5)
    
}

@tailrec
def packHelper[A](l: List[A], result: List[List[A]], currentElemAcc: List[A]): List[List[A]] = {
    (l,result,currentElemAcc) match {
        case (first::tail,res,Nil) => packHelper(tail,res,first::currentElemAcc)
        case (first::tail,res,curr) => if(first==curr(0)) {packHelper(tail,res,first::curr)}
        else {packHelper(tail,res :+ curr,first::List())}
        case (Nil,res,curr) => res :+ curr
    }
}



// scala> duplicate(List('a, 'b, 'c, 'c, 'd))
// res0: List[Symbol] = List('a, 'a, 'b, 'b, 'c, 'c, 'c, 'c, 'd, 'd)


// @main
// def duplicate[A](l: List[A],result: List[A], currResult: List[A]): List[A] = {

// }