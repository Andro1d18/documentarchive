package org.zhezlov.documentarchive.to;

import java.sql.Timestamp;


public class DocumentTo {

    private final Long id;
    private final String name;
    private final String description;
    private final Long authorId;
    private final Timestamp dateTimeCreated;
    private final boolean canPreview;


    public DocumentTo(Long id, String name, String description, Long authorId, Timestamp dateTimeCreated, boolean canPreview) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.authorId = authorId;
        this.dateTimeCreated = dateTimeCreated;
        this.canPreview = canPreview;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Timestamp getDateTimeCreated() {
        return dateTimeCreated;
    }

    public boolean isCanPreview() {
        return canPreview;
    }
}
