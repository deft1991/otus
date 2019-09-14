import lombok.extern.java.Log;
import ru.deft.homework.api.dao.CommonDao;
import ru.deft.homework.api.model.Account;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DBCommonService;
import ru.deft.homework.api.service.impl.DBCommonServiceAccountImpl;
import ru.deft.homework.api.service.impl.DBCommonServiceUserImpl;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.h2.DataSourceH2;
import ru.deft.homework.jdbc.DBExecutor;
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
        DataSource dataSource = new DataSourceH2();
        Main demo = new Main();

        //        demo.createUserTable(dataSource);
        //        demo.createAccountTable(dataSource);

        DBCommonService dbCommonServiceUser = getDBUserService(new SessionManagerJdbc(dataSource));

        DBCommonService dbCommonServiceAccount = getDDAccountService(new SessionManagerJdbc(dataSource));

        long firstUser = dbCommonServiceUser.save(new User(1L, "first", 12));

        long firstAccount = dbCommonServiceAccount.save(new Account(2L, "type T", 12));

        Optional<User> user = dbCommonServiceUser.findById(firstUser);
        user.ifPresent(crUser -> log.info("created user, name:" + crUser.getName()));

        Optional<Account> account = dbCommonServiceAccount.findById(firstUser);
        account.ifPresent(crUser -> log.info("created Account, type:" + crUser.getType()));
        System.out.println(user);
        System.out.println(account);
    }

    private static DBCommonService getDDAccountService(SessionManager sessionManager) {
        DBExecutor<Account> executorAccount = new DBExecutor<>();
        CommonDao commonAccountDao = new AccountDaoJdbc(sessionManager, executorAccount);
        return new DBCommonServiceAccountImpl(commonAccountDao);
    }

    private static DBCommonService getDBUserService(SessionManager sessionManager) {
        DBExecutor<User> executor = new DBExecutor<>();
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
