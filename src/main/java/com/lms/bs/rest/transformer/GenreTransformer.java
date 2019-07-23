package com.lms.bs.rest.transformer;

import com.lms.bs.rest.model.entity.Genre;

public final class GenreTransformer {
    public static Genre getGenre(String description) {
        return GenreDBMapping.getByDescription(description);
    }
    public static String getGenreDescription(Genre genre) {
        return GenreDBMapping.getDescriptionFromGenre(genre);
    }

    private enum GenreDBMapping {
        FANTASY("FNTY", "FANTASY"),
        SCIENCE_FICTION("SCIFI", "SCIENCE_FICTION"),
        WESTERN("WSTR", "WESTERN"),
        ROMANCE("ROMN", "ROMANCE"),
        THRILLER("THRL", "THRILLER"),
        MYSTERY("MYST", "MYSTERY"),
        DETECTIVE_STORY("DETC", "DETECTIVE_STORY");

        String clientGenre;
        String dbCode;

        GenreDBMapping(String dbCode, String clientGenre) {
            this.dbCode = dbCode;
            this.clientGenre = clientGenre;
        }

        static Genre getByDescription(String description) {
            Genre genre = new Genre();
            for(GenreDBMapping genreDBMapping : GenreDBMapping.values()) {
                if(description.equalsIgnoreCase(genreDBMapping.clientGenre)) {
                    genre.setGenreCode(genreDBMapping.dbCode);
                    break;
                }
            }
            return genre;
        }
        static String getDescriptionFromGenre(Genre genre) {
            String description = null;
            for(GenreDBMapping genreDBMapping : GenreDBMapping.values()) {
                if(genre.getGenreCode().equalsIgnoreCase(genreDBMapping.clientGenre)) {
                    description = genreDBMapping.clientGenre;
                }
            }
            return description;
        }
    }
}