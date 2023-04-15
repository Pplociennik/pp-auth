package com.github.pplociennik.auth.business.shared.system;

import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.entity.authentication.VerificationToken;
import com.github.pplociennik.auth.db.entity.authorization.Authority;
import com.github.pplociennik.commons.utility.identifier.ObjectTypeSpecifier;

/**
 * A class storing the definitions of the specific data for various objects' types.
 *
 * @author Created by: Pplociennik at 20.01.2023 15:55
 */
public class ObjectsSpecifierDefinition {

    public static ObjectTypeSpecifier< Account > accountTypeSpecifier() {
        return Account::getUsername;
    }

    public static ObjectTypeSpecifier< Authority > authorityTypeSpecifier() {
        return ( aAuthority ) -> aAuthority.getName() + "#" + aAuthority
                .getAuthoritiesOwner()
                .getUsername();
    }

    public static ObjectTypeSpecifier< VerificationToken > verificationTokenTypeSpecifier() {
        return ( aVerificationToken ) -> "#" + aVerificationToken
                .getType()
                .name() + "#" + aVerificationToken
                .getOwner()
                .getUsername();
    }
}
