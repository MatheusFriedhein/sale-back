package br.com.saleback.dao;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class ProductDAO {

    private String sql;
    private String status = "";

    public String deleteById(UUID id) {
        try (Connection connection = new ConnectionDB().getConnection()) {
            sql = "DELETE FROM public.product WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
            this.status = preparedStatement.getUpdateCount() > 0 ? "OK" : "Ocorreu um erro";
        } catch (SQLException e) {
            e.printStackTrace();
            this.status = e.getMessage();
        }
        return status;
    }
}
