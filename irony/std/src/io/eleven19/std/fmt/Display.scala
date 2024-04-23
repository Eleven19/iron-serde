package io.eleven19.std.fmt

import io.eleven19.irony.std.Result

trait Display[Self]:
    def fmt(self: Self, formatter:Formatter): Result[Error,String]
