package model;

public class Student {
    private String id;
    private String name;
    private String email;
    private int totalCredits;

    public Student(String id, String name, String email, int totalCredits) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.totalCredits = totalCredits;
    }

    public int getTotalCredits() {
		return totalCredits;
	}

	public void setTotalCredits(int totalCredits) {
		this.totalCredits = totalCredits;
	}

	// Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

