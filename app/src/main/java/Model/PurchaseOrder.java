package Model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class PurchaseOrder {

    @SerializedName("poid")
    public int POID;
    @SerializedName("createdOn")
    public LocalDateTime CreatedOn;
    @SerializedName("expectedDate")
    public LocalDateTime ExpectedDate;
    @SerializedName("purchasedBy")
    public int PurchasedBy;
    @SerializedName("supplierID")
    public int SupplierID;
    @SerializedName("status")
    public PurchaseOrderStatus.POStatus Status;
    @SerializedName("receivedDate")
    public LocalDateTime ReceivedDate;
    @SerializedName("poNo")
    public String PONo;

    public LocalDateTime getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        CreatedOn = createdOn;
    }

    public LocalDateTime getExpectedDate() {
        return ExpectedDate;
    }

    public void setExpectedDate(LocalDateTime expectedDate) {
        ExpectedDate = expectedDate;
    }

    public int getPurchasedBy() {
        return PurchasedBy;
    }

    public void setPurchasedBy(int purchasedBy) {
        PurchasedBy = purchasedBy;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(int supplierID) {
        SupplierID = supplierID;
    }

    public PurchaseOrderStatus.POStatus getStatus() {
        return Status;
    }

    public void setStatus(PurchaseOrderStatus.POStatus status) {
        Status = status;
    }

    public LocalDateTime getReceivedDate() {
        return ReceivedDate;
    }

    public void setReceivedDate(LocalDateTime receivedDate) {
        ReceivedDate = receivedDate;
    }

    public String getPONo() {
        return PONo;
    }

    public void setPONo(String PONo) {
        this.PONo = PONo;
    }
}
