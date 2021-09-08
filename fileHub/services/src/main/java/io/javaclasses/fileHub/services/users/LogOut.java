package io.javaclasses.fileHub.services.users;

import io.javaclasses.fileHub.persistent.NotExistedItem;
import io.javaclasses.fileHub.persistent.users.tokens.AuthorizationStorage;
import io.javaclasses.fileHub.services.SecuredUserProcess;
import io.javaclasses.fileHub.services.files.UsersTokenNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Completes an active user session by {@link io.javaclasses.fileHub.services.AuthToken user token}.
 */
public class LogOut extends SecuredUserProcess<LogOutCommand, String> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticateUser.class);

    private final AuthorizationStorage authorizationStorage;

    public LogOut(AuthorizationStorage authorizationStorage) {

        super(checkNotNull(authorizationStorage));

        this.authorizationStorage = authorizationStorage;
    }

    @Override
    protected String doHandle(LogOutCommand inputCommand) throws UsersTokenNotFoundException {

        if (logger.isInfoEnabled()) {
            logger.info("Start deleting token " + inputCommand.token());
        }

        try {

            authorizationStorage.delete(inputCommand.token().value());

            if (logger.isInfoEnabled()) {
                logger.info("Deleting token was successful.");
            }

            return inputCommand.token().value();

        } catch (NotExistedItem notExistedItem) {

            if (logger.isErrorEnabled()) {
                logger.error(notExistedItem.getMessage());
            }

            throw new UsersTokenNotFoundException(inputCommand.token());

        }

    }
}
