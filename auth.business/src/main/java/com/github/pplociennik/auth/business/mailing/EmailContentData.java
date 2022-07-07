package com.github.pplociennik.auth.business.mailing;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.thymeleaf.context.Context;

import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * An immutable data holder for context data necessary for creating email messages.
 *
 * @author Created by: Pplociennik at 30.06.2022 21:02
 */
class EmailContentData {

    private final Context context;
    private final String templateFile;

    private final Locale locale;

    private EmailContentData( @NonNull Context aContext, @NonNull String aTemplateFile, @NonNull Locale aLocale ) {
        context = requireNonNull( aContext );
        templateFile = requireNonNull( aTemplateFile );
        locale = requireNonNull( aLocale );
    }

    /**
     * A factory method returning a new {@link EmailContentData} typed object.
     *
     * @param aContext
     *         a context of a message.
     * @return a new {@link EmailContentData} object containing information necessary to create a new email message to be sent.
     */
    static EmailContentData of( @NonNull Context aContext, @NonNull Locale aLocale ) {
        return new EmailContentData( aContext, StringUtils.EMPTY, aLocale );
    }

    /**
     * A factory method returning a new {@link EmailContentData} typed object.
     *
     * @param aContext
     *         a context of a message.
     * @param aTemplateFile
     *         a name of the template file.
     * @return a new {@link EmailContentData} object containing information necessary to create a new email message to be sent.
     */
    static EmailContentData of( @NonNull Context aContext, @NonNull String aTemplateFile, @NonNull Locale aLocale ) {
        return new EmailContentData( aContext, aTemplateFile, aLocale );
    }

    Context getContext() {
        return context;
    }

    String getTemplateFile() {
        return templateFile;
    }

    Locale getLocale() {
        return locale;
    }
}
