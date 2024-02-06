package org.jacksonhelper.deserialize;

import static org.jacksonhelper.JacksonUtils.readValue;

import org.jacksonhelper.mapper.DefaultObjectMapper;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class DeserializeToList {
  public static <T> List<T> fromString(String str) {
    return fromString(str, null);
  }

  public static <T> List<T> fromString(String str, Class<T> clazz) {
    return fromAny(str, clazz);
  }

  public static <T> List<T> fromUrl(URL url) {
    return fromUrl(url, null);
  }

  public static <T> List<T> fromUrl(URL url, Class<T> clazz) {
    return fromAny(url, clazz);
  }

  public static <T> List<T> fromFile(File file) {
    return fromFile(file, null);
  }

  public static <T> List<T> fromFile(File file, Class<T> clazz) {
    return fromAny(file, clazz);
  }

  private static <T> List<T> fromAny(Object input, Class<T> clazz) {
    List<T> result = readValue(input);
    return (clazz != null)
        ? result.stream().map(i -> DefaultObjectMapper.get().convertValue(i, clazz)).collect(Collectors.toList())
        : result;
  }
}
