package com.github.pplociennik.auth.business.mailing.domain.model;

import java.io.Serializable;
import java.util.Locale;

/**
 * A single method markup interface for classes extending {@link EmailDataDO}.
 *
 * @author Created by: Pplociennik at 30.06.2022 21:43
 */
public interface AddressableDataDO extends Serializable {

    String getRecipientAddress();

    Locale getLocale();
}
