package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Student;

public class StudentDAO implements CRUD_Operation<Student,String>{
	
    private Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }
	@Override
	public void save(Student student) {
	       String sql = "INSERT INTO Student2 (id, name, email, total_credits) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setString(1, student.getId());
	            stmt.setString(2, student.getName());
	            stmt.setString(3, student.getEmail());
	            stmt.setInt(4, student.getTotalCredits());
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }		
	}

	@Override
	public ArrayList<Student> fetch() {
        ArrayList<Student> students= new ArrayList<>();
        String sql = "SELECT * FROM Student2";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
            	
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int totalCredits = rs.getInt("total_credits");
                
                
                Student student = new Student(id, name, email, totalCredits);
                students.add(student);
             }               
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
	}

	@Override
	public void update(Student student) {
        String sql = "UPDATE Student2 SET name=?, email=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, student.getId());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getEmail());
            stmt.setInt(4, student.getTotalCredits());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }		
	}

	@Override
	public void delete(String id) {
        String sql = "DELETE FROM Student2 WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public boolean authenticate(String id) {
        String sql = "SELECT id FROM Student2 WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("id").equals(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
	}

}
