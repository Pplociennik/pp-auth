package com.github.pplociennik.auth.e2e.testdata.authentication;

import auth.dto.RegistrationDto;

/**
 * TODO: Describe this class.
 *
 * @author Created by: Pplociennik at 15.02.2024 15:14
 */
public class TestRegistrationDtoFactory {

    private static final String CORRECT_USERNAME = "TestCorrectUsername";
    private static final String CORRECT_PASSWORD = "Test1234!";
    private static final String INCORRECT_EMAIL_ADDRESS_WITHOUT_DOT = "incorrect@email";

    public static RegistrationDto createRegistrationDtoWithIncorrectEmailAddress() {
        return RegistrationDto.builder()
                .email( INCORRECT_EMAIL_ADDRESS_WITHOUT_DOT )
                .username( CORRECT_USERNAME )
                .password( CORRECT_PASSWORD )
                .repeatedPassword( CORRECT_PASSWORD )
                .build();
    }
}
