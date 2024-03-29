package org.github.jacksonhelper.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.github.jacksonhelper.mapper.DefaultObjectMapper;

public class SerializeToStr {

  /**
   * Serialize the given object to a JSON string.
   *
   * @param obj Object
   * @return String
   */
  @SneakyThrows
  public static String toString(Object obj) {
    try {
      return DefaultObjectMapper.get().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new Exception("Error serializing POJO!", e);
    }
  }

}
