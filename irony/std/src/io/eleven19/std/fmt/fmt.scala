package io.eleven19.std.fmt

final case class Formatter(fill: FillChar)

opaque type FillChar <: Int = Int
object FillChar:
  def apply(value: Int): FillChar = value

trait Error