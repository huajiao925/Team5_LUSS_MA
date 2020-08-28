package Model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class CustomAdjustmentVoucher {
    @SerializedName("adjustmentID")
    public int AdjustmentID;
    @SerializedName("adjustQty")
    public int AdjustQty;
    @SerializedName("adjustType")
    public String AdjustType;
    @SerializedName("status")
    public AdjustmentVoucherStatus.AdjustmentStatus Status;
    @SerializedName("totalCost")
    public int TotalCost;
    @SerializedName("issuedDate")
    public String IssuedDate;
    @SerializedName("voucherNo")
    public String VoucherNo;
    @SerializedName("comment")
    public String Comment;
    @SerializedName("reason")
    public String Reason;
    @SerializedName("itemID")
    public int ItemID;
    @SerializedName("approvedByID")
    public int ApprovedByID;
    @SerializedName("requestByID")
    public int RequestByID;
    @SerializedName("requestedByUser")
    public String RequestedByUser;
    @SerializedName("approvedByUser")
    public String ApprovedByUser;
    @SerializedName("itemCode")
    public String ItemCode;
    @SerializedName("categoryName")
    public String CategoryName;
    @SerializedName("itemName")
    public String ItemName;
    @SerializedName("uom")
    public String UOM;
    @SerializedName("itemPrice")
    public int ItemPrice;

    public int getAdjustmentID() {
        return AdjustmentID;
    }

    public void setAdjustmentID(int adjustmentID) {
        AdjustmentID = adjustmentID;
    }

    public int getAdjustQty() {
        return AdjustQty;
    }

    public void setAdjustQty(int adjustQty) {
        AdjustQty = adjustQty;
    }

    public String getAdjustType() {
        return AdjustType;
    }

    public void setAdjustType(String adjustType) {
        AdjustType = adjustType;
    }

    public AdjustmentVoucherStatus.AdjustmentStatus getStatus() {
        return Status;
    }

    public void setStatus(AdjustmentVoucherStatus.AdjustmentStatus status) {
        Status = status;
    }

    public int getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(int totalCost) {
        TotalCost = totalCost;
    }

    public String getIssuedDate() {
        return IssuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        IssuedDate = issuedDate;
    }

    public String getVoucherNo() {
        return VoucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        VoucherNo = voucherNo;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getApprovedByID() {
        return ApprovedByID;
    }

    public void setApprovedByID(int approvedByID) {
        ApprovedByID = approvedByID;
    }

    public int getRequestByID() {
        return RequestByID;
    }

    public void setRequestByID(int requestByID) {
        RequestByID = requestByID;
    }

    public String getRequestedByUser() {
        return RequestedByUser;
    }

    public void setRequestedByUser(String requestedByUser) {
        RequestedByUser = requestedByUser;
    }

    public String getApprovedByUser() {
        return ApprovedByUser;
    }

    public void setApprovedByUser(String approvedByUser) {
        ApprovedByUser = approvedByUser;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public int getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(int itemPrice) {
        ItemPrice = itemPrice;
    }
}
