package Model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class AdjustmentVoucher {
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
    public LocalDateTime IssuedDate;
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

    public LocalDateTime getIssuedDate() {
        return IssuedDate;
    }

    public void setIssuedDate(LocalDateTime issuedDate) {
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
}
