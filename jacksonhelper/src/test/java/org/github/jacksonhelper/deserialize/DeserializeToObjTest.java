package org.github.jacksonhelper.deserialize;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class DeserializeToObjTest {

  @Test
  void fromString_JsonArray() {
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
    Object deserializedObj = DeserializeToObj.fromString(input, Object.class);
    List<Object> expectedList = List.of(
        Map.of("id", 1, "name", "jacksonHelper1"),
        Map.of("id", 2, "name", "jacksonHelper2")
    );
    Assertions.assertNotNull(deserializedObj);
    Assertions.assertInstanceOf(List.class, deserializedObj);
    Assertions.assertEquals(expectedList, deserializedObj);
  }

  @Test
  void fromString_JsonObject() {
    String input = """
            {
              "id": 1,
              "name": "jacksonHelper1"
            }
        """;
    Object deserializedObj = DeserializeToObj.fromString(input, Object.class);
    Assertions.assertNotNull(deserializedObj);
    Assertions.assertEquals(Map.of("id", 1, "name", "jacksonHelper1"), deserializedObj);
  }

  @Test
  void fromString_WithoutTypeConversion() {
    String input = """
            {
              "id": 1,
              "name": "jacksonHelper1"
            }
        """;
    Object deserializedObj = DeserializeToObj.fromString(input);
    Assertions.assertNotNull(deserializedObj);
    Assertions.assertEquals(Map.of("id", 1, "name", "jacksonHelper1"), deserializedObj);
  }

  @Test
  void fromString_WhenNull() {
    Assertions.assertThrows(NullPointerException.class, () -> DeserializeToObj.fromString(null, Object.class));
  }

  @Test
  void fromString_WhenEmpty() {
    Assertions.assertThrows(Exception.class, () -> DeserializeToObj.fromString("", Object.class));
  }

  @SneakyThrows
  @Test
  void fromUrl() {
    URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("JsonArrayTestData.txt")).toURI();
    Object deserializedObj = DeserializeToObj.fromUrl(uri.toURL(), Object.class);
    List<Object> expectedObj = List.of(
        Map.of("id", 1, "name", "jacksonHelper1"),
        Map.of("id", 2, "name", "jacksonHelper2")
    );
    Assertions.assertNotNull(deserializedObj);
    Assertions.assertInstanceOf(List.class, deserializedObj);
    Assertions.assertEquals(expectedObj, deserializedObj);
  }

  @SneakyThrows
  @Test
  void fromUrl_WithoutTypeConversion() {
    URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("JsonObjectTestData.txt")).toURI();
    Object deserializedObj = DeserializeToObj.fromUrl(uri.toURL());
    Assertions.assertNotNull(deserializedObj);
    Assertions.assertInstanceOf(Map.class, deserializedObj);
    Assertions.assertEquals(Map.of("id", 1, "name", "jacksonHelper1"), deserializedObj);
  }

  @SneakyThrows
  @Test
  void fromFile() {
    URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("JsonObjectTestData.txt")).toURI();
    Object deserializedObj = DeserializeToObj.fromFile(new File(uri), Object.class);
    Assertions.assertNotNull(deserializedObj);
    Assertions.assertEquals(Map.of("id", 1, "name", "jacksonHelper1"), deserializedObj);
  }

  @SneakyThrows
  @Test
  void fromFile_WithoutTypeConversion() {
    URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("JsonArrayTestData.txt")).toURI();
    Object deserializedObj = DeserializeToObj.fromFile(new File(uri));
    List<Object> expectedObj = List.of(
        Map.of("id", 1, "name", "jacksonHelper1"),
        Map.of("id", 2, "name", "jacksonHelper2")
    );
    Assertions.assertNotNull(deserializedObj);
    Assertions.assertEquals(expectedObj, deserializedObj);
  }
}