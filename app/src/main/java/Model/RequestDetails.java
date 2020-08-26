package Model;

import com.google.gson.annotations.SerializedName;

public class RequestDetails {
    @SerializedName("requestDetailID")
    public int RequestDetailID;
    @SerializedName("requestQty")
    public int RequestQty;
    @SerializedName("itemID")
    public int ItemID;
    @SerializedName("requestID")
    public int RequestID;
    @SerializedName("fullfillQty")
    public int FullfillQty;
    @SerializedName("receivedQty")
    public int ReceivedQty;
    @SerializedName("isActive")
    public boolean isActive;

    @SerializedName("request")
    public String request;

    @SerializedName("Item")
    public Item Item;

    public int getRequestQty() {
        return RequestQty;
    }

    public void setRequestQty(int requestQty) {
        RequestQty = requestQty;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getRequestID() {
        return RequestID;
    }

    public void setRequestID(int requestID) {
        RequestID = requestID;
    }

    public int getFullfillQty() {
        return FullfillQty;
    }

    public void setFullfillQty(int fullfillQty) {
        FullfillQty = fullfillQty;
    }

    public int getReceivedQty() {
        return ReceivedQty;
    }

    public void setReceivedQty(int receivedQty) {
        ReceivedQty = receivedQty;
    }
}
