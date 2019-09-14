package ru.deft.homework.jdbc;

import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Log public class DbExecutor<T> {

    public long insertRecord(Connection connection, String sql, List<String> params) throws SQLException {
        Savepoint savePoint = connection.setSavepoint("savePointName");
        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int idx = 0; idx < params.size(); idx++) {
                pst.setString(idx + 1, params.get(idx));
            }
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            connection.rollback(savePoint);
            log.warning(ex.getMessage());
            throw ex;
        }
    }

        public long updateRecord(Connection connection, String sql, List<String> params) throws SQLException {
            Savepoint savePoint = connection.setSavepoint("savePoint");
            try (PreparedStatement pst = connection.prepareStatement(sql)) {
                for (int i = 0; i < params.size(); i++) {
                    pst.setString(i + 1, params.get(i));
                }
               return pst.executeUpdate();
            } catch (SQLException ex) {
                connection.rollback(savePoint);
                log.info(ex.getMessage());
                throw ex;
            }

        }

    public Optional<T> selectRecord(Connection connection, String sql, long id, Function<ResultSet, T> rsHandler)
            throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(rs));
            }
        }
    }
}
