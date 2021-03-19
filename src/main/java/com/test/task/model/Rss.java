package com.test.task.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "rss")
public class Rss {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description", length = 4000)
    private String description;
    @Column(name = "publication_date")
    private Date publicationDate;
    @Column(name = "link")
    private String link;
}
