package org.zhezlov.documentarchive.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "documents")
@Proxy(lazy=false) //добавил т.к. возникала ошибка could not initialize proxy [org.zhezlov.documentarchive.model.Document#77] - no Session
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "description")
    @NotEmpty
    private String description;

    @Column(name = "author")
    private Long authorId;

    @Column(name = "created")
    @NotNull
    private Timestamp dateTimeCreated;



    public Document(Long id, String name, String description, Timestamp dateTimeCreated, Long authorId) {
        this(name, description, dateTimeCreated, authorId);
        this.id = id;
    }

    public Document(String name, String description, Timestamp dateTimeCreated, Long authorId) {
        this.name = name;
        this.description = description;
        this.dateTimeCreated = dateTimeCreated;
        this.authorId = authorId;
    }

    public Document() {

    }

    public Timestamp getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Timestamp dateTimeCreated) {
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

}
