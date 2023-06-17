package com.github.pplociennik.auth.business.shared.system;

import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link SystemPropertiesProvider}.
 *
 * @author Created by: Pplociennik at 20.07.2022 23:32
 */
class EnvironmentPropertiesProviderTest {

    private final Environment environment = mock( Environment.class );
    private final SystemPropertiesProvider sut = new SystemPropertiesProvider( environment );

    @Test
    void shouldThrowNullPointerException_whenNullPassedViaParameter() {
        assertThatThrownBy( () -> sut.getPropertyValue( null ) ).isInstanceOf( NullPointerException.class );
    }

}