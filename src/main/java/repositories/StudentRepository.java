package repositories;

import database.Database;
import models.Student;
import repositories.interfaces.IStudentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRepository implements IStudentRepository {
    private Database database;

    public StudentRepository(Database database) {
        this.database = database;
    }

    @Override
    public boolean doesUserExist(int user_id) {
        String query = "SELECT 1 FROM Students WHERE user_id = ?";
        try (Connection con = database.getConnection();
             PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public void saveStudent(Student student) {
        String sql = "INSERT INTO Students (user_id, name, surname, barcode) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (user_id) DO NOTHING;";

        try (Connection con = database.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, student.getUser_id());
            st.setString(2, student.getName());
            st.setString(3, student.getSurname());
            st.setInt(4, student.getBarcode());

            st.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error occurred while saving user data: " + e.getMessage());
        }
    }
}
