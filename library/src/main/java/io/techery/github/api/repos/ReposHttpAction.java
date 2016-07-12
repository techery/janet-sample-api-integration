package io.techery.github.api.repos;

import java.util.List;

import io.techery.github.api.common.AuthorizedHttpAction;
import io.techery.github.api.repos.model.Repository;
import io.techery.github.api.user.model.UserModel;
import io.techery.janet.http.annotations.HttpAction;
import io.techery.janet.http.annotations.Path;
import io.techery.janet.http.annotations.Response;

@HttpAction("/user/{user_id}/repos")
public class ReposHttpAction extends AuthorizedHttpAction {

    @Path(value = "user_id", encode = false)
    final String userId;

    @Response
    List<Repository> repositories;

    private final UserModel user;

    public ReposHttpAction(UserModel user) {
        this.user = user;
        this.userId = user.id().toString();
    }

    public UserModel user() {
        return user;
    }

    public List<Repository> response() {
        return repositories;
    }
}
