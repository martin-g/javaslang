/*     / \____  _    ______   _____ / \____   ____  _____
 *    /  \__  \/ \  / \__  \ /  __//  \__  \ /    \/ __  \   Javaslang
 *  _/  // _\  \  \/  / _\  \\_  \/  // _\  \  /\  \__/  /   Copyright 2014-2015 Daniel Dietrich
 * /___/ \_____/\____/\_____/____/\___\_____/_/  \_/____/    Licensed under the Apache License, Version 2.0
 */
package javaslang.algebra;

import javaslang.test.Arbitrary;
import javaslang.test.CheckResult;
import javaslang.test.Property;

import java.util.function.Function;

public interface FunctorLaws {

    void shouldSatisfyFunctorIdentity();

    void shouldSatisfyFunctorComposition();

    // m.map(id) ≡ id
    default <T> CheckResult checkFunctorIdentity(Arbitrary<? extends Functor<T>> functors) {
        return new Property("functor.identity")
                .forAll(functors)
                .suchThat(functor -> functor.map(t -> t).equals(functor))
                .check();
    }

    // m.map(f).map(g) ≡ m.map(x -> g.apply(f.apply(x)))
    default <T, U, V> CheckResult checkFunctorComposition(Arbitrary<? extends Functor<T>> functors,
                                                          Arbitrary<Function<? super T, ? extends U>> fs,
                                                          Arbitrary<Function<? super U, ? extends V>> gs) {
        return new Property("functor.composition")
                .forAll(functors, fs, gs)
                .suchThat((functor, f, g) -> functor.map(f).map(g).equals(functor.map(t -> g.apply(f.apply(t)))))
                .check();
    }
}
