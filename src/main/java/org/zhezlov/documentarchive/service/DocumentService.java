package org.zhezlov.documentarchive.service;

import org.zhezlov.documentarchive.model.Document;

import java.util.List;

public interface DocumentService {

    List<Document> getAll();

    void create(Document document);
}
