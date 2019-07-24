package com.lms.bs.rest.transformer;

import com.lms.bs.rest.model.entity.Language;

public final class LanguageTransformer {
    public static String getClientLanguage(Language language) {
        return LanguageDBCode.getClientLanguage(language);
    }
    public static Language getLanguageFromClientLanguage(String clientLanguage) {
        return LanguageDBCode.getLanguageObject(clientLanguage);
    }

    private enum LanguageDBCode {
        ENGLISH("EN", "ENGLISH"),
        BENGALI("BN", "BENGALI"),
        HINDI("HI", "HINDI");

        String languageCode;
        String clientLanguage;

        LanguageDBCode(String languageCode, String clientLanguage) {
            this.languageCode = languageCode;
            this.clientLanguage = clientLanguage;
        }

        static String getClientLanguage(Language language) {
            String clientLanguage = null;
            for(LanguageDBCode languageDBCode : LanguageDBCode.values()) {
                if(languageDBCode.languageCode.equalsIgnoreCase(language.getLangCode())) {
                    clientLanguage = languageDBCode.clientLanguage;
                    break;
                }

            }
            return clientLanguage;
        }
        static Language getLanguageObject(String clientLanguage) {
            Language language = new Language();
            for(LanguageDBCode languageDBCode : LanguageDBCode.values()) {
                if(languageDBCode.clientLanguage.equalsIgnoreCase(clientLanguage)) {
                    language.setLangCode(languageDBCode.languageCode);
                    break;
                }

            }
            return language;
        }
    }
}
