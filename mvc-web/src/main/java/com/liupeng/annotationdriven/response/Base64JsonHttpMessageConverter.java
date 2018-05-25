package com.liupeng.annotationdriven.response;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.FileCopyUtils;

/**
 * 返回值JSON对象->byte[]
 *
 * @author fengdao.lp
 * @date 2018/5/24
 */
public class Base64JsonHttpMessageConverter extends MappingJackson2HttpMessageConverter implements InitializingBean{
    private static final Logger log = LoggerFactory.getLogger(
        Base64JsonHttpMessageConverter.class);

    public Base64JsonHttpMessageConverter() {
        super();
        ObjectMapper mapper = getObjectMapper();
        // JSON configuration not to serialize null field
        // JSON configuration not to throw exception on empty bean class
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // JSON configuration not to throw exception on unknown properties
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // JSON configuration for compatibility
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        //disable
        mapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false));
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException {
        try {
            byte[] bytes = getObjectMapper().writeValueAsBytes(object);
            FileCopyUtils.copy(Base64.encodeBase64(bytes), outputMessage.getBody());
        } catch (Exception ex) {
            log.error("Could not write JSON: " + ex.getMessage(), ex);
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("******************************");
    }
}
