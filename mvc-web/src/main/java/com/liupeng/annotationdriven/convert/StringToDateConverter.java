package com.liupeng.annotationdriven.convert;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * 转Date
 *
 * @author fengdao.lp
 * @date 2018/5/25
 */
public class StringToDateConverter implements Converter<String, Date> {

    /**
     * 目前支持的日期格式
     */
    private final static String[] PATTERNS = new String[] {
        "yyyy-MM-dd",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd'+'HH:mm:ss"
    };

    public StringToDateConverter() {
        System.out.println("转换器我出生啦");
    }

    @Override
    public Date convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        try {
            return DateUtils.parseDate(source, PATTERNS);
        } catch (Exception e) {
            throw new IllegalArgumentException("转换Date出错:" + source, e);
        }
    }
}
