package model;

public class Course {
    private String code;
    private String name;
    private int credits;
    private int quota;

    public Course(String code, String name, int credits, int quota) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.quota = quota;
    }

    public int getQuota() {
		return quota;
	}

	public void setQuota(int quota) {
		this.quota = quota;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Getters
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    // Setters
    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String name) {
        this.name = name;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

}
