import java.lang.annotation.*;

/**
 * Анотації для спостерігачів.
 */

// видно під час роботи програми, тому Reflection її і читає
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface ObserverRole {
    String value();
}

// залишається в .class але JVM вже не бачить
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@interface ClassOnly {
}

// тільки в вихідному коді, після компіляції зникає
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@interface SourceOnly {
}