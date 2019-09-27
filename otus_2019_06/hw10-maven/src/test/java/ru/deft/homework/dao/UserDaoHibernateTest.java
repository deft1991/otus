package ru.deft.homework.dao;

/*
 * Created by sgolitsyn on 9/22/19
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.deft.homework.AbstractHibernateTest;
import ru.deft.homework.api.dao.UserDao;
import ru.deft.homework.api.model.Address;
import ru.deft.homework.api.model.Phone;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.hibernate.dao.UserDaoHibernate;
import ru.deft.homework.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с пользователями должно ")
public class UserDaoHibernateTest extends AbstractHibernateTest {

    private SessionManager sessionManagerHibernate;
    private UserDao userDaoHibernate;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
    }

    @Test
    @DisplayName(" корректно загружать пользователя по заданному id")
    void shouldFindCorrectUserById() {
        User expectedUser = new User(0L, "Вася");
        saveUser(expectedUser);

        assertThat(expectedUser.getId()).isGreaterThan(0);

        sessionManagerHibernate.beginSession();
        User mayBeUser = userDaoHibernate.getById(expectedUser.getId());
        sessionManagerHibernate.commitSession();

        assertThat(mayBeUser).isEqualToComparingOnlyGivenFields(expectedUser, "name");
    }

    @ParameterizedTest(name = "корректно сохранять пользователя c адрессом : {0},  с телефонами : {1}")
    @CsvSource({"false,false", "true,false", "false,true", "true,true"})
    void shouldCorrectSaveUser(boolean withAddress, boolean withPhones) {
        User expectedUser = new User(0L, "Вася");
        Address expectedAddress = new Address();
        List<Phone> expectedPhones = Arrays.asList(new Phone("11-11"), new Phone("22-22"));
        if (withAddress) {
            expectedAddress = new Address("vorobieva");
            expectedUser.setAddress(expectedAddress);
        }
        if (withPhones) {
            expectedUser.addPhone(new Phone("11-11"));
            expectedUser.addPhone(new Phone("22-22"));
        }
        sessionManagerHibernate.beginSession();
        long id = userDaoHibernate.save(expectedUser);
        sessionManagerHibernate.commitSession();

        assertThat(id).isGreaterThan(0);

        User actualUser;
        if (withPhones) {
            actualUser = loadUserWithPhones(id);
        } else {
            actualUser = loadUser(id);
        }
        assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("name", expectedUser.getName());
        if (withAddress) {
            assertThat(actualUser.getAddress()).isNotNull().hasFieldOrPropertyWithValue("street", expectedAddress.getStreet());
        }
        if (withPhones) {
            List<Phone> phones = actualUser.getPhones();
            assertThat(phones).isNotNull().hasSameSizeAs(expectedPhones);
        }

        expectedUser = new User(id, "Не Вася");
        sessionManagerHibernate.beginSession();
        long newId = userDaoHibernate.save(expectedUser);
        sessionManagerHibernate.commitSession();

        assertThat(newId).isGreaterThan(0).isEqualTo(id);
        actualUser = loadUser(newId);
        assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("name", expectedUser.getName());

    }

    @DisplayName(" корректно сохранять пользователя  c измененным адрессом")
    @Test
    void shouldCorrectSaveUserWithChangedAddress() {
        User expectedUser = new User(0L, "Вася");
        sessionManagerHibernate.beginSession();
        Address expectedAddress = new Address("vorobieva");
        expectedUser.setAddress(expectedAddress);
        long id = userDaoHibernate.save(expectedUser);
        sessionManagerHibernate.commitSession();

        assertThat(id).isGreaterThan(0);

        User actualUser = loadUser(id);
        assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("name", expectedUser.getName());
        assertThat(actualUser.getAddress()).isNotNull().hasFieldOrPropertyWithValue("street", expectedAddress.getStreet());

        actualUser.getAddress().setStreet("mendeleeva");
        sessionManagerHibernate.beginSession();
        id = userDaoHibernate.update(actualUser);
        sessionManagerHibernate.commitSession();
        User afterAddrUpdateUser = loadUser(id);
        assertThat(afterAddrUpdateUser).isNotNull().hasFieldOrPropertyWithValue("name", actualUser.getName());
        assertThat(afterAddrUpdateUser.getAddress()).isNotNull()
                .hasFieldOrPropertyWithValue("street", actualUser.getAddress().getStreet());
    }

    @DisplayName(" возвращать менеджер сессий")
    @Test
    void getSessionManager() {
        assertThat(userDaoHibernate.getSessionManager()).isNotNull().isEqualTo(sessionManagerHibernate);
    }
}
