package com.github.pplociennik.auth.business.authentication.testimpl;

import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import com.github.pplociennik.auth.business.authentication.ports.VerificationTokenRepository;

import java.util.List;

/**
 * In memory implementation of the {@link VerificationTokenRepository}.
 *
 * @author Created by: Pplociennik at 01.07.2022 23:21
 */
public class InMemoryVerificationTokenRepository implements VerificationTokenRepository {

    private List< VerificationTokenDO > database;

    public InMemoryVerificationTokenRepository( List< VerificationTokenDO > aDatabase ) {
        database = aDatabase;
    }

    public InMemoryVerificationTokenRepository() {
    }

    public void setDatabase( List< VerificationTokenDO > aDatabase ) {
        database = aDatabase;
    }

    @Override
    public VerificationTokenDO findByToken( String aToken ) {
        return null;
    }

    @Override
    public VerificationTokenDO save( VerificationTokenDO aVerificationToken ) {
        return null;
    }
}
