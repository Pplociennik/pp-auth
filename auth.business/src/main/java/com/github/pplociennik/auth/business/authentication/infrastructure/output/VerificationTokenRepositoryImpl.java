package com.github.pplociennik.auth.business.authentication.infrastructure.output;

import auth.AuthVerificationTokenType;
import com.github.pplociennik.auth.business.authentication.domain.map.VerificationTokenMapper;
import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import com.github.pplociennik.auth.business.authentication.ports.VerificationTokenRepository;
import com.github.pplociennik.auth.db.repository.authentication.AccountDao;
import com.github.pplociennik.auth.db.repository.authentication.VerificationTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.pplociennik.auth.business.authentication.domain.map.VerificationTokenMapper.mapToDomain;
import static com.github.pplociennik.auth.business.authentication.domain.map.VerificationTokenMapper.mapToEntity;
import static com.github.pplociennik.commons.utility.CustomObjects.validateNonNull;
import static java.util.Objects.requireNonNull;

/**
 * {@inheritDoc}
 *
 * @author Created by: Pplociennik at 01.07.2022 13:55
 */
class VerificationTokenRepositoryImpl implements VerificationTokenRepository {

    private final VerificationTokenDao verificationTokenDao;
    private final AccountDao accountDao;

    @Autowired
    VerificationTokenRepositoryImpl(
            @NonNull VerificationTokenDao aVerificationTokenDao, @NonNull AccountDao aAccountDao ) {
        verificationTokenDao = requireNonNull( aVerificationTokenDao );
        accountDao = requireNonNull( aAccountDao );
    }

    @Override
    public VerificationTokenDO findByToken( @NonNull String aToken ) {
        requireNonNull( aToken );

        var verificationToken = verificationTokenDao.findByToken( aToken );
        return verificationToken
                .map( VerificationTokenMapper::mapToDomain )
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
    public List< VerificationTokenDO > findAllActiveByAccountIdAndType( @NonNull Long aAccountId, @NonNull AuthVerificationTokenType aTokenType ) {
        validateNonNull( aAccountId, aTokenType );
        var tokensOwner = accountDao.findAccountById( aAccountId );
        return tokensOwner.stream()
                .map( owner -> verificationTokenDao.findAllByTypeAndOwnerAndActive( aTokenType, owner, Boolean.TRUE ) )
                .collect( Collectors.toList() )
                .stream().flatMap( List::stream )
                .map( VerificationTokenMapper::mapToDomain )
                .collect( Collectors.toList() );
    }

    @Override
    public VerificationTokenDO persist( @NonNull VerificationTokenDO aVerificationToken ) {
        requireNonNull( aVerificationToken );

        var accountDO = aVerificationToken.getOwner();
        var owner = accountDao
                .getAccountByEmailAddress( accountDO.getEmailAddress() )
                .orElseThrow( () -> new IllegalArgumentException( "User not found!" ) );

        var verificationTokenEntity = mapToEntity( aVerificationToken, owner );

        return mapToDomain( verificationTokenDao.saveAndFlush( verificationTokenEntity ) );
    }

    @Override
    public VerificationTokenDO update( @NonNull VerificationTokenDO aVerificationTokenDO ) {
        requireNonNull( aVerificationTokenDO );

        var token = verificationTokenDao.findByToken( aVerificationTokenDO.getToken() );
        var toUpdate = token.orElseThrow();

        toUpdate.setActive( aVerificationTokenDO.isActive() );
        toUpdate.setType( aVerificationTokenDO.getType() );

        return mapToDomain( verificationTokenDao.save( toUpdate ) );
    }
}
