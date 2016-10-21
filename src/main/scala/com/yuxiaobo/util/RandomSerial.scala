package com.yuxiaobo.util

import scala.annotation.tailrec

/**
  * Created by yu on 16-9-16.
  */
case class RandomSerial(max: Int, deep: Int, seed: Long) {

  val length: Int = Integer.SIZE - Integer.numberOfLeadingZeros(max);

  val distance: Int = {
    @tailrec
    def _try(tryDistance: Int, tryTimes: Int): Int =
      if (tryDistance % max != 0) tryDistance
      else _try(Util.md5Hex(("" + tryTimes + seed + max + deep).getBytes).hashCode, tryTimes + 1)

    _try(0, 0) & Integer.MAX_VALUE
  }


  val randomSerials: Array[Option[PowerOfTwoRandomSerial]] = {
    0.until(length).map { i =>
      val power: Int = length - i - 1
      if ((max >> power & 1) == 0) None
      else Some(PowerOfTwoRandomSerial(power, ((Util.md5Hex(("" + distance + power).getBytes).hashCode & 0x00000000ffffffffl) << (Integer.SIZE / 2) ^ distance) << (Integer.SIZE / 2) ^ distance))
    }.to[Array]
  }

  def get(index: Int): Int = {
    @tailrec
    def _value(valueIndex: Int, bitIndex: Int, base: Int, next: Int): Int =
      if (randomSerials(bitIndex).isEmpty) _value(valueIndex, bitIndex + 1, base, next)
      else if (valueIndex < base + next + (1 << (length - bitIndex - 1)))
        randomSerials(bitIndex).get.get(valueIndex % (1 << randomSerials(bitIndex).get.power)) + base + next // exit recursion
      else
        _value(valueIndex, bitIndex + 1, base + next, 1 << (length - bitIndex - 1))

    @tailrec
    def privateGet(index: Int, deep: Int): Int = {
      if (deep <= 0) index
      else privateGet(((1l * distance * deep + _value(index, 0, 0, 0)) % max).toInt, deep - 1)
    }

    if (index >= max) throw new IllegalArgumentException("index must less than max, but " + index + " >= " + max)
    privateGet(index, deep)
  }


  override def toString: String = {
    val builder: StringBuilder = new StringBuilder
    for (serial <- randomSerials) builder.append(if (serial != null) "1" else "0")
    "power: " + length + ", boolean array: " + builder.toString
  }
}


private case class PowerOfTwoRandomSerial(power: Int, seed: Long) {

  val (not, order): (Array[Int], Array[Int]) = {
    logger.debug("new randomSerial: " + power + ", " + seed)
    val mask: Int = Util.md5Hex(("" + seed + power).getBytes).hashCode
    val f: BigInt = Util.factorial(power)
    val m: BigInt = (((f << 4) + seed) ^ mask) % f
    val index: BigInt = f / 2 - m / 3 + m / 4 - f / 5 + f / 6 - m / 7 + m / 8
    logger.debug("mask: " + Integer.toBinaryString(mask))
    (0.until(Integer.SIZE).map(mask >> _ & 1).to[Array], Util.getNumberOrder(index, power).toArray)
  }
  logger.debug("serial: " + order.deep.mkString(" "))

  def get(index: Int): Int = 0.until(power)
    .filter(i => ((index >> i & 1) ^ not(i)) != 0)
    .map(1 << order(_))
    .sum
}


