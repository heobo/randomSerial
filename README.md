用于生成随机数列，范围为`[0, max-1]`的闭区间。这要特点有：

1. 不重复，`[0, max-1]`区间内的数乱序出现，每个只出现一次

2. 可重现，只要保存`max`, `deep`, `seed` 三个参数，就可重现序列

3. 重现不依赖数据库，数列每一项值都是动态计算，仅仅依赖`max`, `deep`, `seed` 三个参数。我们甚至可以把这三个参数记在脑中

4. 随机。**随机**是个伪命题，爱因斯坦说上帝不掷骰子，只要给定宇宙的所有初始状态和物理规律，就可以推导任意时刻的状态。即使是量子物理也只是因为人类的观察会干扰量子的运动，而得出“上帝亦掷骰子”的结论。此工具中，只要取值范围(`max`)和映射深度(`deep`)足够大，很难从一个序列反推出`seed`从而破解该数列

### Usages
#### Scala
```sbt
resolvers += "yuxiaobo" at "http://code.yuxiaobo.com/repository"
```

```scala
    val max = 1000
    val rs = RandomSerial(max, 10, 80)
    // print serial
    0.until(max).map(rs.get).foreach(println)
```

#### Java 8+
```xml
    <repositories>
        <repository>
            <id>yuxiaobo</id>
            <url>http://code.yuxiaobo.com/repository</url>
        </repository>
    </repositories>
```

```java
  int max = 1000; // max value 
  int deep = 10; // hash times
  long seed = 3348203884722039l; // random 
  RandomSerial rs = new RandomSerial(max, deep, seed);
  // print serial
  IntStream.range(0, max).map(rs::get).foreach(System.out::println)
```

### 原理
