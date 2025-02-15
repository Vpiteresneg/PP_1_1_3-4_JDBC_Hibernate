package jm.task.core.jdbc.dao;

import com.mysql.cj.jdbc.ConnectionGroupManager;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.util.Util;//сами импортировали класс который сами же и создали


public class UserDaoJDBCImpl implements UserDao {

    private static final UserDaoJDBCImpl INSTANCE = new UserDaoJDBCImpl();
    private static final String DELETE_SQL = "DELETE FROM users WHERE id = ?";
    private static final String SAVE_SQL = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
    private static final String FINDE_ALL_SQL = "SELECT id, name, last_name, age FROM users";//имена в sql запросе в точности совпадают с именами в таблице
    String sql = "CREATE TABLE IF NOT EXISTS users ("//существует ли уже таблица с таким именем, и только в случае отсутствия таблицы она будет создана.
            + "id INTEGER PRIMARY KEY AUTO_INCREMENT, "
            + "name VARCHAR(50) NOT NULL, "
            + "last_name VARCHAR(50) NOT NULL, "
            + "age INTEGER NOT NULL)";

    String sqlDrop = "DROP TABLE IF EXISTS users";
    String sqlClean = "TRUNCATE TABLE users";

    public static UserDaoJDBCImpl getInstance() {
        return INSTANCE;
    }

    public UserDaoJDBCImpl() {

    }

    // Создание таблицы для User(ов) — не должно приводить к исключению, если такая таблица уже существует
    public void createUsersTable() {
        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {//Создает объект PreparedStatement для отправки параметризованных инструкций SQL в базу данных.
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Удаление таблицы User(ов) — не должно приводить к исключению, если таблицы не существует
    public void dropUsersTable() {
        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDrop)) {//Создает объект PreparedStatement для отправки параметризованных инструкций SQL в базу данных.
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Удаление User из таблицы (по id)
    public void removeUserById(long id) {
        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {//Создает объект PreparedStatement для отправки параметризованных инструкций SQL в базу данных.
            preparedStatement.setLong(1, id);//на место параметра с номером 1(т.е. вместо первого ? в запросе) подставляем значение переменной с именем id
            preparedStatement.executeUpdate();// выполняем нашу sql инструкцию
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Получение всех User(ов) из таблицы
    public List<User> getAllUsers() {
        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FINDE_ALL_SQL)) {//Создаем PreparedStatement с нужным запросом например DELETE_SQL
            ResultSet resultSet = preparedStatement.executeQuery();//Этот код обычно используется для работы с базой данных, чтобы извлечь данные из таблицы.выполняет SQL-запрос, который должен возвращать результат (например, SELECT запрос). Метод executeQuery() используется для выполнения запросов, которые возвращают набор данных (например, строки из таблицы).
            // ResultSet resultSet — это объект, который будет содержать строки данных, возвращенные SQL-запросом.
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"));

                // Добавляем пользователя в список
                users.add(user);
            }

            // Возвращаем список пользователей
            return users;


        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Очистка содержания таблицы
    public void cleanUsersTable() {
        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlClean)) {//Создает объект PreparedStatement для отправки параметризованных инструкций SQL в базу данных.
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

//обращение к базе данных используя jdbc подключение к бд и отпраквка команд
// Dao класс нужен только для связи, взаимодействием с базой данной и только классы Dao могут с ней
// связываться ни какой другой класс не может этого