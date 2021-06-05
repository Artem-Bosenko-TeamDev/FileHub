package io.javaclasses.fileHub.services.users;

import com.google.common.base.Preconditions;
import io.javaclasses.fileHub.persistent.DuplicatedUserIdException;
import io.javaclasses.fileHub.persistent.users.User;
import io.javaclasses.fileHub.persistent.users.UserStorage;
import io.javaclasses.fileHub.persistent.users.tokens.AuthorizationStorage;
import io.javaclasses.fileHub.persistent.users.tokens.AuthorizationUsers;
import io.javaclasses.fileHub.persistent.users.tokens.UserAuthToken;
import io.javaclasses.fileHub.services.AuthToken;
import io.javaclasses.fileHub.services.InvalidHandleCommandException;
import io.javaclasses.fileHub.services.OpenUserProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static com.google.common.base.Preconditions.*;

/**
 * This is service for authentication user in Filehub application if he is been in {@link UserStorage user storage}.
 */
public class AuthenticationUser implements OpenUserProcess<AuthenticationUserCommand, AuthToken> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationUser.class);

    private final UserStorage userStorage;
    private final AuthorizationStorage authorizationStorage;

    public AuthenticationUser(UserStorage userStorage, AuthorizationStorage authorizationStorage) {
        this.userStorage = checkNotNull(userStorage);
        this.authorizationStorage = checkNotNull(authorizationStorage);
    }

    @Override
    public AuthToken handle(AuthenticationUserCommand inputCommand) throws InvalidHandleCommandException {

        if (logger.isInfoEnabled()) {
            logger.info("Start authenticated process for user: " + inputCommand.loginName());
        }

        String password = PasswordEncoder.encode(inputCommand.password());

        Optional<User> user = userStorage.findByLoginAndPassword(inputCommand.loginName(), password);

        if (user.isPresent()) {

            AuthToken token = GenerateUniqueUserToken.generateToken(authorizationStorage);

            if (logger.isInfoEnabled()) {
                logger.info("Authenticate process was successful. User: " + user.get().login() + " have token: " + token);
            }

            try {

                authorizationStorage.create(new AuthorizationUsers(new UserAuthToken(token.value()),
                        user.get().id(), ZonedDateTime.now(ZoneId.of("America/Los_Angeles")).plusHours(6)));

                if(logger.isInfoEnabled()){
                    logger.info("Token for user " + user.get().login() +" was created. Value = " + token.value());
                }

                return token;

            } catch (DuplicatedUserIdException e) {

                if(logger.isErrorEnabled()){
                    logger.error(e.getMessage());
                }

                throw new InvalidHandleCommandException(e.getMessage());

            }

        } else {

            if (logger.isErrorEnabled()) {
                logger.error("User with " + inputCommand.loginName() + " not exist");
            }

            throw new InvalidHandleCommandException("User with " + inputCommand.loginName() + " not exist");
        }
    }
}
