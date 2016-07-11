package io.techery.github.api.util;

import rx.functions.Func0;

public class Preconditions {

    private Preconditions() {}

    static public void check(Func0<Boolean> predicate, String failMessage) {
        if (!predicate.call()) throw new IllegalStateException(failMessage);
    }

}
