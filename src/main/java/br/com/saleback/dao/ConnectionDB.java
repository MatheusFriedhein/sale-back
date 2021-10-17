package br.com.saleback.dao;

import br.com.saleback.config.SaleProperties;
import br.com.saleback.utils.BeanUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static final SaleProperties config = BeanUtil.getBean(SaleProperties.class);

    public Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName(config.getDataSource().getDriver());
            conn = DriverManager.getConnection(
                    config.getDataSource().getUrl(),
                    config.getDataSource().getUsername(),
                    config.getDataSource().getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
