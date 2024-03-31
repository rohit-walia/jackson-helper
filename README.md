# Jackson Helper

Jackson is a popular Java library that specializes in processing JSON (JavaScript Object Notation). It offers robust
functionalities to serialize Java objects into JSON format and deserialize JSON data into Java objects. This process is
crucial in data exchange between a Java-based application and a web server, or for storing data in a human-readable format.

This helper library, built as a wrapper around the Jackson library, aims to streamline the process of serialization and
deserialization. It provides a set of common functionalities that simplify the conversion process, making it more efficient
and less error-prone. The library encapsulates the complexities of using Jackson, offering a more user-friendly interface for
developers to work with JSON data in Java.

## Installation

To use this Jackson Helper library in your project, add below Maven dependency to your `pom.xml` file. Make sure to use the
latest version available. This project is deployed to both
the [Maven Central Repository](https://central.sonatype.com/artifact/io.github.rohit-walia/jacksonhelper) and the 
[GitHub Package Registry](https://github.com/rohit-walia?tab=packages&repo_name=jackson-helper).

```xml
<dependency>
    <groupId>io.github.rohit-walia</groupId>
    <artifactId>jackson-helper</artifactId>
    <version>2.0.6</version>
</dependency>
```

## Usage Examples

#### Deserializing JSON Data into Java Objects

This section provides a detailed guide on how to convert JSON data into Java objects. The source of the JSON data can be a
String, File, or URL. The library allows you to deserialize the data into various Java data structures such as POJOs
(Plain Old Java Objects), Lists, and Maps.

The Jackson Helper library comes with a set of classes specifically designed to aid in the deserialization process. These
classes are located in the [deserialize](jacksonhelper/src/main/java/org/github/jacksonhelper/deserialize) directory of the
project. They provide a simplified interface for deserialization, abstracting away the complexities of the underlying Jackson
library.

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
  List<Object> deserializedList1 = DeserializeToList.fromString(input);
  List<Map<String, Object>> deserializedList2 = DeserializeToList.fromString(input);

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
  Map<String, Object> deserializedMap1 = DeserializeToMap.fromString(input);
  Map<String, String> deserializedMap2 = DeserializeToMap.fromString(input);

  // you can specify the type of the Map value by passing the class type as the second argument
  Map<String, YourPojo> deserializedMapWithType = DeserializeToMap.fromString(input, YourPojo.class);

  // your input source, in addition to a String, can also be a file or URL
  URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("yourFileName.txt")).toURI();
  Map<String, String> deserializedMapFromFile = DeserializeToList.fromFile(new File(uri));
  Map<String, ?> deserializedMapFromUrl = DeserializeToList.fromUrl(uri.toURL());
}
```

#### Configuring the ObjectMapper

The `DefaultObjectMapper` is used by all deserialization and serialization processes. The following is the default mapper's
configuration:

The features enabled by default:

- `JsonParser.Feature.ALLOW_SINGLE_QUOTES`
- `JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES`
- `DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE`

The features disabled by default:

- `SerializationFeature.WRITE_DATES_AS_TIMESTAMPS`
- `DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES`

The modules registered by default:
- `JavaTimeModule`

Generally, this default ObjectMapper will work for most use cases, and you will not need to worry about creating or
configuring the ObjectMapper. However, if you need to customize the ObjectMapper, you can do so by using the
`ObjectMapperBuilder` class and setting the ObjectMapper instance in the `DefaultObjectMapper` class.
Here's an example of how to do this:

```Java
void customizeObjectMapper() {
  // create custom ObjectMapper
  ObjectMapper customObjectMapper = ObjectMapperBuilder.builder()
      .featureToEnable(List.of(JsonParser.Feature.ALLOW_COMMENTS))
      .featureToDisable(List.of(SerializationFeature.FAIL_ON_EMPTY_BEANS))
      .moduleToRegister(List.of(new Jdk8Module()))
      .build()
      .toObjectMapper();

  // set your custom ObjectMapper to the DefaultObjectMapper
  DefaultObjectMapper.setObjectMapper(customObjectMapper);

  // now all deserialization and serialization processes will use your custom ObjectMapper

  // In the case where you want to revert to using the default ObjectMapper again, after customizing it, you can do so by
  // simply calling the setDefault() method
  DefaultObjectMapper.setDefault();
}
```

Wondering what features, modules, and other configurations you can set on the ObjectMapper? Here are some links to the
Jackson documentation that can help you with that:

- [Serialization features](https://github.com/FasterXML/jackson-databind/wiki/Serialization-Features)
- [Deserialization features](https://github.com/FasterXML/jackson-databind/wiki/Deserialization-Features)
- [Parser features](https://github.com/FasterXML/jackson-core/wiki/JsonParser-Features)
- [JsonGenerator features](https://github.com/FasterXML/jackson-core/wiki/JsonGenerator-Features)
- [Datatype features](https://github.com/FasterXML/jackson-databind/wiki/DatatypeFeatures)

# Tools, libraries, and technologies

### JUnit5

This project uses [JUnit5](https://junit.org/junit5/docs/current/user-guide/) for testing. 
See [here](jacksonhelper/src/test/java/org/github/jacksonhelper) for how this library is being tested.

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
