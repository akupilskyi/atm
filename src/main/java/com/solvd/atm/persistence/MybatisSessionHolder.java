package com.solvd.atm.persistence;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisSessionHolder {

    private static final String CONFIG_FILE_NAME = "mybatis-config.xml";
    private static final SqlSessionFactory SQL_SESSION_FACTORY;

    static {
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(CONFIG_FILE_NAME);
        } catch (IOException e) {
            throw new RuntimeException("Unable to prepare mybatis config xml.", e);
        }
        SQL_SESSION_FACTORY = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return SQL_SESSION_FACTORY;
    }
}