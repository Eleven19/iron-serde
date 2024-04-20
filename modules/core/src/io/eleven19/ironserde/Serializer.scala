package io.eleven19.ironserde

trait Serializer {
  type Ok
  type Error <: io.eleven19.ironserde.Error

  def serializeBoolean(value: Boolean): Result[Error, Ok]
  def serializeByte(value: Byte): Result[Error, Ok]
  def serializeInt8(value: Byte): Result[Error, Ok] = serializeByte(value)
  def serializeShort(value: Short): Result[Error, Ok]
  def serializeInt16(value: Short): Result[Error, Ok] = serializeShort(value)
  def serializeInt(value: Int): Result[Error, Ok]
  def serializeInt32(value: Int): Result[Error, Ok] = serializeInt(value)
  def serializeLong(value: Long): Result[Error, Ok]
  def serializeInt64(value: Long): Result[Error, Ok] = serializeLong(value)
  def serializeFloat(value: Float): Result[Error, Ok]
  def serializeFloat32(value: Float): Result[Error, Ok] = serializeFloat(value)
  def serializeDouble(value: Double): Result[Error, Ok]
    def serializeFloat64(value: Double): Result[Error, Ok] = serializeDouble(value)
  def serializeChar(value: Char): Result[Error, Ok]
  def serializeString(value: String): Result[Error, Ok]
  def serializeNone(): Result[Error, Ok]
  def serializeSome[A: Serialize](value: A): Result[Error, Ok]
  def serializeUnit(): Result[Error, Ok]
  def serializeUnitStruct(name: String): Result[Error, Ok]
}

object Serializer:
  type Aux[TOk, TError] = Serializer { type Ok = TOk; type Error = TError }
  trait Generic[TOk, TError] extends Serializer:
    type Ok = TOk
    type Error = TError
    