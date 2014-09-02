trait Size[T] {
  def size(a: T): Int
}

object Size {
  //implicit val stringSize = new Size[String] {
  //  def size(str: String): Int = str.length
  //}
}
