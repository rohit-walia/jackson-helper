package org.jacksonhelper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.DatatypeFeature;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jacksonhelper.mapper.DefaultObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class JacksonUtils {

  /**
   * Method to deserialize JSON content from given input. The input can be in form of a String, URL or File.
   *
   * @param input The input JSON data to read from.
   * @return The deserialized JSON content.
   */
  @SneakyThrows
  public static <T> T readValue(@NonNull Object input) {
    ObjectMapper objectMapper = DefaultObjectMapper.get();

    try {
      if (input instanceof String str) {
        return objectMapper.readValue(str, new TypeReference<>() {
        });
      } else if (input instanceof URL url) {
        return objectMapper.readValue(url, new TypeReference<>() {
        });
      } else if (input instanceof File file) {
        return objectMapper.readValue(file, new TypeReference<>() {
        });
      } else {
        throw new UnsupportedOperationException("Unsupported input type!");
      }
    } catch (IOException e) {
      throw new Exception("Error reading data!", e);
    }
  }

  /**
   * Method to configure the given ObjectMapper with the given features.
   *
   * @param mapper   The ObjectMapper to configure.
   * @param features The features to enable or disable.
   * @param state    The state of the features to set.
   */
  public static void configure(ObjectMapper mapper, List<?> features, boolean state) {
    features.forEach(feature -> {
      if (feature instanceof JsonParser.Feature) {
        mapper.configure((JsonParser.Feature) feature, state);
      } else if (feature instanceof JsonGenerator.Feature) {
        mapper.configure((JsonGenerator.Feature) feature, state);
      } else if (feature instanceof DatatypeFeature) {
        mapper.configure((DatatypeFeature) feature, state);
      } else if (feature instanceof DeserializationFeature) {
        mapper.configure((DeserializationFeature) feature, state);
      } else if (feature instanceof SerializationFeature) {
        mapper.configure((SerializationFeature) feature, state);
      } else {
        throw new RuntimeException("Unmapped feature!");
      }
    });
  }
}
