package com.springlego.autoconfigure.frame.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.springlego.autoconfigure.frame.util.XssUtil;

import java.io.IOException;

/* *
 * @Author tomsun28
 * @Description
 * @Date 21:14 2018/4/16
 */
public class XssSqlStringJsonSerializer extends JsonSerializer<String> {

    @Override
    public Class<String> handledType() {
        return String.class;
    }

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (s != null) {
            String encodedValue = XssUtil.stripSqlXss(s);
            jsonGenerator.writeString(encodedValue);
        }
    }
}
