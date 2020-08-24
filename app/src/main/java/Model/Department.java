package Model;

import com.google.gson.annotations.SerializedName;

public class Department {

    @SerializedName("departmentID")
    public int DepartmentID;
    @SerializedName("departmentName")
    public String DepartmentName;
    @SerializedName("phoneNo")
    public String PhoneNo;
    @SerializedName("fax")
    public String Fax;
    @SerializedName("collectionPointID")
    public int CollectionPointID;
    @SerializedName("departmentCode")
    public String DepartmentCode;

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public int getCollectionPointID() {
        return CollectionPointID;
    }

    public void setCollectionPointID(int collectionPointID) {
        CollectionPointID = collectionPointID;
    }

    public String getDepartmentCode() {
        return DepartmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        DepartmentCode = departmentCode;
    }
}
