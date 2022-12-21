package com.github.pplociennik.auth.common.lang;

import com.github.pplociennik.commons.lang.TranslationKey;

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
     * The link expires in {0} {1}.
     */
    EMAIL_ACCOUNT_CONFIRMATION_DISCLAIMER,

    /**
     * Welcome!
     */
    WELCOME_EMAIL_SUBJECT,

    /**
     * Welcome {0}!
     */
    WELCOME_EMAIL_WELCOME,

    /**
     * Thank you for registering! We hope you will spend some nice time here.
     */
    WELCOME_EMAIL_WELCOME_TEXT,

    /**
     * Best wishes,
     */
    WELCOME_EMAIL_REGARDS;

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
