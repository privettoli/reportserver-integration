package org.spend.reportserver.integration.repository;

import org.spend.reportserver.integration.dto.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import play.libs.F.Promise;
import scala.concurrent.ExecutionContext;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;
import static play.Play.application;
import static play.libs.F.Promise.promise;

@Repository
public class ReportRepositoryJdbcImpl implements ReportRepository {
    private static final String TABLE = "`RS_REPORT`";
    private static Connection connection;

    @Autowired
    @Qualifier("databaseExecutionContext")
    private ExecutionContext databaseExecutionContext;

    @Override
    public Promise<Boolean> exist(Integer id) {
        return promise(() -> {
            try {
                final ResultSet resultSet = connection.createStatement().executeQuery("SELECT count(*) as `count` FROM " + TABLE + " WHERE `id`=" + id);
                resultSet.next();
                return resultSet.getInt("count") == 1;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, databaseExecutionContext);
    }

    @Override
    public Promise<List<ReportDto>> getAll() {
        return promise(() -> {
            try {
                List<ReportDto> reports = new LinkedList<>();
                final Statement statement = connection.createStatement();
                final ResultSet resultSet = statement.executeQuery("SELECT `NAME_FIELD`, `KEY_FIELD`, `id` FROM " + TABLE);

                while (!resultSet.isLast()) {
                    resultSet.next();
                    final ReportDto reportDto = new ReportDto();
                    reportDto.setName(resultSet.getString("NAME_FIELD"));
                    reportDto.setId(resultSet.getInt("id"));
                    reportDto.setKey(resultSet.getString("KEY_FIELD"));
                    reports.add(reportDto);
                }
                return reports;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, databaseExecutionContext);
    }

    @PostConstruct
    public void initDatabaseConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", application().configuration().getString("database.username"));
        connectionProps.put("password", application().configuration().getString("database.password"));
        connection = getConnection(application().configuration().getString("database.url"), connectionProps);
    }
}
