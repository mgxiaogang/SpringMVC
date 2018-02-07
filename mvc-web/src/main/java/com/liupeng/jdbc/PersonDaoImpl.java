package com.liupeng.jdbc;

import org.springframework.stereotype.Service;

@Service
public class PersonDaoImpl implements IPersonDao {

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
        return null;
    }
}
