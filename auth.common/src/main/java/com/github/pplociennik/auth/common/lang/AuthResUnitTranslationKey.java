package com.github.pplociennik.auth.common.lang;

import com.github.pplociennik.commons.lang.TranslationKey;

/**
 * An enum holding keys for identifying units' translated names.
 *
 * @author Created by: Pplociennik at 03.07.2022 05:41
 */
public enum AuthResUnitTranslationKey implements TranslationKey {

    /**
     * Unit: Minutes.
     */
    MINUTES,

    /**
     * Unit: Hours.
     */
    HOURS,

    /**
     * Unit: Days.
     */
    DAYS;


    private static final String UNITS_TRANSLATIONS_BASENAME_PROPERTY = "lang/AuthResUnitNames";


    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getTranslationsSourcePropertyName() {
        return UNITS_TRANSLATIONS_BASENAME_PROPERTY;
    }
}
