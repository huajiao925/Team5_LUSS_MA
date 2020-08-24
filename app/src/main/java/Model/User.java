package Model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("userID")
    public  int UserID;
    @SerializedName("firstName")
    public String FirstName;
    @SerializedName("lastName")
    public String LastName;
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
/*
    @SerializedName("department")
    public Department Department;
   @SerializedName("requestMade")
    public Request[] RequestMade;
    @SerializedName("requestModified")
    public Request[] RequestModified;
    @SerializedName("requestedBy")
    public AdjustmentVoucher[] RequestedBy;
    @SerializedName("approvedBy")
    public AdjustmentVoucher[] ApprovedBy;
    @SerializedName("delegatedManager")
    public DelegatedManager Delegatedmanager;
    */

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

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
   /*
    public Model.Department getDepartment() {
        return Department;
    }

    public void setDepartment(Model.Department department) {
        Department = department;
    }

    public Request[] getRequestMade() {
        return RequestMade;
    }

    public void setRequestMade(Request[] requestMade) {
        RequestMade = requestMade;
    }

    public Request[] getRequestModified() {
        return RequestModified;
    }

    public void setRequestModified(Request[] requestModified) {
        RequestModified = requestModified;
    }

    public AdjustmentVoucher[] getRequestedBy() {
        return RequestedBy;
    }

    public void setRequestedBy(AdjustmentVoucher[] requestedBy) {
        RequestedBy = requestedBy;
    }

    public AdjustmentVoucher[] getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(AdjustmentVoucher[] approvedBy) {
        ApprovedBy = approvedBy;
    }

    public DelegatedManager getDelegatedmanager() {
        return Delegatedmanager;
    }

    public void setDelegatedmanager(DelegatedManager delegatedmanager) {
        Delegatedmanager = delegatedmanager;
    }*/
}
