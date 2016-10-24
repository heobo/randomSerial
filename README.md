## 随机数列生成器

### Usages
#### Scala
```scala
    val max = 1000
    val rs = RandomSerial(max, 10, 80)
    // print serial
    0.until(max).map(rs.get).foreach(println)
```

#### Java 8+
```java
  int max = 1000; // max value 
  int deep = 10; // hash times
  long seed = 3348203884722039l; // random 
  RandomSerial rs = new RandomSerial(max, deep, seed);
  // print serial
  IntStream.range(0, max).map(rs::get).foreach(System.out::println)
```
