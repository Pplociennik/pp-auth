package com.github.pplociennik.auth.common.auth.dto.mailing;

import java.io.Serializable;
import java.util.Locale;

/**
 * A single method markup interface for classes extending {@link EmailDataDto}.
 *
 * @author Created by: Pplociennik at 30.06.2022 21:45
 */
public interface AddressableDataDto extends Serializable {

    String getRecipientAddress();

    Locale getLocale();
}
