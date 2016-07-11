package io.techery.github.api.user;

import io.techery.github.api.api_common.AuthorizedHttpAction;
import io.techery.github.api.user.model.UpdateUserParams;
import io.techery.github.api.user.model.User;
import io.techery.janet.http.annotations.Body;
import io.techery.janet.http.annotations.HttpAction;
import io.techery.janet.http.annotations.Response;

@HttpAction(value = "/user", method = HttpAction.Method.PATCH)
public class UpdateUserHttpAction extends AuthorizedHttpAction {

    @Body
    UpdateUserParams params;

    @Response
    User user;

    public UpdateUserHttpAction(UpdateUserParams params) {
        this.params = params;
    }

    public User response() {
        return user;
    }
}
