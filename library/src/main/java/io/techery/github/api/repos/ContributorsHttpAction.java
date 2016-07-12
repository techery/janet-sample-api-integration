package io.techery.github.api.repos;

import java.util.List;

import io.techery.github.api.common.AuthorizedHttpAction;
import io.techery.github.api.repos.model.RepositoryModel;
import io.techery.github.api.user.model.User;
import io.techery.janet.http.annotations.HttpAction;
import io.techery.janet.http.annotations.Path;
import io.techery.janet.http.annotations.Response;

@HttpAction("/repos/{owner}/{repo}/contributors")
public class ContributorsHttpAction extends AuthorizedHttpAction {

    @Path("owner")
    final String ownerLogin;

    @Path("repo")
    final String repoName;

    @Response
    List<User> contributors;

    private final RepositoryModel repository;

    public ContributorsHttpAction(RepositoryModel repository) {
        this.repository = repository;
        this.ownerLogin = repository.owner().login();
        this.repoName = repository.name();
    }

    public RepositoryModel repository() {
        return repository;
    }

    public List<User> response() {
        return contributors;
    }
}
