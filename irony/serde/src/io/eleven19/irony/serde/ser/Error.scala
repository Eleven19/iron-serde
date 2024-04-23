package io.eleven19.irony.serde.ser
import io.eleven19.irony.std 

// TODO: Make sure to define a Debug and Displat typeclass and make Self require instances of those typeclasses
trait ErrorModule[Self]:
  def custom[T](msg: T): Self

trait ErrorMethods:
  def message: String

trait Error:
  def message: String
object Error:  
  def custom[T](msg: T): Error = new Error:
    def message = msg.toString