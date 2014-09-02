object Main extends App {

  val person = Person("Bob", 70.2f)
  val file = File("downunder.scala", 1337)

  println {
    person + " : " + size(person)
  }

  println {
    file + " : " + size(file)
  }

  //val string = "some string"
  //println {
  //  string + " : " + size(string)
  //}

  def size[T](sizable: T)(implicit sizer: Size[T]): Int =
    sizer.size(sizable)
}
