package io.eleven19.irony.std

trait Error[+Self]:
  type Error <: Self
  def source: Option[Error] = None

object Error:
    given [A] : Error[A] = new Error[A]{}