package de.dafuqs.spectrum.tweakers;

import java.lang.annotation.*;

/**
 * Adds an interface to the class when the interface is present at runtime for soft dependencies.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Implementable {

	public String[] value();

	/**
	 * The *only* side on which these interfaces will be implemented if present (NONE == BOTH)
	 */
	public boolean clientOnly() default false;
}