package sh.echo.simqle

import java.sql.ResultSet

trait ColumnMapper[T] {
  def get(rs: ResultSet, index: Int): T
  def get(rs: ResultSet, column: String): T
}

object ColumnMapper {

  implicit object IntColumnMapper extends ColumnMapper[Int] {
    def get(rs: ResultSet, index: Int): Int = {
      rs.getInt(index)
    }
    def get(rs: ResultSet, column: String): Int = {
      rs.getInt(column)
    }
  }

  implicit object StringColumnMapper extends ColumnMapper[String] {
    def get(rs: ResultSet, index: Int): String = {
      rs.getString(index)
    }
    def get(rs: ResultSet, column: String): String = {
      rs.getString(column)
    }
  }
}
