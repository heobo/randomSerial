import com.yuxiaobo.util.RandomSerial

object Main {

  def main(args: Array[String]) {
    val max = 1000
    val random = new RandomSerial(max, 10, 80)
    val set = 0.until(max).map(random.get).toSet
    println(set.size)
    set.foreach(println)
  }
}
