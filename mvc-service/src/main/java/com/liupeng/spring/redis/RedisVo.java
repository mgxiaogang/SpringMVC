package com.liupeng.spring.redis;

import java.io.Serializable;
import java.util.List;

public class RedisVo<T extends Serializable> implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -2091808016559461636L;
    private List<? extends Serializable> value;

    public RedisVo(List<? extends Serializable> value) {
        
        this.value = value;
    }

    public List<? extends Serializable> getValue() {
        return value;
    }

    public void setValue(List<? extends Serializable> value) {
        this.value = value;
    }
    
}
