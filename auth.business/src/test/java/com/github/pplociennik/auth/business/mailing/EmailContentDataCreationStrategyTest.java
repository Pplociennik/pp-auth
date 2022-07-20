package com.github.pplociennik.auth.business.mailing;

import com.github.pplociennik.auth.business.mailing.domain.model.AddressableDataDO;
import com.github.pplociennik.auth.business.mailing.domain.model.EmailConfirmationDataDO;
import com.github.pplociennik.auth.common.lang.AuthResEmailMsgTranslationKey;
import com.github.pplociennik.auth.common.lang.AuthResUnitTranslationKey;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;
import org.thymeleaf.context.Context;

import java.util.Locale;

import static com.github.pplociennik.auth.business.mailing.EmailContentDataCreationStrategy.EMAIL_CONFIRMATION_MESSAGE;
import static com.github.pplociennik.auth.common.lang.AuthResEmailMsgTranslationKey.EMAIL_ACCOUNT_CONFIRMATION_DISCLAIMER;
import static com.github.pplociennik.auth.common.lang.AuthResEmailMsgTranslationKey.EMAIL_ACCOUNT_CONFIRMATION_MESSAGE;
import static com.github.pplociennik.auth.common.lang.AuthResUnitTranslationKey.MINUTES;
import static com.github.pplociennik.util.utility.LanguageUtil.getLocalizedMessage;
import static java.util.Locale.ENGLISH;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link EmailContentDataCreationStrategy}.
 *
 * @author Created by: Pplociennik at 06.08.2022 19:57
 */
class EmailContentDataCreationStrategyTest {

    private static String getLocalizedDisclaimer( @NonNull AuthResEmailMsgTranslationKey aBaseMessage, Locale aLocale, long aAmount, @NonNull AuthResUnitTranslationKey aUnit ) {
        requireNonNull( aBaseMessage );
        requireNonNull( aUnit );

        var localizedUnit = getLocalizedMessage( aUnit, aLocale );
        var parametersForFullLocalization = new Object[]{ aAmount, localizedUnit };
        return getLocalizedMessage( aBaseMessage, aLocale, parametersForFullLocalization );
    }

    @Nested
    class AccountConfirmationRequest {

        EmailContentDataCreationStrategy sut = EMAIL_CONFIRMATION_MESSAGE;

        @Test
        void shouldThrowNullPointerException_whenNullPassedAsParameter() {

            // WHEN
            // THEN
            assertThatThrownBy( () -> sut.prepare( null ) ).isInstanceOf( NullPointerException.class );
        }

        @Test
        void shouldThrowIllegalArgumentException_whenWrongTypePassedViaParameter() {

            // GIVEN
            var wrongTypeImplementation = new TestInvalidTypeImplementationOfAddressableDataDO();

            // WHEN
            // THEN
            assertThatThrownBy( () -> sut.prepare( wrongTypeImplementation ) ).isInstanceOf( IllegalArgumentException.class );
        }

        @Test
        void shouldCreateValidEmailContentData_whenGivenDataCorrect() {

            // GIVEN
            var recipientAddress = "recipient@email.com";
            var confirmationLink = "http://test-auth-link.com/confirmationToken";
            var locale = ENGLISH;
            var data = new EmailConfirmationDataDO( recipientAddress, confirmationLink, locale );

            // WHEN
            var result = sut.prepare( data );

            // THEN
            var context = new Context();
            context.setVariable( "message", getLocalizedMessage( EMAIL_ACCOUNT_CONFIRMATION_MESSAGE, locale ) );
            context.setVariable( "confirmationLink", confirmationLink );
            context.setVariable( "disclaimer", getLocalizedDisclaimer( EMAIL_ACCOUNT_CONFIRMATION_DISCLAIMER, locale, 15L, MINUTES ) );
            var expectedContentData = EmailContentData.of( context, "confirmationRequestEmailTemplate", locale );

            assertThat( result ).usingRecursiveComparison().isEqualTo( expectedContentData );
        }

        private class TestInvalidTypeImplementationOfAddressableDataDO implements AddressableDataDO {

            @Override
            public String getRecipientAddress() {
                return null;
            }

            @Override
            public Locale getLocale() {
                return null;
            }
        }
    }

}