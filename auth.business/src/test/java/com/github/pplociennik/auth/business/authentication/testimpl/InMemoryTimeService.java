package com.github.pplociennik.auth.business.authentication.testimpl;

import com.github.pplociennik.auth.business.shared.system.TimeService;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * In memory implementation of the {@link TimeService}.
 *
 * @author Created by: Pplociennik at 14.08.2022 21:56
 */
public class InMemoryTimeService implements TimeService {

    private ZoneId systemZoneId;
    private ZonedDateTime currentSystemDateTime;

    @Override
    public ZoneId getSystemZoneId() {
        return systemZoneId;
    }

    @Override
    public ZonedDateTime getCurrentSystemDateTime() {
        return currentSystemDateTime;
    }

    public void setCurrentSystemDateTime( ZonedDateTime aCurrentSystemDateTime ) {
        currentSystemDateTime = aCurrentSystemDateTime;
    }

    public void setSystemZoneId( ZoneId aSystemZoneId ) {
        systemZoneId = aSystemZoneId;
    }
}
