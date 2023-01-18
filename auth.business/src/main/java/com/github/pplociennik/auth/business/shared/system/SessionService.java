package com.github.pplociennik.auth.business.shared.system;

import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * A service providing methods for operating the sessions'a info.
 *
 * @author Created by: Pplociennik at 17.06.2023 18:37
 */
public interface SessionService {

    /**
     * Returns attributes of the currently received request's session.
     *
     * @return the session attributes.
     */
    ServletRequestAttributes getCurrentSessionAttributes();

    /**
     * Returns identifier of the currently received request's session.
     *
     * @return the session id.
     */
    String getCurrentSessionId();
}
