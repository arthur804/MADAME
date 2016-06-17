import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class FileClass {

	private File menuFile;

	public ImageIcon getIcon(){
		try {
		return new ImageIcon(System.getProperty("user.dir") + "//Pictorgram.png");
		} catch(Exception e){
			return null;
		}
	}
	public FileClass() throws IOException {
		String location = System.getProperty("user.dir") + "//Prijs Map";
		// System.out.println(System.getProperty("user.dir"));
		try {
			menuFile = new File(location + "//MenuItems.txt");
			if (!menuFile.exists())
				menuFile.createNewFile();
		} catch (IOException e) {
			File folder = new File(location);
			folder.mkdir();
			menuFile = new File(location + "//MenuItems.txt");
			menuFile.createNewFile();
		}
	}

	public ArrayList<Eten> geefEtenLijst() throws IOException {
		ArrayList<Eten> etenLijst = new ArrayList<Eten>(100);
		FileInputStream fis = new FileInputStream(menuFile);

		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		int number = 0;
		String name = "";
		int price = 0;
		Eten now = null;

		String line = br.readLine();
		while (line != null) {
			if (line.length() == 2) {
				number = Integer.parseInt(line);
			} else if (line.startsWith("Na")) {
				name = line.substring(6);
			} else if (line.startsWith("Pr")) {
				price = Integer.parseInt(line.substring(7));
			} else if (line.startsWith("Ex")) {
				now = new Eten(name, number, price);
				etenLijst.add(now);
				now.setListSize(Integer.parseInt(line.substring(9)));
			} else if (line.startsWith("be")) {
				name = line.substring(14);
			} else if (line.startsWith("pr")) {
				price = Integer.parseInt(line.substring(7));
				Exstra ex = new Exstra(name, price);
				now.addExstr(ex);
			}
			line = br.readLine();
		}
		br.close();

		return etenLijst;
	}

	public void makeNewMenu(ArrayList<Eten> eten) throws IOException {
		if (menuFile.delete())
			menuFile.createNewFile();
		PrintWriter writer = new PrintWriter(menuFile);

		for (int i = 0; i < eten.size(); i++) {
			Eten etenO = eten.get(i);
			if (etenO.nummer < 10)
				writer.println("0" + etenO.nummer);
			else
				writer.println(etenO.nummer);
			writer.println("Naam: " + etenO.naam);
			if (etenO.prijs < 1000) {
				if (etenO.prijs < 100) {
					if (etenO.prijs < 10) {
						writer.println("Prijs: 000" + etenO.prijs);
					} else {
						writer.println("Prijs: 00" + etenO.prijs);
					}
				} else {
					writer.println("Prijs: 0" + etenO.prijs);
				}
			} else
				writer.println("Prijs: " + etenO.prijs);
			if (etenO.exstras == null) {
				writer.println("Exstras: 0");
				continue;
			}
			writer.println("Exstras: " + etenO.exstras.length);
			for (int ii = 0; ii < etenO.exstras.length; ii++) {
				writer.println("beschrijving: " + etenO.exstras[ii].omschrijving);
				if (etenO.exstras[ii].prijs < 1000) {
					if (etenO.exstras[ii].prijs < 100) {
						if (etenO.exstras[ii].prijs < 10) {
							writer.println("prijs: 000" + etenO.exstras[ii].prijs);
						} else {
							writer.println("prijs: 00" + etenO.exstras[ii].prijs);
						}
					} else {
						writer.println("prijs: 0" + etenO.exstras[ii].prijs);
					}
				} else
					writer.println("prijs: " + etenO.exstras[ii].prijs);
			}
		}
		writer.close();
	}

	public void veranderKlantenFile(Klant klant, String datum) throws IOException {
		int klantprijs;
		int totaalprijs = 0;
		ArrayList<Klant> klanten = krijgKlanten(datum);
		klanten.add(klant);
		String location = System.getProperty("user.dir") + "//Prijs Map" + "//Klanten Map";
		File aanbiedingenFile;
		try {
			aanbiedingenFile = new File(location + "//" + datum + ".txt");
			if (!aanbiedingenFile.exists())
				aanbiedingenFile.createNewFile();
		} catch (IOException e) {
			File folder = new File(location);
			folder.mkdir();
			aanbiedingenFile = new File(location + "//" + datum + ".txt");
			aanbiedingenFile.createNewFile();
			return;
		}

		if (aanbiedingenFile.delete())
			aanbiedingenFile.createNewFile();
		PrintWriter writer = new PrintWriter(aanbiedingenFile);
		// ------------------------------------------

		for (int i = 0; i < klanten.size(); i++) {
			Klant klantO = klanten.get(i);
			klantprijs = klantO.bestellingPrijs;
			totaalprijs += klantO.bestellingPrijs;
			writer.println("KlantNaam: " + klantO.naam);
			for (int ii = 0; ii < klantO.bestelling.size(); ii++){
				String exstra = "";
				if (klantO.bestelling.get(ii).isAanbieding)
					exstra = "(A)";
				writer.println("Naam: " + exstra + klantO.bestelling.get(ii).eten.naam);
				writer.println("Prijs: " + klantO.bestelling.get(ii).eten.prijs);
				if (klantO.bestelling.get(ii).eten.exstras == null) {
					writer.println("Exstras: 0");
					continue;
				}
				writer.println("Exstras: " + klantO.bestelling.get(ii).eten.exstras.length);
				for (int iii = 0; iii < klantO.bestelling.get(ii).eten.exstras.length; iii++) {
					writer.println("beschrijving: " + klantO.bestelling.get(ii).eten.exstras[iii].omschrijving);
					if (klantO.bestelling.get(ii).eten.exstras[iii].prijs < 1000) {
						if (klantO.bestelling.get(ii).eten.exstras[iii].prijs < 100) {
							if (klantO.bestelling.get(ii).eten.exstras[iii].prijs < 10) {
								writer.println("prijs: 000" + klantO.bestelling.get(ii).eten.exstras[iii].prijs);
							} else {
								writer.println("prijs: 00" + klantO.bestelling.get(ii).eten.exstras[iii].prijs);
							}
						} else {
							writer.println("prijs: 0" + klantO.bestelling.get(ii).eten.exstras[iii].prijs);
						}
					} else
						writer.println("prijs: " + klantO.bestelling.get(ii).eten.exstras[iii].prijs);
					String jaOfNee;
					if (klantO.bestelling.get(ii).selected[iii])
						jaOfNee = "ja";
					else
						jaOfNee = "nee";
					writer.println("gekozen: " +  jaOfNee);
				}
				
			}
			writer.println("=Bestelling:   "+ klantprijs);
		}
		writer.println("TOTAAL VERDIEND: " +  totaalprijs);
		writer.close();
	}

	public ArrayList<Klant> krijgKlanten(String datum) throws IOException {
		ArrayList<Klant> klantenLijst = new ArrayList<Klant>();
		String location = System.getProperty("user.dir") + "//Prijs Map" + "//Klanten Map";
		File klantenFile;
		try {
			klantenFile = new File(location + "//" + datum + ".txt");
			if (!klantenFile.exists()){
				klantenFile.createNewFile();
				return new ArrayList<Klant>();
			}
		} catch (IOException e) {
			File folder = new File(location);
			folder.mkdir();
			klantenFile = new File(location + "//" + datum + ".txt");
			klantenFile.createNewFile();
			return new ArrayList<Klant>();
		}
		FileInputStream fis = new FileInputStream(klantenFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		String klantnaam = "";
		String name = "";
		int price = 0;
		boolean aanbieding = false;
		ArrayList<GeselecteerdEtenBewaard> etenNow = new ArrayList<GeselecteerdEtenBewaard>();
		Eten eet = null;
		int counter = -1;
		String line = br.readLine();
		while (line != null) {
			if (line.startsWith("Kl")) {
				klantnaam = line.substring(11);
			} else if (line.startsWith("Na")) {
				name = line.substring(6);
				if (name.contains("(A)")) {
					aanbieding = true;
					name = name.substring(3);
				} else
					aanbieding = false;
			} else if (line.startsWith("Pr")) {
				price = Integer.parseInt(line.substring(7));
			} else if (line.startsWith("Ex")) {
				eet = new Eten(name, 0, price);
				eet.setListSize(Integer.parseInt(line.substring(9)));
				GeselecteerdEtenBewaard eetKoos = new GeselecteerdEtenBewaard(eet);
				eetKoos.isAanbieding = aanbieding;
				etenNow.add(eetKoos);
				counter = -1;
			} else if (line.startsWith("be")) {
				name = line.substring(14);
				counter++;
			} else if (line.startsWith("pr")) {
				price = Integer.parseInt(line.substring(7));
				Exstra ex = new Exstra(name, price);
				etenNow.get(etenNow.size()-1).eten.addExstr(ex); 
			} else if (line.startsWith("ge")) {
				if (line.contains("nee")) {
					etenNow.get(etenNow.size()-1).selected[counter] = false;
				} else {
					etenNow.get(etenNow.size()-1).selected[counter] = true;
				} 
			} else if (line.startsWith("=")){
				klantenLijst.add(new Klant(klantnaam,etenNow));
				klantenLijst.get(klantenLijst.size() - 1).bestellingPrijs = Integer.parseInt(line.substring(15));
			}
			line = br.readLine();
		}
		br.close();

		return klantenLijst;

	}
	
	public int krijgKlantenNummer(String datum) throws IOException {
		String location = System.getProperty("user.dir") + "//Prijs Map" + "//Klanten Map";
		File klantenFile;
		klantenFile = new File(location + "//" + datum + ".txt");
		if (!klantenFile.exists())
			return 1;
		FileInputStream fis = new FileInputStream(klantenFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		int nummer = 1;
		String line = br.readLine();
		while (line != null) {
			if (line.startsWith("Kl")){
				int test;
				try {
					test = Integer.parseInt(line.substring(22).replace(" ", ""));
				} catch (IndexOutOfBoundsException e){
					test = 0;
				}
				if (test >= nummer)
				nummer = test + 1;
			}
			line = br.readLine();
		}
		br.close();
		return nummer;

	}

	public ArrayList<SearchResult> geefItems(String datum, String wat) throws IOException{
		ArrayList<SearchResult> items = new ArrayList<SearchResult>();
		String location = System.getProperty("user.dir") + "//Prijs Map" + "//Klanten Map";
		File klantenFile;
		klantenFile = new File(location + "//" + datum + ".txt");
		if (!klantenFile.exists())
			return new ArrayList<SearchResult>();
		FileInputStream fis = new FileInputStream(klantenFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = br.readLine();
		while (line != null) {
			if (line.startsWith(wat)){
				
				//CHECK IF ITS IN ALREADY
				String name = line.substring(line.indexOf(':')+2);
				int indexOf = isInList(items, name);
				if (indexOf != -1){
					items.get(indexOf).amount++;
				} else
					items.add(new SearchResult(name));
			}
			line = br.readLine();
		}
		br.close();
		return items;
	}
	
	
	private int isInList(ArrayList<SearchResult> items, String substring) {
		for (int i = 0; i < items.size(); i++){
			if (items.get(i).name.equals(substring))
				return i;
		}
		return -1;
	}

	public int totaalprijsGet(String datum){
		String location = System.getProperty("user.dir") + "//Prijs Map" + "//Klanten Map";
		File klantenFile;
		klantenFile = new File(location + "//" + datum + ".txt");
		if (!klantenFile.exists())
			return 0;
		FileInputStream fis;
		try {
			fis = new FileInputStream(klantenFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = br.readLine();
		while (line != null) {
			if (line.startsWith("TO")){
				
				//CHECK IF ITS IN ALREADY
				String name = line.substring(line.indexOf(':')+2);
				br.close();
				return Integer.parseInt(name);
				
			}
			line = br.readLine();
		}
		br.close();
		return 0;
		} catch (Exception e) {
		return 0;
		}
	}
	
	public void aanbiedingenNieuwFile(ArrayList<Eten> eten) throws IOException {

		String location = System.getProperty("user.dir") + "//Prijs Map";
		File aanbiedingenFile;
		try {
			aanbiedingenFile = new File(location + "//MenuAanBiedingenItems.txt");
			if (!aanbiedingenFile.exists())
				aanbiedingenFile.createNewFile();
		} catch (IOException e) {
			File folder = new File(location);
			folder.mkdir();
			aanbiedingenFile = new File(location + "//MenuAanBiedingenItems.txt");
			aanbiedingenFile.createNewFile();
			return;
		}

		if (aanbiedingenFile.delete())
			aanbiedingenFile.createNewFile();
		PrintWriter writer = new PrintWriter(aanbiedingenFile);

		for (int i = 0; i < eten.size(); i++) {
			Eten etenO = eten.get(i);
			if (etenO.nummer < 10)
				writer.println("0" + etenO.nummer);
			else
				writer.println(etenO.nummer);
			writer.println("Naam: " + etenO.naam);
			if (etenO.prijs < 1000) {
				if (etenO.prijs < 100) {
					if (etenO.prijs < 10) {
						writer.println("Prijs: 000" + etenO.prijs);
					} else {
						writer.println("Prijs: 00" + etenO.prijs);
					}
				} else {
					writer.println("Prijs: 0" + etenO.prijs);
				}
			} else
				writer.println("Prijs: " + etenO.prijs);
			if (etenO.exstras == null) {
				writer.println("Exstras: 0");
				continue;
			}
			writer.println("Exstras: " + etenO.exstras.length);
			for (int ii = 0; ii < etenO.exstras.length; ii++) {
				writer.println("beschrijving: " + etenO.exstras[ii].omschrijving);
				if (etenO.exstras[ii].prijs < 1000) {
					if (etenO.exstras[ii].prijs < 100) {
						if (etenO.exstras[ii].prijs < 10) {
							writer.println("prijs: 000" + etenO.exstras[ii].prijs);
						} else {
							writer.println("prijs: 00" + etenO.exstras[ii].prijs);
						}
					} else {
						writer.println("prijs: 0" + etenO.exstras[ii].prijs);
					}
				} else
					writer.println("prijs: " + etenO.exstras[ii].prijs);
			}
		}
		writer.close();
	}

	public ArrayList<Eten> aanbiedingenKrijg() throws IOException {
		ArrayList<Eten> etenLijst = new ArrayList<Eten>();
		String location = System.getProperty("user.dir") + "//Prijs Map";
		File aanbiedingenFile;
		try {
			aanbiedingenFile = new File(location + "//MenuAanBiedingenItems.txt");
			if (!aanbiedingenFile.exists())
				aanbiedingenFile.createNewFile();
		} catch (IOException e) {
			File folder = new File(location);
			folder.mkdir();
			aanbiedingenFile = new File(location + "//MenuAanBiedingenItems.txt");
			aanbiedingenFile.createNewFile();
			return new ArrayList<Eten>();
		}
		FileInputStream fis = new FileInputStream(aanbiedingenFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		int number = 0;
		String name = "";
		int price = 0;
		Eten now = null;

		String line = br.readLine();
		while (line != null) {
			if (line.length() == 2) {
				number = Integer.parseInt(line);
			} else if (line.startsWith("Na")) {
				name = line.substring(6);
			} else if (line.startsWith("Pr")) {
				price = Integer.parseInt(line.substring(7));
			} else if (line.startsWith("Ex")) {
				now = new Eten(name, number, price);
				etenLijst.add(now);
				now.setListSize(Integer.parseInt(line.substring(9)));
			} else if (line.startsWith("be")) {
				name = line.substring(14);
			} else if (line.startsWith("pr")) {
				price = Integer.parseInt(line.substring(7));
				Exstra ex = new Exstra(name, price);
				now.addExstr(ex);
			}
			line = br.readLine();
		}
		br.close();

		return etenLijst;

	}
}
