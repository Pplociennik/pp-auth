package com.github.pplociennik.auth.business.authentication.infrastructure.output;

import com.github.pplociennik.auth.business.authentication.domain.map.VerificationTokenMapper;
import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import com.github.pplociennik.auth.business.authentication.ports.VerificationTokenRepository;
import com.github.pplociennik.auth.db.repository.authentication.AccountDao;
import com.github.pplociennik.auth.db.repository.authentication.VerificationTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import static com.github.pplociennik.auth.business.authentication.domain.map.VerificationTokenMapper.mapToDomain;
import static com.github.pplociennik.auth.business.authentication.domain.map.VerificationTokenMapper.mapToEntity;
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
