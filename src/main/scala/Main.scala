import java.io.{File, FileWriter}
import scala.io.Source
import scala.util.{Using, Try}
import Etl.*
import FileUtils.*
import EtlConfig.*
import EtlError.*
import pureconfig.*
import pureconfig.generic.derivation.default.*

# @main def run: Unit =
def run: Unit =
  for config <- ConfigSource.default.load[EtlConfig]
  yield config.etlImpl match
    case EtlImpl.StringImpl => printResult(config, Etl.StringImpl)
    case EtlImpl.IntImpl    => printResult(config, Etl.IntImpl)
    case EtlImpl.JsonImpl    => printResult(config, Etl.JsonImpl)

private def printResult[A, B](config: EtlConfig, etlImpl: Etl[A, B]): Unit =
  etl(config, etlImpl) match
    case Left(error) => println(s"Failure: $error")
    case Right(_)    => println(s"Success!")
