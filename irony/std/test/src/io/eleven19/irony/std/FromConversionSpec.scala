package io.eleven19.irony.std
import zio.test.*
import scala.Conversion
import io.eleven19.irony.std.From.given
object FromConversionSpec extends ZIOSpecDefault {
  def spec = suite("FromConversionSpec")(
    test("FromConversionSpec") {
      val input: Int = 42
      val expected: Long = 42L
      given conversion: Conversion[Int, Long] = _.toLong
      val actual: Long = input.convert
      assertTrue(actual == expected)
    }
  )
}
