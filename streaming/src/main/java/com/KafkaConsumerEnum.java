package com;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.beans.RecordBean;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public enum KafkaConsumerEnum {

    DASHBOARD(Collections.singleton("dashboard"),
              RecordBean.class,
              new TypeReference<RecordBean>() {},
              BuilderUtil.getJsonObjectMapperSnakeCase());

    private final Set<String> topics;
    private final Class encoderClass;
    private final TypeReference typeReference;
    private final ObjectMapper objectMapper;


}
