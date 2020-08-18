package Model;

public class CollectionPoint {
    public int CollectionPointID;
    public String PointName;
    public String Description;
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
