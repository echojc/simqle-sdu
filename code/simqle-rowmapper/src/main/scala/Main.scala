import java.sql.DriverManager
import java.sql.ResultSet
import scala.collection.mutable

import sh.echo.simqle._

object Main extends App {

  Class.forName("org.h2.Driver")
  val db = Db("jdbc:h2:mem:demo;MODE=MYSQL;DB_CLOSE_DELAY=-1")

  db.withConnection { conn ⇒
    val stmt = conn.createStatement()
    stmt.execute("create table users (id int, name varchar(255))")
    stmt.execute("insert into users (id, name) values (1, 'Alice'), (2, 'Bob')")
    stmt.close()
  }

  case class User(id: Int, name: String)

  println {
    query("select * from users") { rs ⇒
      val id = rs.getInt("id")
      val name = rs.getString("name")
      User(id, name)
    }
  }

  def query[T](sql: String)(rm: ResultSet ⇒ T): List[T] =
    db.withConnection { conn ⇒
      val stmt = conn.createStatement()
      val rs = stmt.executeQuery(sql)
      val buffer = mutable.Buffer.empty[T]
      while (rs.next()) buffer += rm(rs)
      rs.close()
      stmt.close()
      buffer.toList
    }

  //trait RowMapper[T] {
  //  def get(rs: ResultSet): T
  //}

  //implicit val userRowMapper = new RowMapper[User] {
  //  def get(rs: ResultSet): User = {
  //    val id = rs.getInt("id")
  //    val name = rs.getString("name")
  //    User(id, name)
  //  }
  //}

  //println {
  //  query("select * from users")
  //}

  //def query[T](sql: String)(implicit rm: RowMapper[T]): List[T] =
  //  db.withConnection { conn ⇒
  //    val stmt = conn.createStatement()
  //    val rs = stmt.executeQuery(sql)
  //    val buffer = mutable.Buffer.empty[T]
  //    while (rs.next()) buffer += rm.get(rs)
  //    rs.close()
  //    stmt.close()
  //    buffer.toList
  //  }
}
