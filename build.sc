import mill._
import mill.scalalib._
import mill.scalajslib._
import mill.scalanativelib._
import mill.scalalib.publish._
import mill.scalalib.scalafmt._
import mill.scalalib.publish.PublishInfo
import $ivy.`io.chris-kipp::mill-ci-release::0.1.9`
import $ivy.`io.eleven19.mill::mill-crossbuild::0.3.0`
import io.eleven19.mill.crossbuild
import io.eleven19.mill.crossbuild.{CrossPlatform, PlatformAwareScalaProject}
import io.kipp.mill.ci.release.CiReleaseModule

object irony extends CrossPlatform {

  object serde extends CrossPlatform {
    trait Shared extends ScalaProject with PublishedProject {
      def platformModuleDeps = Seq(std)
      def ivyDeps =
        Agg(Deps.io.github.kitlangton.neotype)
    }

    object jvm extends Shared with ScalaJvmProject {
      object test extends ScalaTests with ZioTestProject {}
    }

    object js extends Shared with ScalaJSProject {
      object test extends ScalaJSTests with ZioTestProject {}
    }
  }

  object std extends CrossPlatform {
    trait Shared extends ScalaProject with PublishedProject {
      def ivyDeps = Agg(Deps.io.github.kitlangton.neotype)
    }

    object jvm extends Shared with ScalaJvmProject {
      object test extends ScalaTests with ZioTestProject {}
    }

    object js extends Shared with ScalaJSProject {
      object test extends ScalaJSTests with ZioTestProject {}
    }

  }

  object zio extends Module {
    object json extends CrossPlatform {
      trait Shared extends ScalaProject with PublishedProject {
        def platformModuleDeps = Seq(std, serde)
        def ivyDeps = Agg(Deps.dev.zio.`zio-json`)
      }

      object jvm extends Shared with ScalaJvmProject {
        object test extends ScalaTests with ZioTestProject {}
      }

      object js extends Shared with ScalaJSProject {
        object test extends ScalaJSTests with ZioTestProject {}
      }

    }
  }
}

trait PublishedProject extends Project with CiReleaseModule with JavaModule {
  import mill.scalalib.publish._
  def packageDescription: String = s"The $artifactName package"

  def pomSettings = PomSettings(
    description = packageDescription,
    organization = "io.eleven19.ironserde",
    url = "https://github.com/Eleven19/iron-serde",
    licenses = Seq(License.`Apache-2.0`),
    versionControl = VersionControl.github("Eleven19", "iron-serde"),
    developers = Seq(
      Developer(
        "DamianReeves",
        "Damian Reeves",
        "https://github.com/damianreeves"
      )
    )
  )
}

trait Project extends JavaModule {}

trait ZioTestProject extends TestModule.ZioTest {
  override def ivyDeps = Agg(
    Deps.dev.zio.`zio-test`,
    Deps.dev.zio.`zio-test-sbt`,
    Deps.dev.zio.`zio-test-magnolia`
  )

}

trait ScalaProject extends Project with PlatformAwareScalaProject {
  def scalaVersion = Versions.scala
}

trait ScalaJvmProject extends crossbuild.ScalaJvmProject with ScalaProject {}

trait ScalaJSProject extends ScalaProject with crossbuild.ScalaJsProject {
  def scalaJSVersion = Versions.scalaJS
}

trait ScalaNativeProject
    extends ScalaProject
    with crossbuild.ScalaNativeProject {
  def scalaNativeVersion = Versions.scalaNative
}

//---------------------------------------------------------------------
// Dependencies and Versions
//---------------------------------------------------------------------
object Deps {
  case object com {
    case object github {
      case object `j-mie6` {
        val parsley = ivy"com.github.j-mie6::parsley::${Versions.parsley}"
      }
    }
  }
  case object dev {
    case object zio {
      val `izumi-reflect` =
        ivy"dev.zio::izumi-reflect::${Versions.`izumi-reflect`}"
      val zio: Dep = ivy"dev.zio::zio::${Versions.zio}"
      val `zio-cli` = ivy"dev.zio::zio-cli::${Versions.`zio-cli`}"
      val `zio-config` = config()
      val `zio-interop-cats` =
        ivy"dev.zio::zio-interop-cats::${Versions.`zio-interop-cats`}"
      val `zio-json`: Dep = ivy"dev.zio::zio-json::${Versions.`zio-json`}"
      val `zio-json-golden` =
        ivy"dev.zio::zio-json-golden::${Versions.`zio-json`}"
      val `zio-parser` = ivy"dev.zio::zio-parser::${Versions.`zio-parser`}"
      val `zio-nio` = ivy"dev.zio::zio-nio::${Versions.`zio-nio`}"
      val `zio-prelude` = prelude()
      val `zio-prelude-macros` = prelude.macros
      val `zio-process` = ivy"dev.zio::zio-process::${Versions.`zio-process`}"
      val `zio-streams` = ivy"dev.zio::zio-streams::${Versions.zio}"
      val `zio-test` = ivy"dev.zio::zio-test::${Versions.zio}"
      val `zio-test-magnolia` = ivy"dev.zio::zio-test-magnolia::${Versions.zio}"
      val `zio-test-sbt` = ivy"dev.zio::zio-test-sbt::${Versions.zio}"

      object config {
        def apply(): Dep = ivy"dev.zio::zio-config::${Versions.`zio-config`}"
        val magnolia =
          ivy"dev.zio::zio-config-magnolia::${Versions.`zio-config`}"
        val refined = ivy"dev.zio::zio-config-refined::${Versions.`zio-config`}"
        val typesafe =
          ivy"dev.zio::zio-config-typesafe::${Versions.`zio-config`}"
      }

      case object prelude {
        def apply(): Dep = ivy"dev.zio::zio-prelude::${Versions.`zio-prelude`}"
        val macros = ivy"dev.zio::zio-prelude-macros::${Versions.`zio-prelude`}"
      }

      case object schema {
        val `avro` = ivy"dev.zio::zio-schema-avro::${Versions.`zio-schema`}"
        val `bson` = ivy"dev.zio::zio-schema-bson::${Versions.`zio-schema`}"
        val `core` = ivy"dev.zio::zio-schema-core::${Versions.`zio-schema`}"
        val `derivation` =
          ivy"dev.zio::zio-schema-derivation::${Versions.`zio-schema`}"
        val `json` = ivy"dev.zio::zio-schema-json::${Versions.`zio-schema`}"
        val `msg-pack` =
          ivy"dev.zio::zio-schema-msg-pack::${Versions.`zio-schema`}"
      }
    }
  }

  case object io {
    case object getkyo {
      val `kyo-core` = ivy"io.getkyo::kyo-core::${Versions.kyo}"
      val `kyo-direct` = ivy"io.getkyo::kyo-direct::${Versions.kyo}"
      val `kyo-sttp` = ivy"io.getkyo::kyo-sttp::${Versions.kyo}"

    }
    case object github {
      case object iltotore {
        val iron = ivy"io.github.iltotore::iron::${Versions.iron}"
      }
      case object kitlangton {
        val `neotype` = ivy"io.github.kitlangton::neotype::${Versions.neotype}"

      }
    }
  }

  case object org {
    case object wvlet {
      case object airframe {
        val `airframe-surface` =
          ivy"org.wvlet.airframe::airframe-surface:${Versions.airframe}"
      }
    }
  }
}

object Versions {
  val airframe = "24.4.0"
  val iron = "2.5.0"
  val `izumi-reflect` = "2.3.8"
  val kyo = "0.9.2"
  val neotype = "0.2.5"
  val parsley = "4.5.1"
  val scala = "3.3.3"
  val scalaJS = "1.16.0"
  val scalaNative = "0.5.1"
  val zio = "2.0.21"
  val `zio-cli` = "0.5.0"
  val `zio-config` = "4.0.1"
  val `zio-interop-cats` = "23.1.0.1"
  val `zio-json` = "0.6.2"
  val `zio-nio` = "2.0.2"
  val `zio-parser` = "0.1.9"
  val `zio-prelude` = "1.0.0-RC23"
  val `zio-process` = "0.7.2"
  val `zio-schema` = "0.4.12"
}
