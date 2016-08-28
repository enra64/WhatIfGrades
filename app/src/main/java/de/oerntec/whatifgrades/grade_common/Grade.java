package de.oerntec.whatifgrades.grade_common;

public class Grade {
    public int id;
    public int credits;
    public int grade;
    public String name;

    public static final String[] GRADE_NAMES = {"1.0", "1.3", "1.7", "2.0", "2.3", "2.7", "3.0", "3.3", "3.7", "4.0"};

    public Grade(int id, int credits, int grade, String name) {
        this.id = id;
        this.credits = credits;
        this.grade = grade;
        this.name = name;
    }

    public float getGradeFloat(){
        return Float.valueOf(GRADE_NAMES[grade]);
    }
}
