import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Extrator {

	public static ArrayList<Documento> documentos = new ArrayList<>();
	
	public static ArrayList<Documento> extrairDocumentos(){
		
		FileInputStream stream;
		int i = 74;
		int ano = 1974;
		int c=0;
		while(i < 80){
			try {
				stream = new FileInputStream("/home/daniel/Área de Trabalho/cfc/cf" + i);
				InputStreamReader reader = new InputStreamReader(stream);
				BufferedReader br = new BufferedReader(reader);
				String linha = br.readLine();
				
				String scape = null;
				while(linha != null) {
						
					Documento doc = new Documento();

					if(linha != null && linha.startsWith("PN")){
						while (linha.startsWith("RN")) {
							linha = linha.replace("PN ", "");
							linha = linha.trim();
							System.out.println(linha);
							doc.setPaperNumber(linha);						
							
							linha = br.readLine();
						}
					}
					else if(scape.startsWith("PN")){
						scape = scape.replace("PN ", "");
						scape = scape.trim();
						System.out.println(scape);
						doc.setPaperNumber(scape);	
					}
					
					if(linha != null && linha.startsWith("RN")){
						while (!linha.startsWith("AN")) {
							linha = linha.replace("RN ", "");
							linha = linha.trim();
							System.out.println(linha);
							doc.setRecordNumber(linha);
							doc.setYear(ano);
						
							linha = br.readLine();
						}
					}
					if(linha != null && linha.startsWith("AN")){
						while (!linha.startsWith("AU")) {
							linha = linha.replace("AN ", "");
							linha = linha.trim();
							System.out.println(linha);
							doc.setMedlineAcessionNumber(linha);
						
							linha = br.readLine();
						}
					}
					if(linha != null && linha.startsWith("AU")){
						ArrayList<String> authors = new ArrayList<>();
						String aux = "";
						
						while (!linha.startsWith("TI")) {
							linha = linha.replace("AU ", "");
							linha = linha.trim();
							
							aux = aux + linha + "  ";
							
							linha = br.readLine();
						}

						System.out.println(aux);
						String a[] = aux.split("  ");
						for (String string : a) {
							authors.add(string);
						}
						doc.setAuthors(authors);
					}
					if(linha != null && linha.startsWith("TI")){
						String aux = "";
						
						while (!linha.startsWith("SO")) {
							linha = linha.replace("TI ", "");
							linha = linha.trim();
						
							aux = aux + linha + " ";
							
							linha = br.readLine();
						}
						
						System.out.println(aux);
						doc.setTitle(aux);
						
					}
					if(linha != null && linha.startsWith("SO")){
						while (depoisSO(linha)) {
							linha = linha.replace("SO ", "");
							linha = linha.trim();
							System.out.println(linha);
							doc.setSource(linha);
						
							linha = br.readLine();
						}
					}
					if(linha != null && linha.startsWith("MJ")){
						ArrayList<String> major = new ArrayList<>();
						String aux = "";
						
						while (depoisMJ(linha)) {
							linha = linha.replace("MJ ", "");
							linha = linha.trim();
							
							aux = aux + linha + "  ";
							
							linha = br.readLine();
						}
						

						System.out.println(aux);
						String a[] = aux.split("  ");
						for (String string : a) {
							major.add(string);
						}
						doc.setMajorSubjects(major);
					}
					if(linha != null && linha.startsWith("MN")){
						ArrayList<String> minor = new ArrayList<>();
						String aux = "";
						
						while (!linha.startsWith("AB") && !linha.startsWith("EX")) {
							linha = linha.replace("MN ", "");
							linha = linha.trim();

							aux = aux + linha + "  ";
						
							linha = br.readLine();
						}
						
						System.out.println(aux);
						String a[] = aux.split("  ");
						for (String string : a) {
							minor.add(string);
						}
						doc.setMinorSubjects(minor);
					}
					if(linha != null && linha.startsWith("AB") || linha.startsWith("EX")){
						String aux = "";
						
						while (depoisAB(linha)) {
							linha = linha.replace("AB ", "");
							linha = linha.replace("EX ", "");
							linha = linha.trim();
						
							aux = aux + linha + " ";
							
							linha = br.readLine();
						}

						System.out.println(aux);
						doc.setAbstract(aux);
					}
					if(linha != null && linha.startsWith("RF")){
						ArrayList<String> rf = new ArrayList<>();
						
						while (depoisRF(linha)) {
							linha = linha.replace("RF ", "");
							linha = linha.trim();
							System.out.println(linha);
							rf.add(linha);
							
							linha = br.readLine();
						}
						doc.setReferences(rf);
					}
					if(linha != null && linha.startsWith("CT")){
						ArrayList<String> ct = new ArrayList<>();
						
						while (linha != null && !linha.isEmpty()) {
							linha = linha.replace("CT ", "");
							linha = linha.trim();
							System.out.println(linha);
							ct.add(linha);
						
							linha = br.readLine();
						}
						doc.setCitations(ct);
					}
					
					if(doc.getPaperNumber() != null)
						documentos.add(doc);
					else
						c++;
					
					//System.out.println(" FIM - "+linha);
					scape = linha;
					linha = br.readLine();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
			ano++;
		}
		
		System.out.println(c);
		
		return documentos;
	}
	
	private static boolean depoisRF(String line){
		if(line != null && !line.startsWith("PN") && !line.startsWith("RN") && 
				!line.startsWith("AN") && !line.startsWith("AU") && !line.startsWith("TI") && 
				!line.startsWith("SO") && !line.startsWith("MJ") && !line.startsWith("MN") && 
				!line.startsWith("EX") && !line.startsWith("AB") && !line.startsWith("CT")){
			
			return true;
		}
		else{
			return false;
		}
	}
	
	private static boolean depoisSO(String line){
		if(line != null && !line.startsWith("PN") && !line.startsWith("RN") && 
				!line.startsWith("AN") && !line.startsWith("AU") && !line.startsWith("TI") && 
				!line.startsWith("RF") && !line.startsWith("MJ") && !line.startsWith("MN") && 
				!line.startsWith("EX") && !line.startsWith("AB") && !line.startsWith("CT")){
			
			return true;
		}
		else{
			return false;
		}
	}
	
	private static boolean depoisMJ(String line){
		if(line != null && !line.startsWith("PN") && !line.startsWith("RN") && 
				!line.startsWith("AN") && !line.startsWith("AU") && !line.startsWith("TI") && 
				!line.startsWith("RF") && !line.startsWith("SO") && !line.startsWith("MN") && 
				!line.startsWith("EX") && !line.startsWith("AB") && !line.startsWith("CT")){
			
			return true;
		}
		else{
			return false;
		}
	}
	
	private static boolean depoisAB(String line){
		if(line != null && !line.startsWith("PN") && !line.startsWith("RN") && 
				!line.startsWith("AN") && !line.startsWith("AU") && !line.startsWith("TI") && 
				!line.startsWith("RF") && !line.startsWith("SO") && !line.startsWith("MN") && 
				!line.startsWith("EX") && !line.startsWith("MJ") && !line.startsWith("CT")){
			
			return true;
		}
		else{
			return false;
		}
	}
	
	
}
