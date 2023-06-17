package com.github.pplociennik.auth.e2e.it;

import com.github.pplociennik.auth.business.mailing.EmailFacade;
import com.github.pplociennik.auth.business.mailing.domain.map.MailingMapper;
import com.github.pplociennik.auth.e2e.config.EmailFacadeTestContextConfiguration;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.pplociennik.auth.e2e.it.data.EmailFacadeTestDataSupplier.getDummyEmailConfirmationSendingDto;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link EmailFacade}.
 *
 * @author Created by: Pplociennik at 14.06.2022 17:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmailFacadeTestContextConfiguration.class)
@TestPropertySource("classpath:application-test.properties")
class EmailFacadeTestIT {

    private static final String SENDER_ADDRESS = "sender@address.com";
    private static final String RECIPIENT_ADDRESS = "bb@bb.com";
    private GreenMail smtpServer;
    @Autowired
    private EmailFacade emailFacade;

    @BeforeEach
    void setUp() {
        smtpServer = new GreenMail( new ServerSetup( 587, null, "smtp" ) );
        smtpServer.start();
    }

    @AfterEach
    void tearDown() {
        smtpServer.stop();
    }

    @Nested
    class sendingEmails {

        @Test
        @Disabled("Disabled until the post server and async testing will be up.")
        void shouldSendEmailConfirmationRequestMessage() {

            // GIVEN
            var emailDataDto = getDummyEmailConfirmationSendingDto();

            // WHEN
            emailFacade.sendEmailConfirmationRequest( MailingMapper.mapToDO( emailDataDto ) );

            // THEN
            var receivedMessages = getReceivedMessages();
            assertThat( receivedMessages.length ).isEqualTo( 1 );
        }

        private MimeMessage[] getReceivedMessages() {
            return smtpServer.getReceivedMessages();
        }
    }

}
