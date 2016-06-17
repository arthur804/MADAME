import java.util.ArrayList;

public class Klant {

	public String naam;
	public ArrayList<GeselecteerdEtenBewaard> bestelling;
	public int bestellingPrijs;
	
	public Klant(String naam, ArrayList<GeselecteerdEtenBewaard> bestellingen){
		this.naam = naam;
		this.bestelling = bestellingen;
	}
}
