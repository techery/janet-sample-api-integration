package io.techery.github.api.user;

import io.techery.github.api.api_common.AuthorizedHttpAction;
import io.techery.github.api.user.model.User;
import io.techery.janet.http.annotations.HttpAction;
import io.techery.janet.http.annotations.Response;

@HttpAction("/user")
public class UserHttpAction extends AuthorizedHttpAction {

    @Response
    User user;

    public User response() {
        return user;
    }
}
