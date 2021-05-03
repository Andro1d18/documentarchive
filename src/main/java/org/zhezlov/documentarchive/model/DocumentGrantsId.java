package org.zhezlov.documentarchive.model;

import java.io.Serializable;

public class DocumentGrantsId implements Serializable {

    private long userId;

    private long documentId;

    private long granted;


    public int hashCode() {
        return (int)(userId + documentId + granted);
    }

    public boolean equals(Object object) {
        if (object instanceof DocumentGrantsId) {
            DocumentGrantsId otherId = (DocumentGrantsId) object;
            return (otherId.userId == this.userId) && (otherId.documentId == this.documentId) && (otherId.granted == this.granted);
        }
        return false;
    }

}