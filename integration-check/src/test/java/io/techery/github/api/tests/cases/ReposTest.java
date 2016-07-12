package io.techery.github.api.tests.cases;

import org.testng.annotations.Test;

import java.util.List;

import io.techery.github.api.repos.ContributorsHttpAction;
import io.techery.github.api.repos.ReposHttpAction;
import io.techery.github.api.repos.model.Repository;
import io.techery.github.api.tests.BaseTestWithSession;
import ru.yandex.qatools.allure.annotations.Features;

import static org.fest.assertions.api.Assertions.assertThat;

@Features("Repos")
public class ReposTest extends BaseTestWithSession {

    List<Repository> successResponse;

    @Test
    void testGetRepos() {
        ReposHttpAction action = execute(new ReposHttpAction(user()));
        assertThat(action.response()).isNotNull();
        assertThat(action.response()).isNotEmpty();
        successResponse = action.response();
    }

    @Test(dependsOnMethods = "testGetRepos")
    void testGetContributes() {
        ContributorsHttpAction action = execute(new ContributorsHttpAction(successResponse.get(0)));
        assertThat(action.response()).isNotNull();
        assertThat(action.response()).isNotEmpty();
    }
}
