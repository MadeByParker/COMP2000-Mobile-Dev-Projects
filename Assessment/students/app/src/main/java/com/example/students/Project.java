package com.example.students;

//project pojo
public class Project {

    private  int _projectID;
    private int _studentID;
    private String _title;
    private String _description;
    private int _year;
    private String _firstName;
    private String _lastName;
    private String _url;


    //serialise and deserialise functions

    public int getProjectId() { return _projectID; }

    public void setProjectId(int projectId){
        _projectID = projectId;
    }

    public int getStudentId() {
        return _studentID;
    }

    public void setStudentId(int StudentId) {
        _studentID = StudentId;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public int getYear() {
        return _year;
    }

    public void setYear(int year){
        _year = year;
    }

    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String firstName) {
        _firstName = firstName;
    }

    public String getLastName() {
        return _lastName;
    }

    public void setLastName(String lastName) {
        _lastName = lastName;
    }

    public String getURL() {
        return _url;
    }

    public void setURL(String url) {
        _url = url;
    }

}
