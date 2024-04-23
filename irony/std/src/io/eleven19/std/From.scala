package io.eleven19.irony.std

import java.lang.annotation.Target

trait From[-Source, +Target] extends Function[Source, Target]:
  def apply(source: Source): Target
  inline final def from(source: Source): Target = this(source)
  extension (source:Source ) 
    def convert:Target = this(source)
    def into:Target = this(source)

object From:
    def apply[Source, Target](using from: From[Source, Target]) = from

    given From[Int,Long] = _.toLong
    given From[Int,Float] = _.toFloat
    given From[Int,Double] = _.toDouble

    given From[Boolean,Int] = if _ then 1 else 0
    given From[Boolean,Long] = if _ then 1L else 0L
    given From[Boolean,Float] = if _ then 1.0f else 0.0f
    given From[Boolean,Double] = if _ then 1.0 else 0.0

    given [Source,Target](using conversion:Conversion[Source,Target]): From[Source, Target] with
        def apply(source: Source): Target = conversion(source)

