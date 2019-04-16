# Configuration
[![](https://jitpack.io/v/Mouse0w0/Configuration.svg)](https://jitpack.io/#Mouse0w0/Configuration)

A simple configuration API for Java.

## How to import

### 1. Import API
Maven:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependency>
    <groupId>com.github.Mouse0w0.Configuration</groupId>
    <artifactId>configuration-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

Gradle:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
     implementation 'com.github.Mouse0w0.Configuration:configuration-api:1.0.0'
}
```

### 2. Select and import parsers
Now only support `configuration-gson` and `configuration-toml4j`.

Maven:
```xml
<dependency>
    <groupId>com.github.Mouse0w0.Configuration</groupId>
    <artifactId>configuration-toml4j</artifactId>
    <version>1.0.0</version>
</dependency>
```

Gradle:
```groovy
dependencies {
    implementation 'com.github.Mouse0w0.Configuration:configuration-toml4j:1.0.0'
}
```

## How to use

### Get configuration value
```java
Config config;
int i = config.getInt("parent.int");
String s = config.getString("parent.string", "Hello World"); // Support default value
```

### Set configuration value
```java
Config config;
Object oldValue = config.set("parent.string", "Hello World");
```

### Load configuration file
```java
Config config = ConfigParsers.load(new File("config.json"));
// Config config = ConfigParsers.load(new File("config.toml"));
```

### Save configuration file
```java
ConfigParsers.save(new File("config.json"), config);
// ConfigParsers.save(new File("config.toml"), config);
```
