package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();


    // Создание таблицы для User(ов) — не должно приводить к исключению, если такая таблица уже существует
    public void createUsersTable() {
        userDaoJDBC.createUsersTable();
    }

    // Удаление таблицы User(ов) — не должно приводить к исключению, если таблицы не существует
    public void dropUsersTable() {
        userDaoJDBC.dropUsersTable();
    }

    // Добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        userDaoJDBC.saveUser(name, lastName, age);

    }

    // Удаление User из таблицы (по id)
    public void removeUserById(long id) {
        userDaoJDBC.removeUserById(id);
    }

    // Получение всех User(ов) из таблицы
    public List<User> getAllUsers() {
        return userDaoJDBC.getAllUsers();

    }

    // Очистка содержания таблицы
    public void cleanUsersTable() {
        userDaoJDBC.cleanUsersTable();
    }
}





