package Model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class Retrieval {

    @SerializedName("retrievalID")
    public int RetrievalID;
    @SerializedName("status")
    public String Status;
    @SerializedName("issueDate")
    public LocalDateTime IssueDate;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public LocalDateTime getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        IssueDate = issueDate;
    }

}
