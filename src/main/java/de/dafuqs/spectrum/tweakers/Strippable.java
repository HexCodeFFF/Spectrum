package de.dafuqs.spectrum.tweakers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When used on a class, methods from referenced interfaces will not be removed
 * <br>
 * When using this annotation on methods, ensure you do not switch on an enum inside that method.
 * JavaC implementation details means this will cause crashes.
 * <p>
 * Can also strip on modid using "mod:CoFHCore" as a value
 * <br>
 * Can also strip on API using "api:CoFHAPI|energy" as a value
 *
 * Original by https://github.com/CoFH/CoFHCore-1.12-Legacy/blob/56645a1161e30ef79b59d85c1ea5a20c41ab3a2f/src/main/java/cofh/asm/relauncher/Strippable.java
 * used according to licence. Thanks, team CoFH!
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE })
public @interface Strippable {

	public String[] value();
	
}