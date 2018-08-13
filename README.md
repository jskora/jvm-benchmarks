# jvm-benchmarks

Benchmarks for various JVM elements .

## Running JMH benchmarks [1]
To run any of the JVM benchmarks package the Maven project 
with ```mvn package``` and then execute the desired benchmark 
with ```java -cp target/microbenchmarks.jar <class>```.

For example, this will build and run the [StringBenchmarks](https://github.com/jskora/src/main/java/jskora/jvm/StringBenchmarks.java)
benchmark class.
```$bash
$ mvn package
$ java -cp targets/microbenchmarks.jar jskora.jvm.StringBenchmarks
```

[1] More on JMH at the [OpenJDK:jmh](http://openjdk.java.net/projects/code-tools/jmh/) page.


