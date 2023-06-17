package com.github.pplociennik.auth.business.shared.system.time;

import com.github.pplociennik.auth.business.shared.system.SystemPropertiesProvider;
import com.github.pplociennik.auth.business.shared.system.TimeService;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.github.pplociennik.auth.business.shared.system.SystemProperties.SYSTEM_JPA_TIME_ZONE;

/**
 * A service providing system time management. Provides functionalities for time/time zones' management and conversion.
 * The system time zone is being stored in and read from the system properties. If it is not, the default system time
 * zone is "UTC".
 *
 * @author Created by: Pplociennik at 14.08.2022 20:51
 */
public class SystemTimeServiceImpl implements TimeService {

    private static final String DEFAULT_SYSTEM_TIME_ZONE = "UTC";
    private final SystemPropertiesProvider propertiesProvider;

    public SystemTimeServiceImpl( SystemPropertiesProvider aPropertiesProvider ) {
        propertiesProvider = aPropertiesProvider;
    }

    /**
     * Returns the system time zone. The time zone should be set in the system properties. In case if it is not, the
     * method returns a default system time zone "UTC".
     *
     * @return the system time zone
     */
    @Override
    public ZoneId getSystemZoneId() {
        var systemTimeZone = getSystemTimeZone();

        return systemTimeZone.isBlank()
                ? ZoneId.of( DEFAULT_SYSTEM_TIME_ZONE )
                : ZoneId.of( systemTimeZone );
    }

    /**
     * Returns current system date and time with system time zone.
     *
     * @return current zoned date time
     */
    @Override
    public ZonedDateTime getCurrentSystemDateTime() {
        return ZonedDateTime.now( getSystemZoneId() );
    }

    private String getSystemTimeZone() {
        return propertiesProvider.getPropertyValue( SYSTEM_JPA_TIME_ZONE );
    }
}
