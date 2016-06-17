
public class GeselecteerdEtenBewaard {

	public boolean[] selected;
	protected Eten eten;
	protected boolean isAanbieding;

	public GeselecteerdEtenBewaard(Eten eten) {
		if (eten.exstras == null)
			selected = null;
		else
			selected = new boolean[eten.exstras.length];
		this.eten = eten;
		if (selected != null)
			for (int i = 0; i < selected.length; i++) {
				selected[i] = false;
			}
	}
}
