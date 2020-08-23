package Model;

import com.google.gson.annotations.SerializedName;

public class ItemPriceID {
    @SerializedName("itemPriceID")
    public int ItemPriceID;
    @SerializedName("itemID")
    public int ItemID;
    @SerializedName("supplierID")
    public int SupplierID;
    @SerializedName("price")
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
