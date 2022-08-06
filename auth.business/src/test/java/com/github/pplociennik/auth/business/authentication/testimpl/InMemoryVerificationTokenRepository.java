package com.github.pplociennik.auth.business.authentication.testimpl;

import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import com.github.pplociennik.auth.business.authentication.ports.VerificationTokenRepository;

import java.util.LinkedList;
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
        database = new LinkedList<>();
    }

    public void setDatabase( List< VerificationTokenDO > aDatabase ) {
        database = aDatabase;
    }

    public void add( VerificationTokenDO aTokenDO ) {
        database.add( aTokenDO );
    }

    @Override
    public VerificationTokenDO findByToken( String aToken ) {
        return database.stream()
                .filter( token -> token.getToken().equals( aToken ) )
                .findAny()
                .orElse( null );
    }

    @Override
    public VerificationTokenDO save( VerificationTokenDO aVerificationToken ) {
        database.add( aVerificationToken );
        return aVerificationToken;
    }

    @Override
    public VerificationTokenDO update( VerificationTokenDO aVerificationTokenDO ) {
        return null;
    }
}
