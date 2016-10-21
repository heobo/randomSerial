package com.yuxiaobo.util

/**
  * Created by yu on 16-10-21.
  */
object logger {

  val info = (any:Any) => {println(any)}

  def debug = info
}
