package com.yuxiaobo.util

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

import scala.annotation.tailrec

/**
  * Created by yu on 16-10-21.
  */
object Util {

  def getNumberOrder(index: BigInt, size: Int): List[Int] = {
    @tailrec
    def _loop(result: List[Int], remain: List[Int], index: BigInt, size: Int): List[Int] = {
      if (size <= 0) result
      else {
        val dg: BigInt = factorial(size - 1)
        val i: Int = (index / dg).intValue()
        _loop(result :+ remain(i), remain.take(i) ++ remain.drop(i + 1), index.mod(dg), size - 1)
      }
    }

    if (index < 0 || size > 32 || index > factorial(size))
      throw new IllegalArgumentException("size must less than 32, and index less than factorial(size) " + index + "[" + size + "]")
    logger.debug("get number order for " + index + ", " + size)
    _loop(List[Int](), 0.until(size).toList, index, size)
  }


  def factorial(n: Int): BigInt = {
    @tailrec
    def _loop(n: Int, acc: BigInt): BigInt = if (n <= 1) acc else _loop(n - 1, acc * n)
    _loop(n, 1)
  }


  def md5(source: Array[Byte]): Array[Byte] = MessageDigest.getInstance("MD5").digest(source)

  def md5Hex(source: Array[Byte]): String = toHexString(md5(source))

  def toHexString(bytes: Array[Byte]): String = DatatypeConverter.printHexBinary(bytes).toUpperCase
}