package com.lms.bs.rest.model.json;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthorJson implements Serializable, Comparable<AuthorJson> {
    private static final long serialVersionUID = -302934094232424L;

    private String authorId;
    private String authorName;
    private String dateOfBirth;
    private String dateOfDeath;
    private String bio;
    private String wikiUrl;
    private String imageUrl;

    @Override
    public int compareTo(AuthorJson o) {
        if(authorName == o.authorName) {
            return 0;
        }
        if(o.authorName == null) {
            return 1;
        }
        return authorName.compareTo(o.authorName);
    }
}
