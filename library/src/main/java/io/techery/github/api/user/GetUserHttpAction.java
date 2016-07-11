package io.techery.github.api.user;

import io.techery.github.api.api_common.AuthorizedHttpAction;
import io.techery.github.api.user.model.User;
import io.techery.janet.http.annotations.HttpAction;
import io.techery.janet.http.annotations.Path;
import io.techery.janet.http.annotations.Response;

@HttpAction("/users/{username}")
public class GetUserHttpAction extends AuthorizedHttpAction {

    @Response
    User user;

    @Path("username")
    String username;

    public GetUserHttpAction(String username) {
        this.username = username;
    }

    public User response() {
        return user;
    }
}
