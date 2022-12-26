package com.github.pplociennik.auth.business.authentication.testimpl;

import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.repository.authentication.AccountDao;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * In memory implementation for the {@link AccountDao}.
 *
 * @author Created by: Pplociennik at 20.07.2022 22:16
 */
public class InMemoryAccountDao implements AccountDao {

    private List< Account > database;

    public InMemoryAccountDao( List< Account > aDatabase ) {
        database = aDatabase;
    }

    public void setDatabase( List< Account > aDatabase ) {
        database = aDatabase;
    }

    @Override
    public Optional< Account > getAccountByEmailAddress( String aEmailAddress ) {
        return database
                .stream()
                .filter( account -> account
                        .getEmailAddress()
                        .equals( aEmailAddress ) )
                .findAny();
    }

    @Override
    public Optional< Account > getAccountByUniqueObjectIdentifier( String aUniqueIdentifier ) {
        return database
                .stream()
                .filter( account -> account
                        .getUniqueObjectIdentifier()
                        .equals( aUniqueIdentifier ) )
                .findAny();
    }

    @Override
    public Optional< Account > findAccountByUsername( String aUsername ) {
        return database
                .stream()
                .filter( account -> account
                        .getUsername()
                        .equals( aUsername ) )
                .findAny();
    }

    @Override
    public Optional< Account > findAccountByEmailAddress( String aEmailAddress ) {
        return database
                .stream()
                .filter( account -> account
                        .getEmailAddress()
                        .equals( aEmailAddress ) )
                .findAny();
    }

    @Override
    public Optional< Account > findAccountById( long aId ) {
        return database
                .stream()
                .filter( account -> account.getId() == aId )
                .findAny();
    }

    @Override
    public boolean existsAccountByUsername( String aUsername ) {
        return database
                .stream()
                .map( Account::getUsername )
                .anyMatch( aUsername::equals );
    }

    @Override
    public boolean existsAccountByEmailAddress( String aEmail ) {
        return database
                .stream()
                .map( Account::getEmailAddress )
                .anyMatch( aEmail::equals );
    }

    @Override
    public List< Account > findAll() {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public List< Account > findAll( Sort sort ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public Page< Account > findAll( Pageable pageable ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public List< Account > findAllById( Iterable< Long > ids ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public long count() {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public void deleteById( Long aLong ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );

    }

    @Override
    public void delete( Account entity ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );

    }

    @Override
    public void deleteAllById( Iterable< ? extends Long > ids ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );

    }

    @Override
    public void deleteAll( Iterable< ? extends Account > entities ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );

    }

    @Override
    public void deleteAll() {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );

    }

    @Override
    public < S extends Account > S save( S entity ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public < S extends Account > List< S > saveAll( Iterable< S > entities ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public Optional< Account > findById( Long aLong ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public boolean existsById( Long aLong ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public void flush() {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );

    }

    @Override
    public < S extends Account > S saveAndFlush( S entity ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public < S extends Account > List< S > saveAllAndFlush( Iterable< S > entities ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public void deleteAllInBatch( Iterable< Account > entities ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );

    }

    @Override
    public void deleteAllByIdInBatch( Iterable< Long > ids ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );

    }

    @Override
    public void deleteAllInBatch() {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );

    }

    @Override
    public Account getOne( Long aLong ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public Account getById( Long aLong ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public < S extends Account > Optional< S > findOne( Example< S > example ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public < S extends Account > List< S > findAll( Example< S > example ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public < S extends Account > List< S > findAll( Example< S > example, Sort sort ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public < S extends Account > Page< S > findAll( Example< S > example, Pageable pageable ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public < S extends Account > long count( Example< S > example ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }

    @Override
    public < S extends Account > boolean exists( Example< S > example ) {
        throw new AssertionError( "METHOD NOT PREPARED FOR TESTS!" );
    }
}
