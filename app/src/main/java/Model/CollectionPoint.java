package Model;

import com.google.gson.annotations.SerializedName;

public class CollectionPoint {
    @SerializedName("collectionPointID")
    public int CollectionPointID;
    @SerializedName("pointName")
    public String PointName;
    @SerializedName("description")
    public String Description;
    @SerializedName("location")
    public String Location;

    public CollectionPoint(int collectionPointID, String pointName, String description, String location) {
        CollectionPointID = collectionPointID;
        PointName = pointName;
        Description = description;
        Location = location;
    }

    public CollectionPoint() {
    }

    public int getCollectionPointID() {
        return CollectionPointID;
    }

    public void setCollectionPointID(int collectionPointID) {
        CollectionPointID = collectionPointID;
    }

    public String getPointName() {
        return PointName;
    }

    public void setPointName(String pointName) {
        PointName = pointName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
