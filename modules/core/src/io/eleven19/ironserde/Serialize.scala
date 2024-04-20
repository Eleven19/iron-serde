package io.eleven19.ironserde

trait Serialize[-Self] {
  def serialize[S <: Serializer](
      value: Self,
      serializer: S
  ): Result[serializer.Error, serializer.Ok]
}