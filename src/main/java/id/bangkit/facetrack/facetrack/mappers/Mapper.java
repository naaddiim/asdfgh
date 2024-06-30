package id.bangkit.facetrack.facetrack.mappers;

import org.checkerframework.checker.units.qual.A;

public interface Mapper<A, B> {
    B mapTo(A a);
    A mapFrom(B b);
}
