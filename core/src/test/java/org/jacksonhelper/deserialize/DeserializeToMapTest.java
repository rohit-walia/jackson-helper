package org.jacksonhelper.deserialize;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.util.Map;
import java.util.Objects;

class DeserializeToMapTest {
  @Test
  void fromString_JsonObject() {
    final var key = "message";
    final var value = "Process successfully completed";
    String input = """
        {
          "%s": "%s"
        }
        """.formatted(key, value);
    Map<String, String> deserializedMap = DeserializeToMap.fromString(input, String.class);
    Assertions.assertNotNull(deserializedMap);
    Assertions.assertFalse(deserializedMap.isEmpty());
    Assertions.assertEquals(deserializedMap.get(key), value);
  }

  @Test
  void fromString_JsonObject_WithoutTypeConversion() {
    final var key = "message";
    final var value = "Process successfully completed";
    String input = """
        {
          "%s": "%s"
        }
        """.formatted(key, value);
    Map<String, String> deserializedMap = DeserializeToMap.fromString(input);
    Assertions.assertNotNull(deserializedMap);
    Assertions.assertFalse(deserializedMap.isEmpty());
    Assertions.assertEquals(deserializedMap.get(key), value);
  }

  @Test
  void fromString_JsonArray() {
    final var key = "message";
    final var value = "Process successfully completed";
    String input = """
        [
          {
            "%s": "%s"
          }
        ]
        """.formatted(key, value);
    Assertions.assertThrows(Exception.class, () -> DeserializeToMap.fromString(input, Object.class),
        "DeserializeToMap should not be able to parse JSON array, only Map like structure allowed.");
  }

  @Test
  void fromString_WhenNull() {
    Assertions.assertThrows(NullPointerException.class, () -> DeserializeToMap.fromString(null, Object.class));
  }

  @Test
  void fromString_WhenEmpty() {
    Assertions.assertThrows(Exception.class, () -> DeserializeToMap.fromString("", Object.class));
  }

  @SneakyThrows
  @Test
  void fromUrl() {
    URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("JsonObjectTestData.txt")).toURI();
    Map<String, Object> deserializedMap = DeserializeToMap.fromUrl(uri.toURL(), Object.class);
    Assertions.assertNotNull(deserializedMap);
    Assertions.assertFalse(deserializedMap.isEmpty());
    Assertions.assertEquals(Map.of("id", 1, "name", "jacksonHelper1"), deserializedMap);
  }

  @SneakyThrows
  @Test
  void fromUrl_WithoutTypeConversion() {
    URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("JsonObjectTestData.txt")).toURI();
    Map<String, Object> deserializedMap = DeserializeToMap.fromUrl(uri.toURL());
    Assertions.assertNotNull(deserializedMap);
    Assertions.assertFalse(deserializedMap.isEmpty());
    Assertions.assertEquals(Map.of("id", 1, "name", "jacksonHelper1"), deserializedMap);
  }

  @SneakyThrows
  @Test
  void fromFile() {
    URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("JsonObjectTestData.txt")).toURI();
    Map<String, Object> deserializedMap = DeserializeToMap.fromFile(new File(uri), Object.class);
    Assertions.assertNotNull(deserializedMap);
    Assertions.assertFalse(deserializedMap.isEmpty());
    Assertions.assertEquals(Map.of("id", 1, "name", "jacksonHelper1"), deserializedMap);
  }

  @SneakyThrows
  @Test
  void fromFile_WithoutTypeConversion() {
    URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("JsonObjectTestData.txt")).toURI();
    Map<String, ?> deserializedMap = DeserializeToMap.fromFile(new File(uri));
    Assertions.assertNotNull(deserializedMap);
    Assertions.assertFalse(deserializedMap.isEmpty());
    Assertions.assertEquals(Map.of("id", 1, "name", "jacksonHelper1"), deserializedMap);
  }

  @Test
  void fromFile_withInvalidFilePath() {
    File pathToNonExistingFile = new File("non-existing-file.txt");
    Assertions.assertThrows(Exception.class, () -> DeserializeToMap.fromFile(pathToNonExistingFile, null));
  }
}