object Main {
  def main(args: Array[String]): Unit = {
    import scala.concurrent.Await
    import scala.concurrent.duration._
    val count = 4
    val out = Await.result(BintrayFlow.runListPoms(atMost = Some(count)), 2.minute)
    // println(out)
    pprint.pprintln(out.take(count))
  }
}