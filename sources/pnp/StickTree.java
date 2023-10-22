package pnp;

public abstract class StickTree extends Tree {
	public StickTree() {
	}

	@Override
	public void maketrunk() {
		int x = this.pos[0];
		int y = this.pos[1];
		int z = this.pos[2];
		int i = 0;
		while (i < this.height) {
			Forester.assign_value(x, y, z, Forester.WOODMAT, Forester.WOODDATA, this.mcmap);
			y += 1;
			++i;
		}
	}
}