package com.lms.bs.rest.transformer;

import com.lms.bs.rest.model.entity.Author;
import com.lms.bs.rest.model.json.AuthorJson;

public final class AuthorTransformer {

    public static Author transformAuthorJsonToAuthor(AuthorJson authorJson) {
        Author author = new Author();
        author.setAuthorName(authorJson.getAuthorName());
        author.setBio(authorJson.getBio());
        author.setDateOfBirth(authorJson.getDateOfBirth());
        author.setDateOfDeath(authorJson.getDateOfDeath());
        author.setImageUrl(authorJson.getImageUrl());
        author.setWikiUrl(authorJson.getWikiUrl());

        return author;
    }

    public static AuthorJson transformAuthorToAuthorJson(Author author) {
        AuthorJson authorJson = new AuthorJson();
        authorJson.setAuthorId(author.getAuthorId());
        authorJson.setAuthorName(author.getAuthorName());
        authorJson.setBio(author.getBio());
        authorJson.setDateOfBirth(author.getDateOfBirth());
        authorJson.setDateOfDeath(author.getDateOfDeath());
        authorJson.setImageUrl(author.getImageUrl());
        authorJson.setWikiUrl(author.getWikiUrl());

        return authorJson;
    }
}
