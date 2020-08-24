package Model;

import com.google.gson.annotations.SerializedName;

public class Supplier {
    @SerializedName("supplierID")
    public int SupplierID;
    @SerializedName("address")
    public String Address;
    @SerializedName("supplierName")
    public String SupplierName;
    @SerializedName("contactNo")
    public String ContactNo;
    @SerializedName("email")
    public String email;
    @SerializedName("description")
    public String Description;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
