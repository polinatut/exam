package org.example;

class Student extends User {
    private double scholarship;
    private int course;

    public void setScholarship(double scholarship) {
        this.scholarship = scholarship;
    }

    public double getScholarship() {
        return scholarship;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getCourse() {
        return course;
    }
}
