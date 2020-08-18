package Model;

import java.time.LocalDateTime;

public class Retrieval {

    public int RetrievalID;
    public String Status;
    public LocalDateTime IssueDate;
    public int RequestID;
    public int ModifiedBy;

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

    public int getRequestID() {
        return RequestID;
    }

    public void setRequestID(int requestID) {
        RequestID = requestID;
    }

    public int getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(int modifiedBy) {
        ModifiedBy = modifiedBy;
    }
}
