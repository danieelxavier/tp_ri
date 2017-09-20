
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
						
						//System.out.println(aux);
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
	
	//Método de extracao das stopwords do txt
	public static ArrayList<String> extrairStopWords()
	{
		ArrayList<String> s = getStopWords();
		ArrayList<String> stop = new ArrayList<>();
		
		for (String string : s) {
			stop.add(string.toUpperCase());  
		}
		
		return stop;
		
	}
	
	
	//Método que extrai os termos, com seus TF e IDF
	public static HashMap<String, TermoDocumentos> getListaInvertida(ArrayList<Documento> documentos, ArrayList<String> stopWords){
		
		HashMap<String, TermoDocumentos> hashTermos = new HashMap<>();
		
		for (Documento d : documentos) {
			
			String aux;
			
			
		// PEGA OS TERMOS DO TITULO---------------------------
			aux = d.getTitle();
			String[] parts = aux.split("[\\W]");
			
			
			for (int i = 0; i < parts.length; i++) {
				
				if(!parts[i].equals("") && !isStopWord(parts[i], stopWords)){
					d.incrementaTamanho();
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
			
			
//		// PEGA OS TERMOS DAS PALAVRAS CHAVE---------------------------
//			
//			ArrayList<String> auxMJ = d.getMajorSubjects();
//			
//			if(auxMJ != null){
//				for (String string : auxMJ) {
//					
//					parts = string.split("[\\W]");
//					
//
//					for (int i = 0; i < parts.length; i++) {
//						
//						if(!parts[i].equals("") && !isStopWord(parts[i], stopWords)){
//							
//							if (hashTermos.get(parts[i]) == null){
//								
//								TermoDocumentos t = new TermoDocumentos();
//								ArrayList<Frequencia> af = iniciaFrequenciasDocumentos(documentos);
//								t.setFrequenciasDocumentos(af);
//								t.setTermo(parts[i]);
//								
//								hashTermos.put(parts[i], t);
//								hashTermos.get(parts[i]).getFrequenciasDocumentos().get(d.getRecordNumber()-1).incrementaFrequencia();
//							}
//							else if(hashTermos.get(parts[i]) != null){
//								hashTermos.get(parts[i]).getFrequenciasDocumentos().get(d.getRecordNumber()-1).incrementaFrequencia();
//							}
//							
//						}
//					
//					}
//				}
//			}
//						
			
		// PEGA OS TERMOS DO ABSTRACT---------------------------
			aux = d.getAbstract();
			if(aux == null){
				aux = "";
			}
			parts = aux.split("[\\W]");
			
			
			for (int i = 0; i < parts.length; i++) {
				
				if(!parts[i].equals("") && !isStopWord(parts[i], stopWords)){
					d.incrementaTamanho();
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
	public static void getTFConsulta(Consulta consulta, ArrayList<String> stopWords){
		
		HashMap<String, TermoConsultas> hashTermosConsultas = new HashMap<>();
				
		String aux;
		
		aux = consulta.getQuery();
		String[] parts = aux.split("[\\W]");
		
		
		for (int i = 0; i < parts.length; i++) {
			
			if(!parts[i].equals("") && !isStopWord(parts[i], stopWords)){
				
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
	
	//método que calcula o IDF para os termos
		private static void calculaIDFBM25(HashMap<String, TermoDocumentos> hashFrequencias, int numero_de_documentos){
			
			Iterator it = hashFrequencias.entrySet().iterator();
		    while (it.hasNext()) {
		        HashMap.Entry pairs = (HashMap.Entry)it.next();
	   
		        TermoDocumentos t = (TermoDocumentos) pairs.getValue();
		        
		        ArrayList<Frequencia> fr = t.getFrequenciasDocumentos();
		        	        
		        Double idf = Math.log10((numero_de_documentos - size(fr) + 0.5) / size(fr) );
		        
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
	
	public static boolean isStopWord(String word, ArrayList<String> stopWords){
		for (String s : stopWords) {
			if(word.equals(s)){
				return true;
			}
		}
		
		return false;
	}
	
	
	public static ArrayList<String> getStopWords(){
		
		ArrayList<String> s = new ArrayList<>(Arrays.asList(new String[]{"a's","able","about","above","according","accordingly","across","actually","after","afterwards","again","against","ain't","all","allow","allows","almost","alone","along","already","also","although","always","am","among","amongst","an","and","another","any","anybody","anyhow","anyone","anything","anyway","anyways","anywhere","apart","appear","appreciate","appropriate","are","aren't","around","as","aside","ask","asking","associated","at","available","away","awfully","be","became","because","become","becomes","becoming","been","before","beforehand","behind","being","believe","below","beside","besides","best","better","between","beyond","both","brief","but","by","c'mon","c's","came","can","can't","cannot","cant","cause","causes","certain","certainly","changes","clearly","co","com","come","comes","concerning","consequently","consider","considering","contain","containing","contains","corresponding","could","couldn't","course","currently","definitely","described","despite","did","didn't","different","do","does","doesn't","doing","don't","done","down","downwards","during","each","edu","eg","eight","either","else","elsewhere","enough","entirely","especially","et","etc","even","ever","every","everybody","everyone","everything","everywhere","ex","exactly","example","except","far","few","fifth","first","five","followed","following","follows","for","former","formerly","forth","four","from","further","furthermore","get","gets","getting","given","gives","go","goes","going","gone","got","gotten","greetings","had","hadn't","happens","hardly","has","hasn't","have","haven't","having","he","he's","hello","help","hence","her","here","here's","hereafter","hereby","herein","hereupon","hers","herself","hi","him","himself","his","hither","hopefully","how","howbeit","however","i'd","i'll","i'm","i've","ie","if","ignored","immediate","in","inasmuch","inc","indeed","indicate","indicated","indicates","inner","insofar","instead","into","inward","is","isn't","it","it'd","it'll","it's","its","itself","just","keep","keeps","kept","know","knows","known","last","lately","later","latter","latterly","least","less","lest","let","let's","like","liked","likely","little","look","looking","looks","ltd","mainly","many","may","maybe","me","mean","meanwhile","merely","might","more","moreover","most","mostly","much","must","my","myself","name","namely","nd","near","nearly","necessary","need","needs","neither","never","nevertheless","new","next","nine","no","nobody","non","none","noone","nor","normally","not","nothing","novel","now","nowhere","obviously","of","off","often","oh","ok","okay","old","on","once","one","ones","only","onto","or","other","others","otherwise","ought","our","ours","ourselves","out","outside","over","overall","own","particular","particularly","per","perhaps","placed","please","plus","possible","presumably","probably","provides","que","quite","qv","rather","rd","re","really","reasonably","regarding","regardless","regards","relatively","respectively","right","said","same","saw","say","saying","says","second","secondly","see","seeing","seem","seemed","seeming","seems","seen","self","selves","sensible","sent","serious","seriously","seven","several","shall","she","should","shouldn't","since","six","so","some","somebody","somehow","someone","something","sometime","sometimes","somewhat","somewhere","soon","sorry","specified","specify","specifying","still","sub","such","sup","sure","t's","take","taken","tell","tends","th","than","thank","thanks","thanx","that","that's","thats","the","their","theirs","them","themselves","then","thence","there","there's","thereafter","thereby","therefore","therein","theres","thereupon","these","they","they'd","they'll","they're","they've","think","third","this","thorough","thoroughly","those","though","three","through","throughout","thru","thus","to","together","too","took","toward","towards","tried","tries","truly","try","trying","twice","two","un","under","unfortunately","unless","unlikely","until","unto","up","upon","us","use","used","useful","uses","using","usually","value","various","very","via","viz","vs","want","wants","was","wasn't","way","we","we'd","we'll","we're","we've","welcome","well","went","were","weren't","what","what's","whatever","when","whence","whenever","where","where's","whereafter","whereas","whereby","wherein","whereupon","wherever","whether","which","while","whither","who","who's","whoever","whole","whom","whose","why","will","willing","wish","with","within","without","won't","wonder","would","would","wouldn't","yes","yet","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves","zero"}));
						
		return s;
	}
}
