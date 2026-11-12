package se.yrgo.libraryapp.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.yrgo.libraryapp.dao.UserDao;
import se.yrgo.libraryapp.entities.LoginInfo;
import se.yrgo.libraryapp.entities.UserId;
import se.yrgo.libraryapp.services.UserService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class UserServiceTest {
    @Mock
    private UserDao userDao;
    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Test
    void correctLogin() {
        final String username = "testuser";
        final String password = "password";
        final String passwordHash = "hashedPassword";

        final UserId id = UserId.of(1);
        final LoginInfo info = new LoginInfo(id, passwordHash);

        when(userDao.getLoginInfo(username))
                .thenReturn(Optional.of(info));

        when(encoder.matches(password, passwordHash))
                .thenReturn(true);

        assertThat(userService.validate(username, password))
                .isEqualTo(Optional.of(id));

        verify(userDao).getLoginInfo(username);
        verify(encoder).matches(password, passwordHash);
    }

    @Test
    void validateShouldReturnEmptyWhenUserDoesNotExist() {
        String username = "unknown";
        String password = "password";

        when(userDao.getLoginInfo(username))
                .thenReturn(Optional.empty());

        assertThat(userService.validate(username, password)).isEmpty();

        verify(userDao).getLoginInfo(username);
        verifyNoInteractions(encoder);
    }

    @Test
    void validateShouldReturnEmptyWhenPasswordIsIncorrect() {
        String username = "testuser";
        String password = "wrongPassword";
        String passwordHash = "hashedPassword";

        LoginInfo info = new LoginInfo(UserId.of(1), passwordHash);

        when(userDao.getLoginInfo(username))
                .thenReturn(Optional.of(info));

        when(encoder.matches(password, passwordHash))
                .thenReturn(false);

        assertThat(userService.validate(username, password)).isEmpty();

        verify(encoder).matches(password, passwordHash);
    }

    @Test
    void registerShouldHashPasswordAndCallDao() {

        String name = "testuser";
        String realname = "Test User";
        String password = "password";
        String passwordHash = "hashedPassword";

        when(encoder.encode(password))
                .thenReturn(passwordHash);

        when(userDao.register(name, realname, passwordHash))
                .thenReturn(true);

        boolean result = userService.register(name, realname, password);

        assertThat(result).isTrue();

        verify(encoder).encode(password);

        verify(userDao).register(name, realname, passwordHash);
    }

    @Test
    void registerShouldReturnFalseWhenDaoRegistrationFails() {
        String name = "testuser";
        String realname = "Test User";
        String password = "password";
        String passwordHash = "hashedPassword";

        when(encoder.encode(password))
                .thenReturn(passwordHash);

        when(userDao.register(name, realname, passwordHash))
                .thenReturn(false);

        boolean result = userService.register(name, realname, password);

        assertThat(result).isFalse();

        verify(encoder).encode(password);
        verify(userDao).register(name, realname, passwordHash);
    }

    @Test
    void isNameAvailableShouldReturnFalseWhenNameIsNull() {
        assertThat(userService.isNameAvailable(null)).isFalse();

        verifyNoInteractions(userDao);
    }

    @Test
    void isNameAvailableShouldReturnFalseWhenNameIsTooShort() {
        assertThat(userService.isNameAvailable("ab")).isFalse();

        verifyNoInteractions(userDao);
    }

    @Test
    void isNameAvailableShouldReturnTrueWhenNameIsAvailable() {
        String name = "testuser";

        when(userDao.isNameAvailable(name)).thenReturn(true);

        assertThat(userService.isNameAvailable(name)).isTrue();

        verify(userDao).isNameAvailable(name);
    }

    @Test
    void isNameAvailableShouldReturnFalseWhenNameAlreadyExists() {
        String name = "testuser";

        when(userDao.isNameAvailable(name)).thenReturn(false);

        assertThat(userService.isNameAvailable(name)).isFalse();

        verify(userDao).isNameAvailable(name);
    }
}