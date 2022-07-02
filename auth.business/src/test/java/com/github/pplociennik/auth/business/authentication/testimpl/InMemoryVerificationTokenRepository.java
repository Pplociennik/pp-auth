package com.github.pplociennik.auth.business.authentication.testimpl;

import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import com.github.pplociennik.auth.business.authentication.ports.VerificationTokenRepository;

/**
 * In memory implementation of the {@link VerificationTokenRepository}.
 *
 * @author Created by: Pplociennik at 01.07.2022 23:21
 */
public class InMemoryVerificationTokenRepository implements VerificationTokenRepository {

    @Override
    public VerificationTokenDO findByToken( String aToken ) {
        return null;
    }

    @Override
    public VerificationTokenDO save( VerificationTokenDO aVerificationToken ) {
        return null;
    }
}
