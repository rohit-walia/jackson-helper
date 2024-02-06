package org.jacksonhelper.deserialize;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class DeserializeToListTest {
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
    List<Object> deserializedList = DeserializeToList.fromString(input, Object.class);
    List<Object> expectedList = List.of(
        Map.of("id", 1, "name", "jacksonHelper1"),
        Map.of("id", 2, "name", "jacksonHelper2")
    );
    Assertions.assertNotNull(deserializedList);
    Assertions.assertFalse(deserializedList.isEmpty());
    Assertions.assertEquals(expectedList, deserializedList);
  }

  @Test
  void fromString_JsonArray_WithoutTypeConversion() {
    String input = """
           [
             {
               "id": 1,
               "name": "jacksonHelper1"
             }
           ]
        """;
    List<?> deserializedList = DeserializeToList.fromString(input);
    List<?> expectedList = List.of(Map.of("id", 1, "name", "jacksonHelper1"));
    Assertions.assertNotNull(deserializedList);
    Assertions.assertFalse(deserializedList.isEmpty());
    Assertions.assertEquals(expectedList, deserializedList);
  }

  @Test
  void fromString_JsonObject() {
    String input = """
            {
              "id": 1,
              "name": "jacksonHelper1"
            }
        """;
    Assertions.assertThrows(Exception.class, () -> DeserializeToList.fromString(input, Object.class),
        "DeserializeToList should not be able to parse JSON object, only array like structure.");
  }

  @Test
  void fromString_WhenNull() {
    Assertions.assertThrows(NullPointerException.class, () -> DeserializeToList.fromString(null, Object.class));
  }

  @Test
  void fromString_WhenEmpty() {
    Assertions.assertThrows(Exception.class, () -> DeserializeToList.fromString("", Object.class));
  }

  @SneakyThrows
  @Test
  void fromUrl() {
    URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("JsonArrayTestData.txt")).toURI();
    List<Object> deserializedList = DeserializeToList.fromUrl(uri.toURL(), Object.class);
    List<Object> expectedList = List.of(
        Map.of("id", 1, "name", "jacksonHelper1"),
        Map.of("id", 2, "name", "jacksonHelper2")
    );
    Assertions.assertNotNull(deserializedList);
    Assertions.assertFalse(deserializedList.isEmpty());
    Assertions.assertEquals(expectedList, deserializedList);
  }

  @SneakyThrows
  @Test
  void fromUrl_WithoutTypeConversion() {
    URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("JsonArrayTestData.txt")).toURI();
    List<Map<String, Object>> deserializedList = DeserializeToList.fromUrl(uri.toURL());
    List<Map<String, Object>> expectedList = List.of(
        Map.of("id", 1, "name", "jacksonHelper1"),
        Map.of("id", 2, "name", "jacksonHelper2")
    );
    Assertions.assertNotNull(deserializedList);
    Assertions.assertFalse(deserializedList.isEmpty());
    Assertions.assertEquals(expectedList, deserializedList);
  }

  @SneakyThrows
  @Test
  void fromFile() {
    URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("JsonArrayTestData.txt")).toURI();
    List<Object> deserializedList = DeserializeToList.fromFile(new File(uri), Object.class);
    List<Object> expectedList = List.of(
        Map.of("id", 1, "name", "jacksonHelper1"),
        Map.of("id", 2, "name", "jacksonHelper2")
    );
    Assertions.assertNotNull(deserializedList);
    Assertions.assertFalse(deserializedList.isEmpty());
    Assertions.assertEquals(expectedList, deserializedList);
  }

  @SneakyThrows
  @Test
  void fromFile_WithoutTypeConversion() {
    URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("JsonArrayTestData.txt")).toURI();
    List<Map<String, String>> deserializedList = DeserializeToList.fromFile(new File(uri));
    List<Map<String, ?>> expectedList = List.of(
        Map.of("id", 1, "name", "jacksonHelper1"),
        Map.of("id", 2, "name", "jacksonHelper2")
    );
    Assertions.assertNotNull(deserializedList);
    Assertions.assertFalse(deserializedList.isEmpty());
    Assertions.assertEquals(expectedList, deserializedList);
  }
}