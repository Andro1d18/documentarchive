package org.zhezlov.documentarchive.to;

import java.time.LocalDateTime;


public class DocumentTo {

    private final Long id;
    private final String name;
    private final String description;
    private final Long authorId;
    private final String authorName;
    private final LocalDateTime dateTimeCreated;
    private final boolean canPreview;
    private final boolean canSharing;
    private final boolean canUpdate;
    private final boolean canDelete;


    public DocumentTo(Long id, String name, String description, Long authorId, String authorName, LocalDateTime dateTimeCreated, boolean canPreview, boolean canSharing, boolean canUpdate, boolean canDelete) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.authorId = authorId;
        this.authorName = authorName;
        this.dateTimeCreated = dateTimeCreated;
        this.canPreview = canPreview;
        this.canSharing = canSharing;
        this.canUpdate = canUpdate;
        this.canDelete = canDelete;

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

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public boolean isCanPreview() {
        return canPreview;
    }
    public boolean isCanSharing() {
        return canSharing;
    }

    public boolean isCanUpdate() {
        return canUpdate;
    }

    public boolean isCanDelete() {
        return canDelete;
    }
    public String getAuthorName() {
        return authorName;
    }

}
