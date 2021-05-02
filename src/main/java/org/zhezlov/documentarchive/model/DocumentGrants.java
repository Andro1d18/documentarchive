package org.zhezlov.documentarchive.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(DocumentGrantsId.class)
@Table(name = "DOCUMENTS_GRANTS")
public class DocumentGrants implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;


    @Id
    @Column(name = "DOCUMENT_ID")
    private Long documentId;

    @Id
    @Column(name = "USER_ID")
    private Long userId;

    //1 - is Author
    //5 - canDelete
    //10 - canUpdate
    //15 - canView
    private Long granted;

    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID", insertable = false, updatable = false, referencedColumnName = "ID")
    private Document document;

    public DocumentGrants() {
    }

    public DocumentGrants(Long documentId, Long userId, Long granted, Document document) {
        this.documentId = documentId;
        this.userId = userId;
        this.granted = granted;
        this.document = document;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long idDocument) {
        this.documentId = idDocument;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long idUser) {
        this.userId = idUser;
    }

    public Long getGranted() {
        return granted;
    }

    public void setGranted(Long granted) {
        this.granted = granted;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
