package org.github.jacksonhelper.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;

public class DefaultObjectMapper {
  private static ObjectMapper objectMapper = null;

  public static List<?> DEFAULT_FEATURES_TO_ENABLE = List.of(JsonParser.Feature.ALLOW_SINGLE_QUOTES,
      JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);

  public static List<?> DEFAULT_FEATURES_TO_DISABLE = List.of(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
      DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

  public static List<? extends Module> DEFAULT_MODULES_TO_REGISTER = List.of(new JavaTimeModule());

  private DefaultObjectMapper() {
    throw new UnsupportedOperationException("Should not instantiate DefaultObjectMapper.");
  }

  /**
   * Get the default ObjectMapper instance.
   *
   * @return ObjectMapper
   */
  public static ObjectMapper get() {
    if (objectMapper == null) {
      setDefault();
    }
    return objectMapper;
  }

  /**
   * Override the default ObjectMapper instance.
   *
   * @param objectMapper ObjectMapper
   */
  public static void set(ObjectMapper objectMapper) {
    DefaultObjectMapper.objectMapper = objectMapper;
  }

  /**
   * Set the default ObjectMapper instance.
   */
  public static void setDefault() {
    set(ObjectMapperBuilder.builder()
        .featureToEnable(DEFAULT_FEATURES_TO_ENABLE)
        .featureToDisable(DEFAULT_FEATURES_TO_DISABLE)
        .moduleToRegister(DEFAULT_MODULES_TO_REGISTER)
        .build()
        .toObjectMapper());
  }
}
