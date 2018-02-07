package com.liupeng.jdbc;

public interface IPersonDao {

    void add();

    void save(Person person);

    Person query();
}
