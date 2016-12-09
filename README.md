# Official tronalddump.io api client for Java

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.tronalddump/client-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.tronalddump/client-java)
[![Javadocs](http://www.javadoc.io/badge/io.tronalddump/client-java.svg?color=brightgreen)](http://www.javadoc.io/doc/io.tronalddump/client-java)
[![Apache 2.0 License](https://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

[Tronalddump.io][] is a free api and web archive for the dumbest things Donald Trump has ever said ...

## Installation

Add the dependency to your project:

_Maven_:

```xml
<dependency>
  <groupId>io.tronalddump</groupId>
  <artifactId>client-java</artifactId>
  <version>1.0.0</version>
</dependency>
```

_Gradle_:

```groovy
dependencies {
    compile "io.tronalddump:client-java:1.0.0"
}
```

## Usage

```java
// Create the Tronald client
TronaldClient client = new TronaldClient();

// Retrieve a Tronald quote by its id
Quote quote = client.getQuote("wAgIgzV1S9OARKhfun3f0A");
System.out.println(quote.getValue());

// Perform a free text search
List<Quote> quotes = client.search("money");
```

## License

This software is released under version 2.0 of the [Apache License][].


[Apache License]: http://www.apache.org/licenses/LICENSE-2.0
[Tronalddump.io]: https://www.tronalddump.io
