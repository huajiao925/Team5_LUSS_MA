package Model.ViewModel;

public class CustomRequestDetail {
    public int requestDetailID;
    public int requestQty;
    public int itemID;
    public int requestID;
    public int fulfillQty;
    //public int receivedQty;
    public String itemCode;
    public String itemName;
    public String uom;
    public int inStockQty;

    public int getRequestDetailID() {
        return requestDetailID;
    }

    public void setRequestDetailID(int requestDetailID) {
        this.requestDetailID = requestDetailID;
    }

    public int getRequestQty() {
        return requestQty;
    }

    public void setRequestQty(int requestQty) {
        this.requestQty = requestQty;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getFulfillQty() {
        return fulfillQty;
    }

    public void setFulfillQty(int fulfillQty) {
        this.fulfillQty = fulfillQty;
    }

//    public int getReceivedQty() {
//        return receivedQty;
//    }

//    public void setReceivedQty(int receivedQty) {
//        this.receivedQty = receivedQty;
//    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public int getInStockQty() {
        return inStockQty;
    }

    public void setInStockQty(int inStockQty) {
        this.inStockQty = inStockQty;
    }
}
