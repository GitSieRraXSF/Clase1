package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Course;
import model.Student;;

public class CourseDAO implements CRUD_Operation<Course,String> {
    private Connection connection;

    public CourseDAO(Connection connection) {
        this.connection = connection;
    }
	@Override
	public void save(Course course) {
	       String sql = "INSERT INTO Course2 (code, name, credits, quota) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setString(1, course.getCode());
	            stmt.setString(2, course.getName());
	            stmt.setInt(3, course.getCredits());
	            stmt.setInt(4, course.getQuota());
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }	
		
	}

	@Override
	public ArrayList<Course> fetch() {
        ArrayList<Course> courses= new ArrayList<>();
        String sql = "SELECT * FROM Course2";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
            	
                String code = rs.getString("code");
                String name = rs.getString("name");
                int credits = rs.getInt("credits");
                int quota = rs.getInt("quota");
                
                
                Course course = new Course(code, name, credits, quota);
                courses.add(course);
             }               
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
	}

	@Override
	public void update(Course course) {
        String sql = "UPDATE Course2 SET name=?, credits=?, quota=? WHERE code=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, course.getCode());
            stmt.setString(2, course.getName());
            stmt.setInt(3, course.getCredits());
            stmt.setInt(4, course.getQuota());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }		
		
	}

	@Override
	public void delete(String code) {
        String sql = "DELETE FROM Course2 WHERE code=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, code);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public boolean authenticate(String code) {
        String sql = "SELECT code FROM Course2 WHERE code=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("code").equals(code);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
	}

}
