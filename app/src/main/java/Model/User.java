package Model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("userID")
    public  int UserID;
    @SerializedName("firstName")
    public String FirstName;
    @SerializedName("lastName")
    public String LastName;
    @SerializedName("designation")
    public String designation;
    @SerializedName("contactNumber")
    public String  ContactNumber;
    @SerializedName("email")
    public String Email;
    @SerializedName("password")
    public String Password;
    @SerializedName("role")
    public String Role;
    @SerializedName("isRepresentative")
    public boolean IsRepresentative;
    @SerializedName("reportToID")
    public int ReportToID;
    @SerializedName("departmentID")
    public int DepartmentID;

    @SerializedName("department")
    public String Department;
    @SerializedName("requestMade")
    public String RequestMade;
    @SerializedName("requestModified")
    public String RequestModified;
    @SerializedName("requestedBy")
    public String RequestedBy;
    @SerializedName("approvedBy")
    public String ApprovedBy;
    @SerializedName("delegatedManager")
    public String DelegatedManager;

    //public DelegatedManager DelegatedManager;


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
