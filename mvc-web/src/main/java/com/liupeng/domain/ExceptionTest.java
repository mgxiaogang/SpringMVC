package com.liupeng.domain;

import javax.validation.constraints.NotNull;

/**
 * @author fengdao.lp
 * @date 2018/3/13
 */
public class ExceptionTest {
    @NotNull(message = "id不能为空")
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ExceptionTest{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }

    public static final class ExceptionTestBuilder {
        private Long id;
        private String name;

        private ExceptionTestBuilder() {}

        public static ExceptionTestBuilder newExceptionTest() { return new ExceptionTestBuilder(); }

        public ExceptionTestBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ExceptionTestBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ExceptionTest build() {
            ExceptionTest exceptionTest = new ExceptionTest();
            exceptionTest.setId(id);
            exceptionTest.setName(name);
            return exceptionTest;
        }
    }
}
