package frc.robot.libs.utils;

import java.util.function.*;

public final class Lazy {
    public static interface ILazy<T> extends Supplier<T> {
        Supplier<T> init();
        public default T get() { return init().get(); }
    }

    public static <U> Supplier<U> lazily(ILazy<U> lazy) { return lazy; }
    public static <T> Supplier<T> value(T value) { return ()->value; }
}