
case class Person(name: String, weight: Float)

object Person {
  implicit val size = new Size[Person] {
    def size(p: Person): Int = Math.round(p.weight)
  }
}
