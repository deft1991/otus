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

    public static void main(String[] args) throws SQLException {
        Main main = new Main();
        DataSource dataSourceH2 = new DataSourceH2();
        main.createAccountTable(dataSourceH2);
        main.createUserTable(dataSourceH2);
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSourceH2);
        DBCommonService dbCommonServiceUser = getDBUserService(sessionManager);
        DBCommonService dbCommonServiceAccount = getDDAccountService(sessionManager);

        long userId = dbCommonServiceUser.save(new User("qqq", 20));
//        log.info("created user: " + userId);
//
//        Optional<User> user = dbCommonServiceUser.findById(userId);
//        System.out.println(user.get().getName());
//        dbCommonServiceUser.update(new User(userId, "www", 30));
//        user = dbCommonServiceUser.findById(userId);
//        System.out.println(user.get().getName());

        long accountId = dbCommonServiceAccount.save(new Account("type", 20));
        log.info("created account: " + accountId);
        Optional<Account> account = dbCommonServiceAccount.findById(accountId);
        System.out.println("account: " + account.get().getType());
        dbCommonServiceAccount.update(new Account(accountId, "epyt", 11));
        account = dbCommonServiceAccount.findById(accountId);
        System.out.println(account.get().getType());
//        dbCommonServiceUser.createOrUpdate(new User(11L, "aaaaaaaa", 2));
//        user = dbCommonServiceUser.findById(11L);
//        System.out.println(user.get().getName());
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
                .prepareStatement("create table if not exists User(id long auto_increment, name varchar(50), age int(3))")) {
            pst.executeUpdate();
        }
        dataSource.getConnection().commit();
        System.out.println("table User created");
    }

    private void createAccountTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection(); PreparedStatement pst = connection
                .prepareStatement("create table if not exists Account(id long auto_increment, type varchar(50), rest number)")) {
            pst.executeUpdate();
        }
        dataSource.getConnection().commit();
        System.out.println("table Account created");
    }

}
