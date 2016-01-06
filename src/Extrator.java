
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Extrator {

	//Metodo que extrai os dados dos documentos
	public static ArrayList<Documento> extrairDocumentos(String path){
		
		ArrayList<Documento> documentos = new ArrayList<>();
		FileInputStream stream;
		int i = 74;
		int ano = 1974;
		int c=0;
		while(i < 80){
			try {
				stream = new FileInputStream(path + "cf" + i);
				InputStreamReader reader = new InputStreamReader(stream);
				BufferedReader br = new BufferedReader(reader);
				String linha = br.readLine();
				
				String scape = null;
				while(linha != null) {
						
					Documento doc = new Documento();
					
					//EXTRAI O PAGE NUMBER
					if(linha != null && linha.startsWith("PN")){
						linha = linha.replace("PN ", "");
						linha = linha.trim();
						linha = linha.toUpperCase();  
						doc.setPaperNumber(linha);						
						
						linha = br.readLine();
					}
					else if(scape.startsWith("PN")){
						scape = scape.replace("PN ", "");
						scape = scape.trim();
						linha = linha.toUpperCase();  
						doc.setPaperNumber(scape);	
					}
					
					//EXTRAI O RECORD NUMBER
					if(linha != null && linha.startsWith("RN")){
						linha = linha.replace("RN ", "");
						linha = linha.trim();
						linha = linha.toUpperCase();  
						doc.setRecordNumber(Integer.parseInt(linha));
						doc.setYear(ano);
					
						linha = br.readLine();
					}
					
					//EXTRAI O ACESS NUMBER
					if(linha != null && linha.startsWith("AN")){
						linha = linha.replace("AN ", "");
						linha = linha.trim();
						linha = linha.toUpperCase();  
						doc.setMedlineAcessionNumber(linha);
					
						linha = br.readLine();
					}
					
					//EXTRAI OS AUTORES
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

						String a[] = aux.split("  ");
						for (String string : a) {
							authors.add(string);
						}
						doc.setAuthors(authors);
					}
					
					//EXTRAI O TITULO
					if(linha != null && linha.startsWith("TI")){
						String aux = "";
						
						while (linha.startsWith("TI") || linha.startsWith(" ")) {
							linha = linha.replace("TI ", "");
							linha = linha.trim();
							linha = linha.toUpperCase();  
						
							aux = aux + linha + " ";
							
							linha = br.readLine();
						}
						
						doc.setTitle(aux);
						
					}
					
					//EXTRAI OS SOURCES
					if(linha != null && linha.startsWith("SO")){
						while (linha.startsWith("SO") || linha.startsWith(" ")) {
							linha = linha.replace("SO ", "");
							linha = linha.trim();
							linha = linha.toUpperCase();  
							doc.setSource(linha);
						
							linha = br.readLine();
						}
					}
					
					//EXTRAI OS MAJOR SUBJECTS
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
						
						String a[] = aux.split("  ");
						for (String string : a) {
							major.add(string);
						}
						doc.setMajorSubjects(major);
					}
					
					//EXTRAI OS MINOR SUBJECTS
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
						
						String a[] = aux.split("  ");
						for (String string : a) {
							minor.add(string);
						}
						doc.setMinorSubjects(minor);
					}
					
					//EXTRAI OS ABSTRACTS
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

						doc.setAbstract(aux);
					}
//					else if(!linha.startsWith(" ")){
//					}
					
					//EXTRAI AS REFERENCIAS
					if(linha != null && linha.startsWith("RF")){
						ArrayList<String> rf = new ArrayList<>();
						
						while (linha.startsWith("RF") || linha.startsWith(" ")) {
							linha = linha.replace("RF ", "");
							linha = linha.trim();
							linha = linha.toUpperCase();  
							rf.add(linha);
							
							linha = br.readLine();
						}
						doc.setReferences(rf);
					}
					
					//EXTRAI AS CITACOES
					if(linha != null && linha.startsWith("CT")){
						ArrayList<String> ct = new ArrayList<>();
						
						while (linha != null && !linha.isEmpty()) {
							linha = linha.replace("CT ", "");
							linha = linha.trim();
							linha = linha.toUpperCase();  
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
		
		return documentos;
	}
	
	
	//Metodo que extrai os dados das consultas	
	public static ArrayList<Consulta> extrairConsultas(String path){
		
		ArrayList<Consulta> consultas = new ArrayList<>();
		FileInputStream stream;

		try {
			stream = new FileInputStream(path + "cfquery");
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader br = new BufferedReader(reader);
			String linha = br.readLine();

			while (linha != null){
				
				Consulta consult = new Consulta();
				
				//EXTRAI OS QUERY NUMBERS
				if(linha != null && linha.startsWith("QN")){
					linha = linha.replace("QN ", "");
					linha = linha.trim();
					linha = linha.toUpperCase();  
					consult.setQueryNumber(Integer.parseInt(linha));
				
					linha = br.readLine();
				}
				
				//EXTRAI AS CONSULTAS
				if(linha != null && linha.startsWith("QU")){
					String aux = "";
					
					while (linha.startsWith("QU") || linha.startsWith(" ")) {
						linha = linha.replace("QU ", "");
						linha = linha.trim();
						linha = linha.toUpperCase();  
					
						aux = aux + linha + " ";
						
						linha = br.readLine();
					}

					consult.setQuery(aux);
				}
				
				//EXTRAI O NUMERO DE DOCUMENTOS RELEVANTES PARA A CONSULTA
				if(linha != null && linha.startsWith("NR")){
					linha = linha.replace("NR ", "");
					linha = linha.trim();
					linha = linha.toUpperCase();  
					consult.setNR(Integer.parseInt(linha));
					
					linha = br.readLine();
				}
				
				//EXTRAI AS AVALIAÇÕES DOS DOCUMENTOS (QUE SÃO OS RELEVANTES)
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
						}
						
						linha = br.readLine();
						linha = linha.trim();
					}
					
					consult.setAvaliacoes(arrayAvaliacoes);
				}
				

				if(consult.getQuery() != null)
					consultas.add(consult);
				
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
	
	
	//Método que extrai os termos, com seus TF e IDF
	public static HashMap<String, TermoDocumentos> getListaInvertida(ArrayList<Documento> documentos){
		
		HashMap<String, TermoDocumentos> hashTermos = new HashMap<>();
		
		for (Documento d : documentos) {
			
			String aux;
			
			
		// PEGA OS TERMOS DO TITULO---------------------------
			aux = d.getTitle();
			String[] parts = aux.split("[\\W]");
			
			
			for (int i = 0; i < parts.length; i++) {
				
				if(!parts[i].equals("")){
					
					if (hashTermos.get(parts[i]) == null){
						
						TermoDocumentos t = new TermoDocumentos();
						ArrayList<Frequencia> af = iniciaFrequenciasDocumentos(documentos);
						t.setFrequenciasDocumentos(af);
						t.setTermo(parts[i]);
						
						hashTermos.put(parts[i], t);
						hashTermos.get(parts[i]).getFrequenciasDocumentos().get(d.getRecordNumber()-1).incrementaFrequencia();
					}
					else if(hashTermos.get(parts[i]) != null){
						hashTermos.get(parts[i]).getFrequenciasDocumentos().get(d.getRecordNumber()-1).incrementaFrequencia();
						
					}
					
				}
			}
			
			
		// PEGA OS TERMOS DAS PALAVRAS CHAVE---------------------------
			
			ArrayList<String> auxMJ = d.getMajorSubjects();
			
			if(auxMJ != null){
				for (String string : auxMJ) {
					
					parts = string.split("[\\W]");
					

					for (int i = 0; i < parts.length; i++) {
						
						if(!parts[i].equals("")){
							
							if (hashTermos.get(parts[i]) == null){
								
								TermoDocumentos t = new TermoDocumentos();
								ArrayList<Frequencia> af = iniciaFrequenciasDocumentos(documentos);
								t.setFrequenciasDocumentos(af);
								t.setTermo(parts[i]);
								
								hashTermos.put(parts[i], t);
								hashTermos.get(parts[i]).getFrequenciasDocumentos().get(d.getRecordNumber()-1).incrementaFrequencia();
							}
							else if(hashTermos.get(parts[i]) != null){
								hashTermos.get(parts[i]).getFrequenciasDocumentos().get(d.getRecordNumber()-1).incrementaFrequencia();
							}
							
						}
					
					}
				}
			}
						
			
		// PEGA OS TERMOS DO ABSTRACT---------------------------
			aux = d.getAbstract();
			if(aux == null){
				aux = "";
			}
			parts = aux.split("[\\W]");
			
			
			for (int i = 0; i < parts.length; i++) {
				
				if(!parts[i].equals("")){
					
					if (hashTermos.get(parts[i]) == null){
						
						TermoDocumentos t = new TermoDocumentos();
						ArrayList<Frequencia> af = iniciaFrequenciasDocumentos(documentos);
						t.setFrequenciasDocumentos(af);
						t.setTermo(parts[i]);
						
						hashTermos.put(parts[i], t);
						hashTermos.get(parts[i]).getFrequenciasDocumentos().get(d.getRecordNumber()-1).incrementaFrequencia();
					}
					else{
						hashTermos.get(parts[i]).getFrequenciasDocumentos().get(d.getRecordNumber()-1).incrementaFrequencia();
					}
				}
			}	
		}

		calculaIDF(hashTermos, documentos.size());
		return hashTermos;
		
	}
	
	//método que calcula a frequencia de um termo em todas os documentos
	public static void getTFConsulta(Consulta consulta){
		
		HashMap<String, TermoConsultas> hashTermosConsultas = new HashMap<>();
				
		String aux;
		
		aux = consulta.getQuery();
		String[] parts = aux.split("[\\W]");
		
		
		for (int i = 0; i < parts.length; i++) {
			
			if(!parts[i].equals("")){
				
				if (hashTermosConsultas.get(parts[i]) == null){
					
					TermoConsultas t = new TermoConsultas();
					t.setTermo(parts[i]);
					
					Frequencia f = new Frequencia();							
					f.setDocumento(consulta.getQueryNumber());
					f.setFrequencia(0);
					
					t.setFrequencia(f);
					
					hashTermosConsultas.put(parts[i], t);
					hashTermosConsultas.get(parts[i]).getFrequencia().incrementaFrequencia();
				}
				else if(hashTermosConsultas.get(parts[i]) != null){
					hashTermosConsultas.get(parts[i]).getFrequencia().incrementaFrequencia();
								
				}
				
			}
		}
		
		consulta.setHashTermosConsulta(hashTermosConsultas);
		
	}
	
	//método que inicia o vetor de frequencias dos documentos para um termo
	private static ArrayList<Frequencia> iniciaFrequenciasDocumentos(ArrayList<Documento> documentos){
		
		ArrayList<Frequencia> af = new ArrayList<>();
		
		for (Documento d : documentos) {
					
			Frequencia f = new Frequencia();
			
			f.setDocumento(d.getRecordNumber());
			f.setFrequencia(0);
			
			af.add(f);
		}
		
		return af;
	}
	
	//método que calcula o IDF para os termos
	private static void calculaIDF(HashMap<String, TermoDocumentos> hashFrequencias, int numero_de_documentos){
		
		Iterator it = hashFrequencias.entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry pairs = (HashMap.Entry)it.next();
   
	        TermoDocumentos t = (TermoDocumentos) pairs.getValue();
	        
	        ArrayList<Frequencia> fr = t.getFrequenciasDocumentos();
	        	        
	        Double idf = Math.log10(numero_de_documentos/size(fr));
	        
	        t.setIDFDocumentos(idf);

	    }
		
	}
	
	public static int size(ArrayList<Frequencia> arrayFrequencias){
		int c = 0;
		
		for (Frequencia frequencia : arrayFrequencias) {
			if(frequencia.getFrequencia() > 0)
				c++;
		}
		
		return c;
	}
	
}
