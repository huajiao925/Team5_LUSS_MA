package Model;

public class ItemPrice {
    public int ItemPriceID;
    public int ItemID;
    public int SupplierID;
    public int Price;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(int supplierID) {
        SupplierID = supplierID;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
