package org.github.jacksonhelper.deserialize;

import static org.github.jacksonhelper.JacksonUtils.readValue;

import org.github.jacksonhelper.mapper.DefaultObjectMapper;

import java.io.File;
import java.net.URL;

public class DeserializeToObj {
  public static <T> T fromString(String str) {
    return fromString(str, null);
  }

  public static <T> T fromString(String str, Class<T> clazz) {
    return fromAny(str, clazz);
  }

  public static <T> T fromUrl(URL url) {
    return fromUrl(url, null);
  }

  public static <T> T fromUrl(URL url, Class<T> clazz) {
    return fromAny(url, clazz);
  }

  public static <T> T fromFile(File file) {
    return fromFile(file, null);
  }

  public static <T> T fromFile(File file, Class<T> clazz) {
    return fromAny(file, clazz);
  }

  private static <T> T fromAny(Object input, Class<T> clazz) {
    T result = readValue(input);
    return clazz != null
        ? DefaultObjectMapper.get().convertValue(result, clazz)
        : result;
  }
}
