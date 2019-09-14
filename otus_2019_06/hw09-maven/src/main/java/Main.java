import lombok.extern.java.Log;
import ru.deft.homework.api.dao.CommonDao;
import ru.deft.homework.api.model.Account;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DBCommonService;
import ru.deft.homework.api.service.impl.DBCommonServiceAccountImpl;
import ru.deft.homework.api.service.impl.DBCommonServiceUserImpl;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.h2.DataSourceH2;
import ru.deft.homework.jdbc.DbExecutor;
import ru.deft.homework.jdbc.dao.AccountDaoJdbc;
import ru.deft.homework.jdbc.dao.UserDaoJdbc;
import ru.deft.homework.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@Log public class Main {

    //    public static void main(String[] args) throws SQLException {
    //        //        demo.createUserTable(dataSource);
    //        //        demo.createAccountTable(dataSource);
    //
    //
    //
    //        //        DBCommonService dbCommonServiceAccount = getDDAccountService(sessionManager);
    //
    //        //        long firstUser = dbCommonServiceUser.save(new User(1L, "first", 12));
    //
    //        //        long firstAccount = dbCommonServiceAccount.save(new Account(2L, "type T", 12));
    //
    //        //        Optional<User> user = dbCommonServiceUser.findById(firstUser);
    //        //        user.ifPresent(crUser -> log.info("created user, name:" + crUser.getName()));
    //        //
    //        //        Optional<Account> account = dbCommonServiceAccount.findById(firstUser);
    //        //        account.ifPresent(crUser -> log.info("created Account, type:" + crUser.getType()));
    //        //        System.out.println(user);
    //        //        System.out.println(account);
    //

    //
    //        DBCommonService dbCommonServiceUser = getDBUserService(sessionManager);
    ////        List<String> list = new ArrayList<>();
    ////        list.add(String.valueOf(1L));
    ////        list.add("qqq");
    ////        list.add("12");
    ////        String sql = "insert into user(id, name, age) values (1,'qqq',12)";
    ////        String sql2 = "select id from user where name = 'qqq'";
    ////        stmt.executeUpdate(sql);
    ////        ResultSet resultSet = stmt.executeQuery(sql2);
    //
    //        dbCommonServiceUser.save(new User(1L, "first", 12));
    //        Optional<User> userFromBd = dbCommonServiceUser.findById(1L);
    //        System.out.println(userFromBd);
    //
    //    }

    private static final String URL = "jdbc:h2:mem:";

    public static void main(String[] args) throws SQLException {
        Main demo = new Main();
        DataSource dataSourceH2 = new DataSourceH2();
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSourceH2);
        DBCommonService dbCommonServiceUser = getDBUserService(sessionManager);

        long userId = dbCommonServiceUser.save(new User("qqq", 20));
        log.info("created user: " + userId);

        Optional<User> user = dbCommonServiceUser.findById(userId);
        System.out.println(user.get().getName());

    }

    private void createTable(Connection connection) throws SQLException {
        try (PreparedStatement pst = connection
                .prepareStatement("create table user(id long auto_increment, name varchar(50),  age int(3))")) {
            pst.executeUpdate();
        }
    }

    private static DBCommonService getDDAccountService(SessionManager sessionManager) {
        DbExecutor<Account> executorAccount = new DbExecutor<>();
        CommonDao commonAccountDao = new AccountDaoJdbc(sessionManager, executorAccount);
        return new DBCommonServiceAccountImpl(commonAccountDao);
    }

    private static DBCommonService getDBUserService(SessionManagerJdbc sessionManager) {
        DbExecutor<User> executor = new DbExecutor<>();
        CommonDao commonUserDao = new UserDaoJdbc(sessionManager, executor);
        return new DBCommonServiceUserImpl(commonUserDao);
    }

    private void createUserTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection(); PreparedStatement pst = connection
                .prepareStatement("create table User(id long auto_increment, name varchar(50), age int(3))")) {
            pst.executeUpdate();
        }
        dataSource.getConnection().commit();
        System.out.println("table User created");
    }

    private void createAccountTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection(); PreparedStatement pst = connection
                .prepareStatement("create table Account(id long auto_increment, type varchar(50), rest number)")) {
            pst.executeUpdate();
        }
        dataSource.getConnection().commit();
        System.out.println("table Account created");
    }

}
