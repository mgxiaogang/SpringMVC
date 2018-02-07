package com.liupeng.jdbc;

import com.liupeng.dao.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonDaoImpl implements IPersonDao {

    @Resource
    private TestMapper testMapper;

    @Override
    public void add() {
        System.out.println("add");

    }

    @Override
    public void save(Person person) {
        System.out.println("bb");
    }

    @Override
    public Person query() {
        return testMapper.queryPerson();
    }
}
