package br.com.saleback.config;

import org.hibernate.dialect.PostgreSQL94Dialect;

import java.sql.Types;

public class PostgreSQL94CustomDialect extends PostgreSQL94Dialect {

    public PostgreSQL94CustomDialect() {
        super();
        this.registerHibernateType(Types.ARRAY, CustomStringArrayType.class.getName());
    }

}