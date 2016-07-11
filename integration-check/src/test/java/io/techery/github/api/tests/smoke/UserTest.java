package io.techery.github.api.tests.smoke;


import org.testng.annotations.Test;

import ie.corballis.fixtures.annotation.Fixture;
import io.techery.github.api.tests.BaseTestWithSession;
import io.techery.github.api.user.GetUserHttpAction;
import io.techery.github.api.user.UpdateUserHttpAction;
import io.techery.github.api.user.UserHttpAction;
import io.techery.github.api.user.model.UpdateUserParams;
import ru.yandex.qatools.allure.annotations.Features;

import static org.fest.assertions.api.Assertions.assertThat;

@Features("User")
public class UserTest extends BaseTestWithSession {

    @Fixture("username")
    String username;

    @Fixture("update_params")
    UpdateUserParams updateParams;

    @Fixture("default_params")
    UpdateUserParams defaultParams;

    @Test
    void testGetCurrentUser() {
        UserHttpAction action = execute(new UserHttpAction());
        assertThat(action.response()).isNotNull();
        assertThat(action.response().login()).isEqualTo(authorizedUser().username());
    }

    @Test
    void testGetUser() {
        GetUserHttpAction action = execute(new GetUserHttpAction(username));
        assertThat(action.response()).isNotNull();
        assertThat(action.response().login()).isEqualTo(username);
    }

    @Test
    void testUpdateUser() {
        UpdateUserHttpAction action = execute(new UpdateUserHttpAction(updateParams));

        assertThat(action.response()).isNotNull();
        assertThat(action.response().name()).isEqualTo(updateParams.name());

        //return default params
        action = execute(new UpdateUserHttpAction(defaultParams));
        assertThat(action.response()).isNotNull();
        assertThat(action.response().name()).isEqualTo(defaultParams.name());
    }
}
