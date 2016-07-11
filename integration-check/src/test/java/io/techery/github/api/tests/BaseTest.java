package io.techery.github.api.tests;

import org.fest.assertions.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.techery.github.api.GetServerStatusAction;
import io.techery.github.api.api_common.BaseHttpAction;
import io.techery.github.api.api_common.converter.GsonProvider;
import io.techery.github.api.fixtures.UserCredential;
import io.techery.github.api.fixtures.util.FixtureAnnotations;
import io.techery.github.api.http.executor.ActionExecutor;
import io.techery.github.api.http.executor.AuthorizedActionExecutor;
import io.techery.github.api.http.executor.SimpleActionExecutor;
import io.techery.github.api.user.UserHttpAction;

import static org.fest.assertions.api.Assertions.assertThat;

public class BaseTest implements ActionExecutor {

    ///////////////////////////////////////////////////////////////////////////
    // Common Setup
    ///////////////////////////////////////////////////////////////////////////

    protected static final String DATA_LOAD = "bt.data_load";

    protected static final String BASE_SETUP = "bt.preparation";

    @BeforeSuite
    public void checkServer() {
        ActionExecutor executor = new SimpleActionExecutor();

        GetServerStatusAction action = executor.execute(new GetServerStatusAction());
        assertThat(action.state()).isEqualTo(GetServerStatusAction.ServerState.AVAILABLE);
    }

    @BeforeClass(groups = {DATA_LOAD})
    public void injectFixtures() throws Exception {
        FixtureAnnotations.initFixtures(this, new GsonProvider().provideGson());
    }

    ///////////////////////////////////////////////////////////////////////////
    // Janet setup
    ///////////////////////////////////////////////////////////////////////////

    private ActionExecutor actionExecutor;

    @BeforeClass(groups = {BASE_SETUP}, dependsOnGroups = {DATA_LOAD})
    public void createActionExecutor() throws Exception {
        actionExecutor = provideActionExecutor();
    }

    protected ActionExecutor provideActionExecutor() {
        return new SimpleActionExecutor();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Sugar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * All request should be executed via this method. Execution is blocking
     *
     * @param action to execute
     * @return the same executed action with response data filled in
     */
    @Override
    public final <T extends BaseHttpAction> T execute(T action) {
        return actionExecutor.execute(action);
    }

    protected final AuthorizedActionExecutor as(UserCredential credential) {
        AuthorizedActionExecutor executor = new AuthorizedActionExecutor(credential.username(), credential.password());
        UserHttpAction action = executor.authorize();
        Assertions.assertThat(action.statusCode()).isEqualTo(200);
        assertThat(action.response()).isNotNull();
        return executor;
    }
}
