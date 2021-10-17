package br.com.saleback.dao;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@Service
public class SolicitationDAO {

    private String sql;
    private String status = "";

    public String deleteById(UUID id) {
        try (Connection connection = new ConnectionDB().getConnection()) {
            sql = "DELETE FROM public.solicitation WHERE id = ?;";
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
