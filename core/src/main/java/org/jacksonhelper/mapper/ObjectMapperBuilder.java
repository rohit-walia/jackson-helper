package org.jacksonhelper.mapper;


import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import org.jacksonhelper.JacksonUtils;

import java.util.List;

@Builder
public class ObjectMapperBuilder {
  private List<?> featureToEnable;
  private List<?> featureToDisable;
  private List<? extends Module> moduleToRegister;

  /**
   * Builds an ObjectMapper from this Builder instance.
   *
   * @return ObjectMapper
   */
  public ObjectMapper toObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    JacksonUtils.configure(objectMapper, featureToEnable, true);
    JacksonUtils.configure(objectMapper, featureToDisable, false);
    objectMapper.registerModules(moduleToRegister);
    return objectMapper;
  }
}
