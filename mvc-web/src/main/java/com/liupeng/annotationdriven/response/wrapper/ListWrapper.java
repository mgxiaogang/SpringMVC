package com.liupeng.annotationdriven.response.wrapper;

import java.util.List;

import com.liupeng.annotationdriven.response.ResponseVo;
import org.springframework.core.MethodParameter;

/**
 * @author fengdao.lp
 * @date 2018/5/24
 */
public class ListWrapper extends AbstractBeanWrapper {

    @Override
    public boolean supports(MethodParameter returnType) {
        return List.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object wrap(Object bean) {
        List<?> list = (List)bean;
        Rows rows = new Rows();
        rows.setCount(list.size());
        rows.setRows(list);
        return new ResponseVo(rows);
    }

    private class Rows {

        private Integer count;

        private List rows;

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public List getRows() {
            return rows;
        }

        public void setRows(List rows) {
            this.rows = rows;
        }

    }
}
