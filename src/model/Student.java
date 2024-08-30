package model;

public class Student {
    private String id;
    private String name;
    private String dateOfBirth;
    private String classroom;
    private String subject;

    public Student() {
    }

    public Student(String id, String name, String dateOfBirth, String classroom, String subject) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.classroom = classroom;
        this.subject = subject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", classroom='" + classroom + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
