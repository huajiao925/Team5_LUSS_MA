package Model;

public class RequestDetails {

    public int RequestDetailID;
    public int RequestQty;
    public int ItemID;
    public int RequestID;
    public int FullfillQty;
    public int ReceivedQty;

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
