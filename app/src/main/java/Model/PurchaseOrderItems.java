package Model;

public class PurchaseOrderItems {

    public int POItemID;
    public int POID;
    public int ItemID;
    public int OrderQty;
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
