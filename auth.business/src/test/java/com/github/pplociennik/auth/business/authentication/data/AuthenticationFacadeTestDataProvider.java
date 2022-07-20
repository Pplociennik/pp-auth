package com.github.pplociennik.auth.business.authentication.data;

import com.github.pplociennik.auth.business.authentication.domain.model.RegistrationDO;

/**
 * A data provider for AuthenticationFacadeTest.
 *
 * @author Created by: Pplociennik at 24.07.2022 22:53
 */
public class AuthenticationFacadeTestDataProvider {

    private static final String TEST_VALID_USERNAME = "ValidUserName";
    private static final String TEST_INVALID_USERNAME = "I";
    private static final String TEST_VALID_PASSWORD = "TestPass1!";
    private static final String TEST_INVALID_PASSWORD = "pass";
    private static final String TEST_ENCODED_VALID_PASSWORD = "EncodedPass";
    private static final String TEST_VALID_EMAIL = "testEmail@gmail.com";

    private static final String TEST_OCCUPIED_USERNAME = "InvalidUserName";
    private static final String TEST_OCCUPIED_EMAIL = "occupied@email.com";

    public static RegistrationDO prepareDummyRegistrationDO() {
        return new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, TEST_VALID_PASSWORD, TEST_VALID_PASSWORD );
    }

    public static RegistrationDO prepareDummyRegistrationDOWithInvalidPassword() {
        return new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, TEST_INVALID_PASSWORD, TEST_VALID_PASSWORD );
    }

    public static RegistrationDO prepareDummyRegistrationDOWithInvalidUsername() {
        return new RegistrationDO( TEST_VALID_EMAIL, TEST_INVALID_USERNAME, TEST_VALID_PASSWORD, TEST_VALID_PASSWORD );
    }
}
