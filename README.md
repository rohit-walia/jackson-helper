# Jackson Helper

Jackson is a popular Java library used for JSON (JavaScript Object Notation) processing. 
Its main purpose is to provide functionalities to serialize Java objects into JSON and deserialize JSON into Java objects.
This helper library is a wrapper around Jackson library to provide some common deserializing and serializing functionalities.

## Installation

To use this Jackson Helper library in your project, follow these steps:

1. Add following repository in your `settings.xml` file
```xml
<repository>
  <id>github</id>
  <url>https://maven.pkg.github.com/rohit-walia/jackson-helper</url>
  <snapshots>
    <enabled>true</enabled>
  </snapshots>
</repository>
```

2. Add the Maven dependency to your `pom.xml` file. Make sure to use the latest version available:
```xml
<dependency>
  <groupId>org.jacksonhelper</groupId>
  <artifactId>jackson-helper</artifactId>
  <version>${jackson-helper.version}</version>
</dependency>
```

3. Run mvn install

## Examples

#### Deserialize JSON data into Java objects

This code snippet shows how you can deserialize JSON data into Java objects. The JSON data source can be a String, file, 
or URL. You can deserialize into POJOs, Lists, and Maps. See [here](core/src/test/java/org/jacksonhelper/deserialize) for 
list of classes that can help you deserialize JSON data.

```Java
void deserializeToList() {
  String input = """
         [
           {
             "id": 1,
             "name": "jacksonHelper1"
           },
           {
             "id": 2,
             "name": "jacksonHelper2"
           }
         ]
      """;

  // without specifying the type of the list, the return value will be generic type
  List<Object> deserializedList = DeserializeToList.fromString(input);
  List<Map<String, Object>> deserializedList = DeserializeToList.fromString(input);

  // you can specify the type of the list by passing the class type as the second argument
  List<YourPojo> deserializedListWithType = DeserializeToList.fromString(input, YourPojo.class);

  // your input source, in addition to a String, can also be a file or URL
  URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("yourFileName.txt")).toURI();
  List<?> deserializedListFromFile = DeserializeToList.fromFile(new File(uri));
  List<?> deserializedListFromUrl = DeserializeToList.fromUrl(uri.toURL());
}
```

```Java
void deserializeToMap() {
  String input = """
         {
           "id": 1,
           "name": "jacksonHelper1"
         }
      """;

  // without specifying the type of the Map value
  Map<String, Object> deserializedMap = DeserializeToMap.fromString(input);
  Map<String, String> deserializedMap = DeserializeToMap.fromString(input);

  // you can specify the type of the Map value by passing the class type as the second argument
  Map<String, YourPojo> deserializedMapWithType = DeserializeToMap.fromString(input, YourPojo.class);

  // your input source, in addition to a String, can also be a file or URL
  URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("yourFileName.txt")).toURI();
  Map<String, String> deserializedMapFromFile = DeserializeToList.fromFile(new File(uri));
  Map<String, ?> deserializedMapFromUrl = DeserializeToList.fromUrl(uri.toURL());
}
```

# Tools, libraries, and technologies

### JUnit5

This project uses JUnit5 for testing. See [here](https://junit.org/junit5/docs/current/user-guide/) for more information.

### Lombok

This project uses lombok to decrease boilerplate code. If you are using Intellij please install the Lombok Plugin. If
you are using Eclipse STS follow the instructions [here](https://projectlombok.org/setup/eclipse).
If you are using another IDE you can see if it is supported on the Lombok website [here](https://projectlombok.org).

### Jackson

Jackson is the JSON library used in this project. See [here](https://github.com/FasterXML/jackson) for more information.

### Code Quality

As part of the build, there are several code quality checks running against the code base. All code quality files can be
found in the root of the project under the [codequality](.codequality) directory.

#### CheckStyle

The project runs checkstyle plugin to validate java code formatting and enforce best coding standards.

#### PMD

The project runs PMD code analysis to find common programming flaws like unused variables, empty catch blocks, unnecessary
object creation, etc...
