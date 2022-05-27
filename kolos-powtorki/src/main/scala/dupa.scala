
// val data = io.Source.fromFile("world-happiness-report.csv")
// 		   .getLines().toList

// case class CountryData(
// 	CountryName : String,
// 	Year : String,
// 	LadderScore : String,
// 	LogGDPPerCapita : String,
// 	SocialSupport : String,
// 	HealthyLifeExpectancyAtBirth : String,
// 	FreedomToMakeLifeChoices : String,
// 	Generosity : String,
// 	PerceptionsOfCorruption : String,
// 	PositiveAffect : String,
// 	NegativeAffect : String
// )

// @annotation.tailrec
// def countPassa(list:List[(Int,Int)], mainAcum:List[Int], smallAcum:List[(Int,Int)]): Int = {
// 	list match {
// 		case Nil => ((smallAcum.length)::mainAcum).max
// 		case hd::tl => smallAcum match {
// 			case Nil => countPassa(tl, mainAcum, hd::smallAcum)
// 			case acumHD::acumTL if acumHD._2 == hd._1 => countPassa(tl, mainAcum, hd::smallAcum)
// 			case acumHD::acumTL if acumHD._2 != hd._1 => countPassa(list, (smallAcum.length)::mainAcum, List())
// 		}
// 	}
// }

// val result = data.map(n => n.split(",").toList)
// 			.filter(n => n.length == 11 && n.forall(m => m != ""))
// 			.map(n => n match {
// 				case List(a,b,c,d,e,f,g,h,i,j,k) => CountryData(a,b,c,d,e,f,g,h,i,j,k)
// 			}).groupMap(n => n.CountryName)(n => (n.Year.toInt, n.LadderScore.toDouble))
// 			.filter(n => n._2.length > 1).mapValues(n => n.sortBy(m => m._1).sliding(2).toList.collect({
// 				case List((year1,ladder1), (year2,ladder 2)) if year1 == year2-1 && ladder1 < ladder2 => (year1,year2)
// 			})).toList.map(n => (n._1, countPassa(n._2, List(), List())))
// 			.groupMap(n => n._2)(n => n._1).maxBy(n => n._1)._2

// @main
// def app: Unit = {
// println(result)
// }
// s


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

def countRepeating(word: String)(set: Set[Char]): Map[Char,Int] = {
    val wordList = word.toCharArray.toList.groupBy(x => x).map(el => el._1 -> el._2.length)
    val keys = wordList.keySet
    set.foldLeft(Map(): Map[Char,Int])((acc: Map[Char,Int],curr) => {
        if(keys.contains(curr)) {
            acc + (curr -> wordList.get(curr).get)
        } else {
            acc + (curr -> 0)
        }
    })
}

@main
def app: Unit = {
    println(countRepeating("spaghetti")(Set('a', 'b', 'u', 't', 'p')))
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

@annotation.tailrec
def onpRec(tokeny: List[String], acc: Double, tempAcc: List[Double]): Double = {
    tokeny match {
        case h::t => h.toCharArray.head match {
            case '+' => onpRec(t,acc+add(tempAcc),List())
            case _ => onpRec(t,acc,h.toCharArray.head.toDouble :: tempAcc)
        }
        case Nil =>acc
    }
}

def add(l: List[Double]): Double = {
    l.foldLeft(0.0)((acc,curr)=>curr+acc)
}

def onp(tokeny: List[String]): Double = {
    onpRec(tokeny,0.0,List())
}

@main
def notacja: Unit = {
    println(onp(List("2", "3", "+"))-96)
}




// def findPairs(n: Int): Set[(Int,Int)] = {
// 	def isPrime(x:Int): Boolean = {
// 		x match {
// 			case 1 => false 
// 			case 2 => true
// 			case _ => (2 until x).toList.forall(y => x % y != 0)
// 		}
// 	}
// 	(2 until 2*n).toSet.collect({
// 		case m if isPrime(m) && isPrime(2*n-m) && m <= n => (m, 2*n-m)
// 	})
// }

// @main
// def app: Unit = {
// 	println(findPairs(100))
// }
