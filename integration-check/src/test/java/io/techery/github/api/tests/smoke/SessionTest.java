package io.techery.github.api.tests.smoke;

import org.testng.annotations.Test;

import ie.corballis.fixtures.annotation.Fixture;
import io.techery.github.api.fixtures.UserCredential;
import io.techery.github.api.http.executor.AuthorizedActionExecutor;
import io.techery.github.api.tests.BaseTestWithSession;
import io.techery.github.api.user.UserHttpAction;
import io.techery.github.api.user.model.User;
import ru.yandex.qatools.allure.annotations.Features;

import static org.fest.assertions.api.Assertions.assertThat;

@Features("Session")
public class SessionTest extends BaseTestWithSession {

    @Fixture("default_user")
    UserCredential defaultUser;

    @Fixture("invalid_user")
    UserCredential invalidUser;

    @Test
    void testSuccessfulCredential() {

        UserHttpAction action = as(defaultUser).authorize();
        User user = action.response();

        assertThat(user).isNotNull();
        assertThat(user.id()).isNotNull();
        assertThat(user.login()).isNotNull().isNotEmpty();
        assertThat(user.url()).isNotNull().isNotEmpty();
    }

    @Test(dependsOnMethods = "testSuccessfulCredential")
    void testInvalidCredential() {
        AuthorizedActionExecutor executor = new AuthorizedActionExecutor(invalidUser.username(), invalidUser.password());
        UserHttpAction action = executor.authorize();

        assertThat(action.statusCode()).isEqualTo(401);
        assertThat(action.errorResponse()).isNotNull();
    }

}
