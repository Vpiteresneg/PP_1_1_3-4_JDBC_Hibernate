package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Иван", "Иванов", (byte) 44);
        System.out.println("User с именем — Иван добавлен в базу данных");
        userService.saveUser("Петр", "Петров", (byte) 33);
        System.out.println("User с именем — Петр добавлен в базу данных");
        userService.saveUser("Василий", "Купров", (byte) 18);
        System.out.println("User с именем — Василий добавлен в базу данных");
        userService.saveUser("Мария", "Попова", (byte) 24);
        System.out.println("User с именем — Мария добавлен в базу данных");

        UserDaoJDBCImpl.getInstance().getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

        //userService.removeUserById(5);


    }
}
