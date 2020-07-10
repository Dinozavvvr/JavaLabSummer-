package ru.itis.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainConnection {


    private static final String URL = "jdbc:postgresql://localhost:5432/sport_archive";
    private static final String USER = "postgres";
    private static final String PASSWORD = "6363";


    public static void main(String[] args) throws SQLException {
        SimpleDataSource dataSource = new SimpleDataSource();
        // подключение к базе данных
        Connection connection = dataSource.openConnection(URL, USER, PASSWORD);
        // создаем выражение для отправки запросов в бд
        Statement statement = connection.createStatement();
        // получаем результат запроса
        ResultSet resultSet = statement.executeQuery("select * from competition");
        // пробегаем по результирующему множеству
        while (resultSet.next()) {
            // выводим информацию по каждой строке
            System.out.println("Champion id: " + resultSet.getInt("champion_id") + " | " +
                    "Year: " + resultSet.getString("year") + " | " +
                    "Country id: " + resultSet.getString("country_id") + " | " +
                    "Sport: " + resultSet.getString("sport"));

        }
        // закрывем выражение для отправки запросов в бд
        resultSet.close();

        resultSet = statement.executeQuery("select a.first_name as name, a.last_name as lastname " +
                "from athlete a " +
                "inner join competition c " +
                "on a.id = c.champion_id " +
                "where c.country_id = 3");

        System.out.println("\nСhampions of competitions in Germany in the year 2020\n");

        while (resultSet.next()) {
            System.out.println("Name: " + resultSet.getString("name") + " | " +
                    "Last name: " + resultSet.getString("lastname"));
        }

        connection.close();
    }
}