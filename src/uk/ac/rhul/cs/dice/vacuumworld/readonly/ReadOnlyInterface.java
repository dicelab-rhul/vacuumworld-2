package uk.ac.rhul.cs.dice.vacuumworld.readonly;

/**
 * An instance of a class may be converted to a read-only copy when implementing
 * this interface. The read-only class must have a constructor ready to accept
 * the original instance, and from that instance should make a copy. Any setter
 * methods should do nothing and access to all fields should be protected or
 * private (depending on the extent of the read constraints). Any getter methods
 * that provide a mutable {@link Object} should also be converted to read-only
 * or they should be copied on return so that an alteration has no affect.
 * 
 * </br> As an example we may have:
 * "MyObject implements ReadOnlyInterface<\MyObjectReadOnly\>"
 * 
 * @author Ben Wilkins
 *
 * @param <A>
 *            the read-only class
 */
public interface ReadOnlyInterface<A> {

	public Class<A> getReadOnlyClass();
}
