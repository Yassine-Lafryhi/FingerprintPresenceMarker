package me.fingerprint.presence.marker.models;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private boolean absent;

    public Student() {
    }

    public Student(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(int id, String firstName, String lastName, boolean absent) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.absent = absent;
    }

    public boolean isAbsent() {
        return absent;
    }

    public void setAbsent(boolean absent) {
        this.absent = absent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
