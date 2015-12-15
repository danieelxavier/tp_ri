import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Extrator {

	public static ArrayList<Documento> documentos = new ArrayList<>();
	public static ArrayList<Consultas> consultas = new ArrayList<>();
	
	public static ArrayList<Documento> extrairDocumentos(){
		
		FileInputStream stream;
		int i = 74;
		int ano = 1974;
		int c=0;
		while(i < 80){
			try {
				stream = new FileInputStream("assets/cfc/cf" + i);
				InputStreamReader reader = new InputStreamReader(stream);
				BufferedReader br = new BufferedReader(reader);
				String linha = br.readLine();
				
				String scape = null;
				while(linha != null) {
						
					Documento doc = new Documento();

					if(linha != null && linha.startsWith("PN")){
						linha = linha.replace("PN ", "");
						linha = linha.trim();
						linha = linha.toUpperCase();  
				//		System.out.println(linha);
						doc.setPaperNumber(linha);						
						
						linha = br.readLine();
					}
					else if(scape.startsWith("PN")){
						scape = scape.replace("PN ", "");
						scape = scape.trim();
						linha = linha.toUpperCase();  
				//		System.out.println(scape);
						doc.setPaperNumber(scape);	
					}
					
					if(linha != null && linha.startsWith("RN")){
						linha = linha.replace("RN ", "");
						linha = linha.trim();
						linha = linha.toUpperCase();  
				//		System.out.println(linha);
						doc.setRecordNumber(Integer.parseInt(linha));
						doc.setYear(ano);
					
						linha = br.readLine();
					}
					if(linha != null && linha.startsWith("AN")){
						linha = linha.replace("AN ", "");
						linha = linha.trim();
						linha = linha.toUpperCase();  
				//		System.out.println(linha);
						doc.setMedlineAcessionNumber(linha);
					
						linha = br.readLine();
					}
					if(linha != null && linha.startsWith("AU")){
						ArrayList<String> authors = new ArrayList<>();
						String aux = "";
						
						while (linha.startsWith("AU") || linha.startsWith(" ")) {
							linha = linha.replace("AU ", "");
							linha = linha.trim();
							linha = linha.toUpperCase();  
							
							aux = aux + linha + "  ";
							
							linha = br.readLine();
						}

			//			System.out.println(aux);
						String a[] = aux.split("  ");
						for (String string : a) {
							authors.add(string);
						}
						doc.setAuthors(authors);
					}
					if(linha != null && linha.startsWith("TI")){
						String aux = "";
						
						while (linha.startsWith("TI") || linha.startsWith(" ")) {
							linha = linha.replace("TI ", "");
							linha = linha.trim();
							linha = linha.toUpperCase();  
						
							aux = aux + linha + " ";
							
							linha = br.readLine();
						}
						
				//		System.out.println(aux);
						doc.setTitle(aux);
						
					}
					if(linha != null && linha.startsWith("SO")){
						while (linha.startsWith("SO") || linha.startsWith(" ")) {
							linha = linha.replace("SO ", "");
							linha = linha.trim();
							linha = linha.toUpperCase();  
				//			System.out.println(linha);
							doc.setSource(linha);
						
							linha = br.readLine();
						}
					}
					if(linha != null && linha.startsWith("MJ")){
						ArrayList<String> major = new ArrayList<>();
						String aux = "";
						
						while (linha.startsWith("MJ") || linha.startsWith(" ")) {
							linha = linha.replace("MJ ", "");
							linha = linha.trim();
							linha = linha.toUpperCase();  
							
							aux = aux + linha + "  ";
							
							linha = br.readLine();
						}
						

			//			System.out.println(aux);
						String a[] = aux.split("  ");
						for (String string : a) {
							major.add(string);
						}
						doc.setMajorSubjects(major);
					}
					if(linha != null && linha.startsWith("MN")){
						ArrayList<String> minor = new ArrayList<>();
						String aux = "";
						
						while (linha.startsWith("MN") || linha.startsWith(" ")) {
							linha = linha.replace("MN ", "");
							linha = linha.trim();
							linha = linha.toUpperCase();  

							aux = aux + linha + "  ";
						
							linha = br.readLine();
						}
						
			//			System.out.println(aux);
						String a[] = aux.split("  ");
						for (String string : a) {
							minor.add(string);
						}
						doc.setMinorSubjects(minor);
					}
					if(linha != null && linha.startsWith("AB") || linha.startsWith("EX")){
						String aux = "";
						
						while (linha.startsWith("AB") || linha.startsWith("EX") || linha.startsWith(" ")) {
							linha = linha.replace("AB ", "");
							linha = linha.replace("EX ", "");
							linha = linha.trim();
							linha = linha.toUpperCase();  
						
							aux = aux + linha + " ";
							
							linha = br.readLine();
						}

			//			System.out.println(aux);
						doc.setAbstract(aux);
					}
					else if(!linha.startsWith(" ")){
				//		System.out.println(doc.getPaperNumber() +" --- "+linha);
					}
					
					if(linha != null && linha.startsWith("RF")){
						ArrayList<String> rf = new ArrayList<>();
						
						while (linha.startsWith("RF") || linha.startsWith(" ")) {
							linha = linha.replace("RF ", "");
							linha = linha.trim();
							linha = linha.toUpperCase();  
			//				System.out.println(linha);
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
							linha = linha.toUpperCase();  
			//				System.out.println(linha);
							ct.add(linha);
						
							linha = br.readLine();
						}
						doc.setCitations(ct);
					}
					
					if(doc.getPaperNumber() != null)
						documentos.add(doc);
					else
						c++;
					
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
	
	
	
	
	public static ArrayList<Consultas> extrairConsultas(){
		
		FileInputStream stream;

		try {
			stream = new FileInputStream("assets/cfc/cfquery");
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader br = new BufferedReader(reader);
			String linha = br.readLine();

			while (linha != null){
				
				Consultas consult = new Consultas();
				
				if(linha != null && linha.startsWith("QN")){
					linha = linha.replace("QN ", "");
					linha = linha.trim();
					linha = linha.toUpperCase();  
			//		System.out.println(linha);
					consult.setQueryNumber(linha);
				
					linha = br.readLine();
				}
				
				if(linha != null && linha.startsWith("QU")){
					String aux = "";
					
					while (linha.startsWith("QU") || linha.startsWith(" ")) {
						linha = linha.replace("QU ", "");
						linha = linha.trim();
						linha = linha.toUpperCase();  
					
						aux = aux + linha + " ";
						
						linha = br.readLine();
					}

		//			System.out.println(consult.getQueryNumber() + " - " + aux);
					consult.setQuery(aux);
				}
				
				if(linha != null && linha.startsWith("NR")){
					linha = linha.replace("NR ", "");
					linha = linha.trim();
					linha = linha.toUpperCase();  
			//		System.out.println(linha);
					consult.setRecordNumber(Integer.parseInt(linha));
					
					linha = br.readLine();
				}
				
				if(linha != null && linha.startsWith("RD")){
					ArrayList<AvaliacaoConsulta> arrayAvaliacoes = new ArrayList<>();
					
					while(linha != null && !linha.isEmpty()){
						linha = linha.replace("RD ", "");
						linha = linha.replace("  ", " ");
						linha = linha.replace("  ", " ");
						linha = linha.replace("  ", " ");
						linha = linha.replace("  ", " ");
						linha = linha.trim();
						linha = linha.toUpperCase();  
			
						String array[] = linha.split(" ");
						
						for (int i = 0; i < array.length; i = i + 2) {
							
							AvaliacaoConsulta ac = new AvaliacaoConsulta();
							
							char[] a = array[i+1].toCharArray();
							int[] avaliacoes = new int[4];
							

							for (int j = 0; j < a.length ; j++) {
								avaliacoes[j] = (int) a[j];
							}
							
							ac.setRecornNumber(Integer.parseInt(array[i]));
							ac.setQueryNumber(consult.getQueryNumber());
							ac.setAvaliacoes(avaliacoes);
							
							arrayAvaliacoes.add(ac);

			//				System.out.println(array[i] + " - " + array[i+1]);
						}
						
						linha = br.readLine();
						linha = linha.trim();
					}
					
					consult.setAvaliacoes(arrayAvaliacoes);
				}
				

				if(consult.getQueryNumber() != null)
					consultas.add(consult);
				else
		//			System.out.println("FAIL");
				
				linha = br.readLine();
				
			}
			

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return consultas;
		
	}
}
