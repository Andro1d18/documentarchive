package org.zhezlov.documentarchive.model;

import java.io.Serializable;

public class DocumentGrantsId implements Serializable {

    private long userId;

    private long documentId;


    public int hashCode() {
        return (int)(userId + documentId);
    }

    public boolean equals(Object object) {
        if (object instanceof DocumentGrantsId) {
            DocumentGrantsId otherId = (DocumentGrantsId) object;
            return (otherId.userId == this.userId) && (otherId.documentId == this.documentId);
        }
        return false;
    }

}