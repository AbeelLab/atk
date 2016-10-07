/**
 * %HEADER%
 */
package be.abeel.util;

/**
 * Represents a pair or couple of objects.
 * 
 * @author Thomas Abeel
 * 
 * @param <S>
 *            type of the first element
 * @param <T>
 *            type of the second element
 */
public class Triplet<S, T, U> {

    private S x;

    private T y;

    private U z;

    private int hashCode;

    public Triplet(S x, T y, U z) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.hashCode = (2 << x.hashCode()) + (1 << y.hashCode()) + z.hashCode();
    }

    public S x() {
        return x;

    }

    public T y() {
        return y;
    }

    public U z() {
        return z;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        Triplet p = (Triplet) o;
        return p.x.equals(this.x) && p.y.equals(this.y) && p.z.equals(this.z);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "[" + x.toString() + ";" + y.toString()+ ";" + z.toString() + "]";
    }
}