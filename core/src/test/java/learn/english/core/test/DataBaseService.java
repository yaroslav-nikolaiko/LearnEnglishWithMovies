package learn.english.core.test;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaroslav on 12/21/14.
 */
@Stateless
public class DataBaseService {
    @Inject
    EntityManager em;
    String insertQuery;

    @PostConstruct
    public void init() {
        try {
            insertQuery = new String (Files.readAllBytes(Paths.get("src/test/resources/insert.sql")), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insert() throws Exception {
        Query nativeQuery = em.createNativeQuery(insertQuery);
        nativeQuery.executeUpdate();
    }

    public void clear() throws SQLException {
        java.sql.Connection connection = em.unwrap(java.sql.Connection.class);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("show tables");
        List<String> tables = new ArrayList<>();
        while(resultSet.next())
            tables.add(resultSet.getString(1));
        resultSet.close();
        statement.executeUpdate("SET REFERENTIAL_INTEGRITY FALSE");

        for (String table : tables)
            statement.executeUpdate("TRUNCATE TABLE " + table);

        statement.executeUpdate("SET REFERENTIAL_INTEGRITY TRUE");
        statement.close();
        connection.close();
    }
}
