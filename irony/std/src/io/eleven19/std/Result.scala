package io.eleven19.irony.std

opaque type Result[+Err,+A] <: Either[Err, A] = Either[Err,A]
object Result:
  def fromEither[Err,A](value: Either[Err,A]): Result[Err,A] = value

  def ok[A](value: A): Result[Nothing,A] = Right(value)
  def error[Err](value: Err): Result[Err,Nothing] = Left(value)

