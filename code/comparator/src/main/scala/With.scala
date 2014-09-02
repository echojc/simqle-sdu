object With extends App {

  trait Comparator[T] {
    def compare(a: T, b: T): Int
  }

  case class User(id: Int, name: String)

  val users = List(
    User(1, "Bob"),
    User(2, "Zoo"),
    User(3, "Alice"),
    User(4, "Cat")
  )

  implicit val comparator = new Comparator[User] {
    def compare(a: User, b: User): Int =
      a.name.compareTo(b.name)
  }

  println {
    sort(users)
    //sort(users, comparator)
  }

  def sort[T](list: List[T])(implicit comparator: Comparator[T]): List[T] =
    list sortWith { (a, b) ⇒
      // returns true if a < b
      comparator.compare(a, b) < 0
    }
}
