package com.pitchbook.bootcamp;

import java.util.Objects;

class Tuple<T1, T2> {

    private T1 firstType;
    private T2 secondType;

    Tuple(T1 firstType, T2 secondType) {
        this.firstType = firstType;
        this.secondType = secondType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(firstType, tuple.firstType) &&
                Objects.equals(secondType, tuple.secondType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstType, secondType);
    }
}
