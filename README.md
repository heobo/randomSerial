## 随机数列生成器

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
