package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDate;

import model.Course;
import model.Enrollment;
import model.Student;

public class EnrollmentDAO {
    private Connection connection;

    public EnrollmentDAO(Connection connection) {
        this.connection = connection;
    }

//	public void save(Enrollment enrollment) {
//	       String sql = "INSERT INTO Enrollment2 (student_id, course_code, enrollment_date) VALUES (?, ?, ?)";
//	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//	            stmt.setString(1, enrollment.getStudentId());
//	            stmt.setString(2, enrollment.getCourseCode());
//	            stmt.setDate(3, java.sql.Date.valueOf(enrollment.getEnrollmentDate())); 
//	            stmt.executeUpdate();
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        }	
//
//	}
    
    public void save(Enrollment enrollment, Student student, Course course) throws SQLException {
        String insertSQL = "INSERT INTO Enrollment2 (student_id, course_code, enrollment_date) VALUES (?, ?, ?)";
        String updateStudentSQL = "UPDATE Student2 SET total_credits = ? WHERE id = ?";
        String updateCourseSQL = "UPDATE Course2 SET quota = ? WHERE code = ?";

            connection.setAutoCommit(false); // Begin transaction 

            try (
                PreparedStatement insertStmt = connection.prepareStatement(insertSQL);
                PreparedStatement updateStudentStmt = connection.prepareStatement(updateStudentSQL);
                PreparedStatement updateCourseStmt = connection.prepareStatement(updateCourseSQL)
            ) {
                // Step 1: Insert Enrollment 
                insertStmt.setString(1, enrollment.getStudentId());
                insertStmt.setString(2, enrollment.getCourseCode());
                insertStmt.setDate(3, java.sql.Date.valueOf(enrollment.getEnrollmentDate()));
                insertStmt.executeUpdate();

                // Step 2: Update Student total credits 
                int updatedCredits = student.getTotalCredits() + course.getCredits();
                updateStudentStmt.setInt(1, updatedCredits);
                updateStudentStmt.setString(2, student.getId());
                updateStudentStmt.executeUpdate();

                // Step 3: Update Course quota
                int updatedQuota = course.getQuota() - 1;
                updateCourseStmt.setInt(1, updatedQuota);
                updateCourseStmt.setString(2, course.getCode());
                updateCourseStmt.executeUpdate();

                // Step 4: Commit transaction
                connection.commit();
                System.out.println("Enrollment, student, and course updated successfully.");

            } catch (SQLException e) {
                connection.rollback(); // Roll back all changes if any fail
                System.err.println("Transaction failed. Rolled back. " + e.getMessage());
            } finally {
                connection.setAutoCommit(true); // Reset auto-commit
            }      
    }



	public ArrayList<Enrollment> fetch() {
        ArrayList<Enrollment> enrollments= new ArrayList<>();
        String sql = "SELECT * FROM Enrollment2";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
            	
                String studentId = rs.getString("student_id");
                String courseCode = rs.getString("course_code");
                LocalDate enrollmentDate = rs.getDate("enrollment_date").toLocalDate();            
            
                Enrollment enrollment = new Enrollment(studentId, courseCode, enrollmentDate);
                enrollments.add(enrollment);
             }               
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
	}


	public void update(Enrollment enrollment) {
        String sql = "UPDATE Enrollment2 SET enrollment_date=? WHERE student_id=? AND course_code=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, enrollment.getStudentId());
            stmt.setString(2, enrollment.getCourseCode());
            stmt.setDate(3, java.sql.Date.valueOf(enrollment.getEnrollmentDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }	
	}




	public void delete(String id, String code) {
        String sql = "DELETE FROM Enrollment2 WHERE WHERE student_id=? AND course_code=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setString(1, code);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

	}

	public boolean authenticate(String id, String code) {
	    String sql = "SELECT student_id, course_code FROM Enrollment2 WHERE student_id = ? AND course_code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setString(2, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("student_id").equals(id) && rs.getString("course_code").equals(code);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
	}
	

}
