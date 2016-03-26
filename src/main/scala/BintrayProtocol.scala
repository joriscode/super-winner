import spray.json._

import org.joda.time.DateTime
import org.joda.time.format.{ISODateTimeFormat, DateTimeFormatter}

case class BintraySearch(
  sha1: String,
  `package`: String,
  name: String,
  path: String,
  size: Int,
  version: String,
  owner: String,
  repo: String,
  created: DateTime
)

trait BintrayProtocol extends DefaultJsonProtocol {
  implicit object DateTimeFormat extends RootJsonFormat[DateTime] {
    val formatter = ISODateTimeFormat.dateTimeParser
    def write(obj: DateTime): JsValue = JsString(formatter.print(obj))
    def read(json: JsValue): DateTime = json match {
      case JsString(s) => 
        try {
          formatter.parseDateTime(s)
        }
        catch { case scala.util.control.NonFatal(e) => error(e.toString) }
      case _ => error(json.toString())
    }

    def error(v: Any): DateTime = {
      val example = formatter.print(0)
      deserializationError(f"'$v' is not a valid date value. Dates must be in compact ISO-8601 format, e.g. '$example'")
    }
  }
  implicit val bintraySearchFormat = jsonFormat9(BintraySearch)
}