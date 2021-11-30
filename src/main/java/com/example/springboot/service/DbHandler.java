package com.example.springboot.service;

import com.example.springboot.model.Hero;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.sqlite.JDBC;

import javax.validation.Valid;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Validated
public class DbHandler {

    // Константы, в которой хранится URL пользователь и пароль
    private static final String URL = "jdbc:mysql://localhost:3306/hero?useLegacyDatetimeCode=false&serverTimezone=Australia/Sydney&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "mmm333";

    // Используем шаблон "singleton", чтобы не плодить множество экземпляров класса DbHandler
    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    private DbHandler() throws SQLException {
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(URL, USER, PASS);
    }

    // Получение списка Hero из БД
    public List<Hero> getAllHeroes() {
        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()) {
            // В данный список будем загружать наши продукты, полученные из БД
            List<Hero> heroes = new ArrayList<Hero>();
            // В resultSet будет храниться результат нашего запроса, который выполняется командой statement.executeQuery()
            ResultSet resultSet = statement.executeQuery("SELECT id, name, level, ultimate FROM heroes");
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                heroes.add(new Hero(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("level"),
                        resultSet.getString("ultimate")));
            }
            // Возвращаем наш список
            return heroes;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }

    // Получение Hero из БД по ID
    public Hero getHero(int id) {

        try (Statement statement = this.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM heroes WHERE id = " + id);
            Hero hero = new Hero();
            while (resultSet.next()) {
                hero.setId(resultSet.getInt("id"));
                hero.setName(resultSet.getString("name"));
                hero.setLevel(resultSet.getInt("level"));
                hero.setUltimate(resultSet.getString("ultimate"));
            }
            System.out.println("ГЕРОЙ - " + hero);
            return hero;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    // Добавление Hero в БД
    public void addHero(@Valid Hero hero) {
        // Создадим готовое выражение, чтобы избежать SQL-инъекций
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO heroes(`name`, `level`, `ultimate`) " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, hero.name);
            statement.setObject(2, hero.level);
            statement.setObject(3, hero.ultimate);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление Hero по id
    public void deleteHero(int id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM heroes WHERE id = ?")) {
            statement.setObject(1, id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}