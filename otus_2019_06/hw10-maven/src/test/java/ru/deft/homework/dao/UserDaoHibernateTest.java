package ru.deft.homework.dao;

/*
 * Created by sgolitsyn on 9/22/19
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.deft.homework.AbstractHibernateTest;
import ru.deft.homework.api.dao.BaseDao;
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
    private BaseDao userDaoHibernate;

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
        User mayBeUser = (User) userDaoHibernate.getById(expectedUser.getId());
        sessionManagerHibernate.commitSession();

        assertThat(mayBeUser).isEqualToIgnoringNullFields(expectedUser);
    }

    @DisplayName(" корректно сохранять пользователя")
    @Test
    void shouldCorrectSaveUser() {
        User expectedUser = new User(0L, "Вася");
        sessionManagerHibernate.beginSession();
        long id = userDaoHibernate.save(expectedUser);
        sessionManagerHibernate.commitSession();

        assertThat(id).isGreaterThan(0);

        User actualUser = loadUser(id);
        assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("name", expectedUser.getName());

        expectedUser = new User(id, "Не Вася");
        sessionManagerHibernate.beginSession();
        long newId = userDaoHibernate.save(expectedUser);
        sessionManagerHibernate.commitSession();

        assertThat(newId).isGreaterThan(0).isEqualTo(id);
        actualUser = loadUser(newId);
        assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("name", expectedUser.getName());

    }

    @DisplayName(" корректно сохранять пользователя  c адрессом")
    @Test
    void shouldCorrectSaveUserWithAddress() {
        User expectedUser = new User(0L, "Вася");
        sessionManagerHibernate.beginSession();
        Address expectedAddress = new Address() {{
            setStreet("vorobieva");
        }};
        expectedUser.setAddress(expectedAddress);
        long id = userDaoHibernate.save(expectedUser);
        sessionManagerHibernate.commitSession();

        assertThat(id).isGreaterThan(0);

        User actualUser = loadUser(id);
        assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("name", expectedUser.getName());
        assertThat(actualUser.getAddress()).isNotNull().hasFieldOrPropertyWithValue("street", expectedAddress.getStreet());

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

    @DisplayName(" корректно сохранять пользователя  c телефонами")
    @Test
    void shouldCorrectSaveUserWithPhones() {
        User expectedUser = new User(0L, "Вася");
        sessionManagerHibernate.beginSession();
        List<Phone> expectedPhones = Arrays.asList(new Phone("11-11"), new Phone("22-22")
        );
        expectedUser.addPhone(new Phone("11-11"));
        expectedUser.addPhone(new Phone("22-22"));
        long id = userDaoHibernate.save(expectedUser);
        sessionManagerHibernate.commitSession();
        assertThat(id).isGreaterThan(0);

        User actualUser = loadUserWithPhones(id);
        List<Phone> phones = actualUser.getPhones();

        assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("name", expectedUser.getName());
        assertThat(phones).isNotNull().hasSameSizeAs(expectedPhones);

    }

    @DisplayName(" возвращать менеджер сессий")
    @Test
    void getSessionManager() {
        assertThat(userDaoHibernate.getSessionManager()).isNotNull().isEqualTo(sessionManagerHibernate);
    }
}
