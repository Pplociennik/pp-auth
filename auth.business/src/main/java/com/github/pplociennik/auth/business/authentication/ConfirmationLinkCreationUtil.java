package com.github.pplociennik.auth.business.authentication;

import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import org.springframework.lang.NonNull;

import static com.github.pplociennik.commons.utility.CustomObjects.requireNonEmpty;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * A util for account confirmation link creation.
 *
 * @author Created by: Pplociennik at 15.02.2024 12:34
 */
class ConfirmationLinkCreationUtil {

    /**
     * An identifier of the verification token in the result url.
     */
    private static final String TOKEN_PARAMETER_ID = "aToken";
    /**
     * The result url pattern: CLIENT_URL/?TOKEN_IDENTIFIER=token_value
     */
    private static final String CONFIRMATION_LINK_PATTERN = "%s/?" + TOKEN_PARAMETER_ID + "=%s";

    /**
     * Private constructor to hide the default one.
     */
    private ConfirmationLinkCreationUtil() {
        throw new AssertionError( "Class not instantiable." );
    }

    /**
     * Returns the account confirmation url.
     *
     * @param aClientUrl
     *         an url to the client application.
     * @param aTokenValueDO
     *         a verification token to be included in the result url.
     * @return the created account confirmation link.
     */
    static String createConfirmationLink( @NonNull String aClientUrl, @NonNull VerificationTokenDO aTokenValueDO ) {
        requireNonNull( aTokenValueDO );
        requireNonEmpty( aClientUrl );

        var tokenValue = aTokenValueDO.getToken();
        return format( CONFIRMATION_LINK_PATTERN, aClientUrl, tokenValue );
    }
}
