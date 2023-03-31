package com.motionlabs.util;

import com.motionlabs.domain.member.Member;
import com.motionlabs.domain.member.MemberRepository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Component
@ActiveProfiles("test")
public class DatabaseCleaner implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        entityManager.unwrap(Session.class)
            .doWork(this::getTableNames);
    }

    public void setDatabase() {
        execute();
        createTestData();
    }

    @Transactional
    public void execute() {
        entityManager.unwrap(Session.class)
            .doWork(this::cleanUpDatabase);
    }

    public void createTestData() {
        memberRepository.save(new Member("test-user", "test@gmail.com"));
    }


    private void getTableNames(Connection conn) throws SQLException {
        List<String> tableNames = new ArrayList<>();

        ResultSet tables = conn
            .getMetaData()
            .getTables(conn.getCatalog(), "PUBLIC", "%", new String[]{"TABLE"});

        while(tables.next()) {
            tableNames.add(tables.getString("table_name"));
        }

        this.tableNames = tableNames;
    }

    private void cleanUpDatabase(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("SET REFERENTIAL_INTEGRITY FALSE");

        for (String tableName : tableNames) {
            statement.executeUpdate("TRUNCATE TABLE " + tableName);
            statement.executeUpdate("ALTER TABLE " + tableName + " ALTER COLUMN id RESTART WITH 1");
        }

        statement.executeUpdate("SET REFERENTIAL_INTEGRITY TRUE");
    }

}
