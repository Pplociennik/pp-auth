package com.github.pplociennik.auth.business.authorization.infrastructure;

import com.github.pplociennik.auth.business.authorization.domain.map.AuthorityMapper;
import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDO;
import com.github.pplociennik.auth.business.authorization.ports.AuthorityRepository;
import com.github.pplociennik.auth.db.repository.authentication.AuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.util.Set;

import static com.github.pplociennik.auth.business.authorization.domain.map.AuthorityMapper.mapToDomain;
import static com.github.pplociennik.auth.business.authorization.domain.map.AuthorityMapper.mapToEntity;
import static com.github.pplociennik.commons.utility.CustomObjects.requireNonEmpty;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * {@inheritDoc}
 *
 * @author Created by: Pplociennik at 01.07.2022 15:20
 */
class AuthorityRepositoryImpl implements AuthorityRepository {

    private final AuthorityDao authorityDao;

    @Autowired
    public AuthorityRepositoryImpl( @NonNull AuthorityDao aAuthorityDao ) {
        authorityDao = requireNonNull( aAuthorityDao );
    }

    @Override
    public Set< AuthorityDO > findByEmail( @NonNull String aEmailAddress ) {
        requireNonEmpty( aEmailAddress );
        return authorityDao
                .findAllByAuthoritiesOwner_EmailAddress( aEmailAddress )
                .stream()
                .map( AuthorityMapper::mapToDomain )
                .collect( toUnmodifiableSet() );
    }

    @Override
    public Set< AuthorityDO > findByUsername( @NonNull String aUsername ) {
        requireNonNull( aUsername );
        return authorityDao
                .findAllByAuthoritiesOwner_Username( aUsername )
                .stream()
                .map( AuthorityMapper::mapToDomain )
                .collect( toUnmodifiableSet() );
    }

    @Override
    public AuthorityDO persist( @NonNull AuthorityDO aAuthority ) {
        requireNonNull( aAuthority );
        var authorityEntity = mapToEntity( aAuthority );
        var persistedObject = authorityDao.saveAndFlush( authorityEntity );

        return mapToDomain( persistedObject );
    }
}
