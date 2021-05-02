package org.zhezlov.documentarchive.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @Column(name = "author")
    @NotNull
    private Long authorId;

    @NotEmpty
    private String authorName;

    @Column(name = "created")
    @NotNull
    private LocalDateTime dateTimeCreated;

    @OneToMany(mappedBy = "document", fetch = FetchType.EAGER)
    private List<DocumentGrants> documentGrants;

    public Document(Long id, String name, String description, LocalDateTime dateTimeCreated, Long authorId, String authorName) {
        this(name, description, dateTimeCreated, authorId, authorName);
        this.id = id;
    }

    public Document(String name, String description, LocalDateTime dateTimeCreated, Long authorId, String authorName) {
        this.name = name;
        this.description = description;
        this.dateTimeCreated = dateTimeCreated;
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public Document() {

    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public boolean isNew(){
        return id == null;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }


    public List<DocumentGrants> getDocumentGrants() {
        return documentGrants;
    }

    public void setDocumentGrants(List<DocumentGrants> documentGrants) {
        this.documentGrants = documentGrants;
    }

    public void addDocumentGrants(DocumentGrants dg) {

        if (documentGrants == null)
            documentGrants = new ArrayList<>();
        documentGrants.add(dg);
    }
}
