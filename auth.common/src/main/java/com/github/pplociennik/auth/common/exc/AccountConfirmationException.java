package com.github.pplociennik.auth.common.exc;

import com.github.pplociennik.util.exc.BaseRuntimeException;
import com.github.pplociennik.util.lang.TranslationKey;

import java.io.Serializable;

/**
 * An exception being thrown during the account confirmation process.
 *
 * @author Created by: Pplociennik at 01.07.2022 22:22
 */
public class AccountConfirmationException extends BaseRuntimeException {

    public AccountConfirmationException() {
        super( "An exception during the account confirmation process." );
    }

    public AccountConfirmationException( String message ) {
        super( message );
    }

    public AccountConfirmationException( TranslationKey aTranslationKey, Serializable... aParams ) {
        super( aTranslationKey, aParams );
    }
}
