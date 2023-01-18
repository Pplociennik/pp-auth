package com.github.pplociennik.auth.business.shared.system.session;

import com.github.pplociennik.auth.business.shared.system.SessionService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * A service providing methods for operating the sessions'a info.
 *
 * @author Created by: Pplociennik at 17.06.2023 18:40
 */
public class SessionServiceImpl implements SessionService {

    /**
     * Returns attributes of the currently received request's session.
     *
     * @return the session attributes.
     */
    @Override
    public ServletRequestAttributes getCurrentSessionAttributes() {
        return ( ( ServletRequestAttributes ) RequestContextHolder.getRequestAttributes() );
    }

    /**
     * Returns identifier of the currently received request's session.
     *
     * @return the session id.
     */
    @Override
    public String getCurrentSessionId() {
        var sessionAttributes = getCurrentSessionAttributes();
        return sessionAttributes.getSessionId();
    }
}
