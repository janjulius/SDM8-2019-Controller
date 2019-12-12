package janjulius;

/**
 * Represents a Tuple
 * 
 * @author Jan Julius de Lang
 * @date Dec 9, 2019
 */
public class Tuple<L, R> {

	/**
	 * The left value
	 */
	private L left;

	/**
	 * The right value
	 */
	private R right;

	/**
	 * Constructs a new {@link Tuple}
	 */
	public Tuple(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}

	public void setLeft(L l) {
		this.left = l;
	}

	public void setRight(R r) {
		this.right = r;
	}

	@Override
	public int hashCode() {
		return left.hashCode() ^ right.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Tuple))
			return false;
		Tuple<?, ?> tupleo = (Tuple<?, ?>) o;
		return this.left.equals(tupleo.getLeft()) && this.right.equals(tupleo.getRight());
	}

}
