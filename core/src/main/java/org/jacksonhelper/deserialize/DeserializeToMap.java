package org.jacksonhelper.deserialize;

import static org.jacksonhelper.JacksonUtils.readValue;

import org.jacksonhelper.mapper.DefaultObjectMapper;

import java.io.File;
import java.net.URL;
import java.util.Map;

public class DeserializeToMap {
  public static <T> Map<String, T> fromString(String str) {
    return fromString(str, null);
  }

  public static <T> Map<String, T> fromString(String str, Class<T> clazz) {
    return fromAny(str, clazz);
  }

  public static <T> Map<String, T> fromUrl(URL url) {
    return fromUrl(url, null);
  }

  public static <T> Map<String, T> fromUrl(URL url, Class<T> clazz) {
    return fromAny(url, clazz);
  }

  public static <T> Map<String, T> fromFile(File file) {
    return fromFile(file, null);
  }

  public static <T> Map<String, T> fromFile(File file, Class<T> clazz) {
    return fromAny(file, clazz);
  }

  private static <T> Map<String, T> fromAny(Object input, Class<T> clazz) {
    Map<String, T> result = readValue(input);
    if (clazz != null) {
      result.replaceAll((k, v) -> DefaultObjectMapper.get().convertValue(v, clazz));
    }
    return result;
  }
}
