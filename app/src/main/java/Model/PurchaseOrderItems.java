package Model;

import com.google.gson.annotations.SerializedName;

public class PurchaseOrderItems {

    @SerializedName("poItemID")
    public int POItemID;
    @SerializedName("poid")
    public int POID;
    @SerializedName("itemID")
    public int ItemID;
    @SerializedName("orderQty")
    public int OrderQty;
    @SerializedName("receivedQty")
    public int ReceivedQty;

    public int getPOID() {
        return POID;
    }

    public void setPOID(int POID) {
        this.POID = POID;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(int orderQty) {
        OrderQty = orderQty;
    }

    public int getReceivedQty() {
        return ReceivedQty;
    }

    public void setReceivedQty(int receivedQty) {
        ReceivedQty = receivedQty;
    }
}
