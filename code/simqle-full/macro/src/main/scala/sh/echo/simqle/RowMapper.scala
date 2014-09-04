package sh.echo.simqle

import java.sql.ResultSet
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

import shapeless._

trait RowMapper[T] {
  def get(rs: ResultSet): T
}

object RowMapper extends RowMapperLowerPriorityImplicits {

  implicit object IntRowMapper extends RowMapper[Int] {
    val columnMapper = implicitly[ColumnMapper[Int]]
    def get(rs: ResultSet): Int = {
      columnMapper.get(rs, 1)
    }
  }

  implicit object StringRowMapper extends RowMapper[String] {
    val columnMapper = implicitly[ColumnMapper[String]]
    def get(rs: ResultSet): String = {
      columnMapper.get(rs, 1)
    }
  }
}

trait RowMapperLowerPriorityImplicits {
  import RowMapperLowerPriorityImplicits._
  implicit def caseClassRowMapper[T]: RowMapper[T] = macro materializeCaseClassRowMapper[T]
}

object RowMapperLowerPriorityImplicits {

  def materializeCaseClassRowMapper[T: c.WeakTypeTag](c: Context): c.Expr[RowMapper[T]] = {
    import c.universe._
    println("################")

    val tpe = weakTypeOf[T]

    println {
      tpe
    }

    c.Expr[RowMapper[T]] { q"""
      42
    """ }
  }
}























  //def materializeCaseClassRowMapper[T: c.WeakTypeTag](c: Context): c.Expr[RowMapper[T]] = {
  //  import c.universe._
  //  val tpe = weakTypeOf[T]

  //  val fields = tpe.decls.collectFirst {
  //    case m: MethodSymbol if m.isPrimaryConstructor ⇒ m
  //  }.get.paramLists.head

  //  val mappedColumns = fields map { field ⇒
  //    val name = field.name
  //    val decoded = name.decodedName.toString
  //    val fieldTpe = tpe.decl(name).typeSignature
  //    q"implicitly[ColumnMapper[$fieldTpe]].get(rs, $decoded)"
  //  }

  //  c.Expr[RowMapper[T]] { q"""
  //    new RowMapper[$tpe] {
  //      def get(rs: java.sql.ResultSet): $tpe =
  //        new $tpe(..$mappedColumns)
  //    }
  //  """ }
  //}
