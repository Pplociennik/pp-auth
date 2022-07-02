package com.github.pplociennik.auth.common.lang;

import com.github.pplociennik.util.lang.TranslationKey;

/**
 * An enum holding keys for identifying emails' translation messages.
 *
 * @author Created by: Pplociennik at 07.05.2022 23:13
 */
public enum AuthResEmailMsgTranslationKey implements TranslationKey {

    // Confirmation request email

    /**
     * Confirm your account!
     */
    EMAIL_ACCOUNT_CONFIRMATION_SUBJECT,

    /**
     * Welcome! Please confirm your email address. Here's your confirmation link:
     */
    EMAIL_ACCOUNT_CONFIRMATION_MESSAGE,

    /**
     * The link expires in {0} minutes.
     */
    EMAIL_ACCOUNT_CONFIRMATION_DISCLAIMER;

    private static final String EMAILS_TRANSLATIONS_BASENAME_PROPERTY = "lang/AuthResEmailMsg";


    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getTranslationsSourcePropertyName() {
        return EMAILS_TRANSLATIONS_BASENAME_PROPERTY;
    }
}
