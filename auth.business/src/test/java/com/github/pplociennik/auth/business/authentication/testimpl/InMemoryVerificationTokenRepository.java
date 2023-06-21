package com.github.pplociennik.auth.business.authentication.testimpl;

import auth.AuthVerificationTokenType;
import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import com.github.pplociennik.auth.business.authentication.ports.VerificationTokenRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        return database
                .stream()
                .filter( token -> token
                        .getToken()
                        .equals( aToken ) )
                .findAny()
                .orElse( null );
    }

    /**
     * Returns all the tokens of the specified type currently active for the specified user.
     *
     * @param aAccountId
     *         the account id.
     * @param aTokenType
     *         the type of the tokens to be found.
     *
     * @return a list of the tokens.
     */
    @Override
    public List< VerificationTokenDO > findAllActiveByAccountIdAndType( Long aAccountId, AuthVerificationTokenType aTokenType ) {
        return database.stream()
                .filter( token -> token.getId() == aAccountId )
                .filter( token -> token.getType() == aTokenType )
                .collect( Collectors.toList() );
    }

    @Override
    public VerificationTokenDO persist( VerificationTokenDO aVerificationToken ) {
        database.add( aVerificationToken );
        return aVerificationToken;
    }

    @Override
    public VerificationTokenDO update( VerificationTokenDO aVerificationTokenDO ) {
        return null;
    }
}
