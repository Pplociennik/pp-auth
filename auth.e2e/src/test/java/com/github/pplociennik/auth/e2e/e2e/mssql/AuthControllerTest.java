package com.github.pplociennik.auth.e2e.e2e.mssql;

import com.github.pplociennik.auth.e2e.config.AbstractMsSQLTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.github.pplociennik.auth.e2e.testdata.authentication.TestRegistrationDtoFactory.createRegistrationDtoWithIncorrectEmailAddress;

/**
 * TODO: Describe this class.
 *
 * @author Created by: Pplociennik at 15.02.2024 15:11
 */
@SpringBootTest
public class AuthControllerTest extends AbstractMsSQLTest {

    private static final String API_URL = "/auth";
    private static final String REGISTRATION_API_URL = API_URL + "/register";

    @Test
    void shouldThrowValidationException_whenEmailAddressInvalid() throws Exception {

        // GIVEN
        var registrationDto = createRegistrationDtoWithIncorrectEmailAddress();
        var dtoAsString = objectMapper.writeValueAsString( registrationDto );

        // WHEN
        mockMvc.perform( MockMvcRequestBuilders.post( REGISTRATION_API_URL ).contentType( MediaType.APPLICATION_JSON ).content( dtoAsString ) ).andExpect( MockMvcResultMatchers.status().isBadRequest() );
    }
}
