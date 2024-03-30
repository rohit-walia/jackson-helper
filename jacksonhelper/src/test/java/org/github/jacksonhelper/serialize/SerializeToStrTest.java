package org.github.jacksonhelper.serialize;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SerializeToStrTest {

  @Test
  void toString_NonSerializablePojo() {
    Assertions.assertThrows(Exception.class, () -> SerializeToStr.toString(this));
  }
}