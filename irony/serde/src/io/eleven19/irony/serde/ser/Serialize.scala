package io.eleven19.irony.serde.ser
import io.eleven19.irony.std.Result 

trait Serialize[-Self]:
  def serialize[S <: Serializer](
      value: Self,
      serializer: S
  ): Result[serializer.Error, serializer.Ok]

object Srialize:
    def apply[A:Serialize](using serialize:Serialize[A]) = serialize

    given Serialize[Boolean] with
        def serialize[S <: Serializer](
            value: Boolean,
            serializer: S
        ): Result[serializer.Error, serializer.Ok] = serializer.serializeBoolean(value)

    given Serialize[Byte] with
        def serialize[S <: Serializer](
            value: Byte,
            serializer: S
        ): Result[serializer.Error, serializer.Ok] = serializer.serializeByte(value)

    given Serialize[Short] with
        def serialize[S <: Serializer](
            value: Short,
            serializer: S
        ): Result[serializer.Error, serializer.Ok] = serializer.serializeShort(value)

    given Serialize[Int] with
        def serialize[S <: Serializer](
            value: Int,
            serializer: S
        ): Result[serializer.Error, serializer.Ok] = serializer.serializeInt(value)

    given Serialize[Long] with
        def serialize[S <: Serializer](
            value: Long,
            serializer: S
        ): Result[serializer.Error, serializer.Ok] = serializer.serializeLong(value)

    given Serialize[Float] with
        def serialize[S <: Serializer](
            value: Float,
            serializer: S
        ): Result[serializer.Error, serializer.Ok] = serializer.serializeFloat(value)

    given Serialize[Double] with
        def serialize[S <: Serializer](
            value: Double,
            serializer: S
        ): Result[serializer.Error, serializer.Ok] = serializer.serializeDouble(value)

    given Serialize[Char] with
        def serialize[S <: Serializer](
            value: Char,
            serializer: S
        ): Result[serializer.Error, serializer.Ok] = serializer.serializeChar(value)

    given Serialize[String] with
        def serialize[S <: Serializer](
            value: String,
            serializer: S
        ): Result[serializer.Error, serializer.Ok] = serializer.serializeString(value)

    given Serialize[Unit] with
        def serialize[S <: Serializer](
            value: Unit,
            serializer: S
        ): Result[serializer.Error, serializer.Ok] = serializer.serializeUnit()

    given [A:Serialize]: Serialize[Option[A]] with
        def serialize[S <: Serializer](
            value: Option[A],
            serializer: S
        ): Result[serializer.Error, serializer.Ok] = value match
            case Some(v) => serializer.serializeSome(v)
            case None => serializer.serializeNone()    