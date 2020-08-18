package Model;

public class User {

    public  int UserID;
    public String FirstName;
    public String LastName;
    public String  ContactNumber;
    public String Email;
    public String Password;
    public String Role;
    public boolean IsRepresentative;
    public int ReportToID;
    public int DepartmentID;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public boolean isRepresentative() {
        return IsRepresentative;
    }

    public void setRepresentative(boolean representative) {
        IsRepresentative = representative;
    }

    public int getReportToID() {
        return ReportToID;
    }

    public void setReportToID(int reportToID) {
        ReportToID = reportToID;
    }

    public int getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(int departmentID) {
        DepartmentID = departmentID;
    }
}
