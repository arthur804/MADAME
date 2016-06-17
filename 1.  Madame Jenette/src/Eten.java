import java.util.ArrayList;

public class Eten {

	public String naam;
	public int nummer;
	public int prijs;
	protected Exstra[] exstras;
	private int index = 0;
	
	public Eten(String naam, int nummer, int prijs){
		this.prijs = prijs;
		this.nummer = nummer;
		this.naam = naam;		
	}
	public void setListSize(int size){
		exstras = new Exstra[size];
	}
	public void addExstr(Exstra item){
		exstras[index] = item;
		index++;
	}
	public void arrayListToArray(ArrayList<Exstra> extr){
		if (extr == null) 
			return;
		exstras = new Exstra[extr.size()];
		for (int i = 0; i < exstras.length; i++)
			exstras[i] = extr.get(i);
	}
	//TODO
	public void changeExstr(int index, Exstra item){
		exstras[index] = item;
		index++;
	}
}
