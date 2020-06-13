package database;

import database.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static Database instance;

    private Connection connection;

    public int getCount() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) FROM Client");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("count(*)");
    }

    public List<Client> getClients(int gender, String name, String number, boolean birthDayInCurrentMonth) throws SQLException {
        connect();

        StringBuilder sb = new StringBuilder("SELECT * FROM Client WHERE " +
                Client.FirstName + " LIKE '%" + name + "%' AND " +
                Client.Phone + " LIKE '%" + number + "%' ");

        if (gender != 0) sb.append("AND " + Client.GenderCode + " = " + gender);
        if (birthDayInCurrentMonth) sb.append(" AND MONTH(" + Client.Birthday + ") = MONTH(CURRENT_DATE())");

        sb.append(" ORDER BY " + Client.LastName);

        System.out.println(sb.toString());

        PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Client> clients = new ArrayList<>();
        while (resultSet.next()) {
            clients.add(new Client(
                    resultSet.getInt(Client.ID),
                    resultSet.getString(Client.FirstName),
                    resultSet.getString(Client.LastName),
                    resultSet.getString(Client.Patronymic),
                    resultSet.getString(Client.Birthday),
                    resultSet.getString(Client.RegistrationDate),
                    resultSet.getString(Client.Email),
                    resultSet.getString(Client.Phone),
                    resultSet.getInt(Client.GenderCode),
                    resultSet.getString(Client.PhotoPath)
            ));
        }
        return clients;
    }

    public void removeClient(int id) throws SQLException {
        connect();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Client WHERE " + Client.ID + " = " + id);
        statement.executeUpdate();
    }

    void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?serverTimezone=UTC", "root", "1234");
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
}
