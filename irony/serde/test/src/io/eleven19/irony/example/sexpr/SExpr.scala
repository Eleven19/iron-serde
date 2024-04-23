package io.eleven19.irony.example.sexpr
import io.eleven19.irony.std.Result
import io.eleven19.irony.serde.ser.{Serializer, Serialize}

enum SAtom:
  case Bool(value: Boolean)
  case Str(value: String)  
  case Num(value: SNumber)

enum SNumber:
  case Int(value: Int)
  case Float(value: Float)  

enum SExpr:
  case Atom(atom: SAtom)
  case Cons(car: SExpr, cdr: SExpr)
  case SNil

class SExprSerializer() extends Serializer:
  type Ok = SExpr 
  type Error 
  
object SExprSerializer:
  final val default = SExprSerializer() 