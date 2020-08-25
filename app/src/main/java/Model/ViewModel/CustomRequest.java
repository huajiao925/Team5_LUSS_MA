package Model.ViewModel;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

import Model.RequestType;
import Model.Status;

public class CustomRequest {

    @SerializedName("requestID")
    public int requestID;
    @SerializedName("requestStatus")
    public Status.EOrderStatus requestStatus;
    @SerializedName("requestDate")
    public String requestDate;
    @SerializedName("requestBy")
    public int requestBy;
    @SerializedName("modifiedBy")
    public int modifiedBy;
    @SerializedName("comment")
    public String comment;
    @SerializedName("requestType")
    public Model.RequestType.ERequestType requestType;
    @SerializedName("parentRequestID")
    public int parentRequestID;
    @SerializedName("collectionTime")
    public String collectionTime;
    @SerializedName("retrievalID")
    public int retrievalID;
    @SerializedName("requestByName")
    public String requestByName;
    @SerializedName("modifiedByName")
    public String modifiedByName;

    @SerializedName("departmentName")
    public String departmentName;
    @SerializedName("departmentRep")
    public String departmentRep;
     @SerializedName("collectionPoint")
    public String collectionPoint;

    public CustomRequest() {
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public Status.EOrderStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Status.EOrderStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public int getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(int requestBy) {
        this.requestBy = requestBy;
    }

    public int getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RequestType.ERequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType.ERequestType requestType) {
        this.requestType = requestType;
    }

    public int getParentRequestID() {
        return parentRequestID;
    }

    public void setParentRequestID(int parentRequestID) {
        this.parentRequestID = parentRequestID;
    }

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    public int getRetrievalID() {
        return retrievalID;
    }

    public void setRetrievalID(int retrievalID) {
        this.retrievalID = retrievalID;
    }

    public String getRequestByName() {
        return requestByName;
    }

    public void setRequestByName(String requestByName) {
        this.requestByName = requestByName;
    }

    public String getModifiedByName() {
        return modifiedByName;
    }

    public void setModifiedByName(String modifiedByName) {
        this.modifiedByName = modifiedByName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentRep() {
        return departmentRep;
    }

    public void setDepartmentRep(String departmentRep) {
        this.departmentRep = departmentRep;
    }

    public String getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
    }
}

