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
  created: DateTime,
  at: Option[Int]
)

trait BintrayProtocol extends DefaultJsonProtocol {
  implicit object DateTimeFormat extends RootJsonFormat[DateTime] {
    val parser = ISODateTimeFormat.dateTimeParser
    val formatter = ISODateTimeFormat.dateTime
    def write(obj: DateTime): JsValue = JsString(formatter.print(obj))
    def read(json: JsValue): DateTime = json match {
      case JsString(s) => 
        try {
          parser.parseDateTime(s)
        }
        catch { case scala.util.control.NonFatal(e) => error(e.toString) }
      case _ => error(json.toString())
    }

    def error(v: Any): DateTime = {
      val example = formatter.print(0)
      deserializationError(f"'$v' is not a valid date value. Dates must be in compact ISO-8601 format, e.g. '$example'")
    }
  }
  implicit val bintraySearchFormat = jsonFormat10(BintraySearch)
}