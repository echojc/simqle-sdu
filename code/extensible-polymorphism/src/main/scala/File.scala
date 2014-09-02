
case class File(name: String, size: Long)

object File {
  implicit val size = new Size[File] {
    def size(f: File): Int = f.size.toInt
  }
}
