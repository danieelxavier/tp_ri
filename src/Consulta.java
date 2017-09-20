
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class Consulta {
	
	private int QueryNumber;
	private String Query;
	private int NR;
	private ArrayList<AvaliacaoConsulta> avaliacoes;
	ArrayList<Double> vetorQuery;
	ArrayList<ArrayList<Frequencia>> vetorQueryTermos;
	ArrayList<Documento> documentos;
	private Double mediaDocumentos;
	
	private static HashMap<String, TermoConsultas> hashTermosConsulta;		
	private static ArrayList<ArrayList<Double>> vetoresDocumentos;
	private static ArrayList<Similaridade> similaridades;
	private static ArrayList<Similaridade> retornados;
	
	public int getQueryNumber() {
		return QueryNumber;
	}
	public void setQueryNumber(int queryNumber) {
		QueryNumber = queryNumber;
	}
	public String getQuery() {
		return Query;
	}
	public void setQuery(String query) {
		Query = query;
	}
	public ArrayList<AvaliacaoConsulta> getAvaliacoes() {
		return avaliacoes;
	}
	public void setAvaliacoes(ArrayList<AvaliacaoConsulta> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}
	public int getNR() {
		return NR;
	}
	public void setNR(int NR) {
		this.NR = NR;
	}
	public ArrayList<ArrayList<Double>> getVetoresDocumentos() {
		return vetoresDocumentos;
	}
	public void setVetoresDocumentos(ArrayList<ArrayList<Double>> vetoresDocumentos) {
		Consulta.vetoresDocumentos = vetoresDocumentos;
	}
	public  HashMap<String, TermoConsultas> getHashTermosConsulta() {
		return hashTermosConsulta;
	}
	public void setHashTermosConsulta(HashMap<String, TermoConsultas> hashTermosConsultas) {
		Consulta.hashTermosConsulta = hashTermosConsultas;
	}
	public ArrayList<Similaridade> getSimilaridades() {
		return similaridades;
	}
	public void setSimilaridades(ArrayList<Similaridade> similaridades) {
		Consulta.similaridades = similaridades;
	}
	public ArrayList<Similaridade> getRetornados() {
		return retornados;
	}
	public static void setRetornados(ArrayList<Similaridade> retornados) {
		Consulta.retornados = retornados;
	}

	
	//Método que faz o processamento das consultas gerando o ranking de similaridade
	public void processaConsulta(HashMap<String, TermoDocumentos> hashTermosDocumentos, ArrayList<String> stopWords, ArrayList<Documento> docs, Double md){
	
		Extrator.getTFConsulta(this, stopWords); //extrai os termos da consulta
		vetorQuery = new ArrayList<>();
		vetorQueryTermos = new ArrayList<>();
		vetoresDocumentos = new ArrayList<>();
		mediaDocumentos = md;
		documentos = docs;
		
		Iterator it = hashTermosConsulta.entrySet().iterator();
		
	    while (it.hasNext()) {
	        HashMap.Entry pairs = (HashMap.Entry)it.next();
   
	        TermoDocumentos t = hashTermosDocumentos.get(pairs.getKey());
	        
	        if(t != null){
	        	
		        Double idf = t.getIDFDocumentos();
		        
		        ArrayList<Frequencia> fr = t.getFrequenciasDocumentos();
		        	        
		        for (Frequencia frequencia : fr) {
		        	
		        	if(frequencia.getDocumento() > vetoresDocumentos.size()){
		        		ArrayList<Double> v = new ArrayList<>();
		        		v.add(frequencia.getFrequencia() * t.getIDFDocumentos());
		        		vetoresDocumentos.add(v);
		        	}
		        	else{
		        		vetoresDocumentos.get(frequencia.getDocumento()-1).add(frequencia.getFrequencia() * t.getIDFDocumentos());
		        	}
					
//		        	System.out.println(t.getTermo() + " - " + vetoresDocumentos.get(frequencia.getDocumento()-1).size());
				}
	        		        	
	        }
	        else{ //entra aqui quando algum termo na consulta não consta na lista invertida dos termos dos documentos
	        	
	        	for (ArrayList<Double> vd : vetoresDocumentos) {
					vd.add(0.0);
				}
	        }

	    }
			    
	    it = hashTermosConsulta.entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry pairs = (HashMap.Entry)it.next();
	        
	        
	        TermoConsultas t = (TermoConsultas) pairs.getValue();
	        
	        if(hashTermosDocumentos.get(t.getTermo()) == null){
	        	t.setIDFDocumentos(0.0);
	        }
	        else{
	        	t.setIDFDocumentos(hashTermosDocumentos.get(t.getTermo()).getIDFDocumentos());
	        }
	        
	        vetorQuery.add(t.getIDFDocumentos() * t.getFrequencia().getFrequencia());

	        TermoDocumentos tc = hashTermosDocumentos.get(pairs.getKey());
	        if(tc != null) {
	        		vetorQueryTermos.add(tc.getFrequenciasDocumentos());
	        }
	        else {
	        		vetorQueryTermos.add(null);
	        }
	    }
	    
//	    calcularSimilaridades(); //calcula a similaridade
	    calcularSimilaridades(); //calcula a similaridade
	    
	    retornados = new ArrayList<>();
	    
	    //gera uma lista apenas com os 10 documentos mais similares
	    
	    for (Similaridade s : similaridades) {
	    	if(s.getSim() > 0)
		    	retornados.add(s);
		}
	   	    
	}
	
	//Método que faz o processamento das consultas gerando o ranking de similaridade BM25
		public void processaConsultaBM25(HashMap<String, TermoDocumentos> hashTermosDocumentos, ArrayList<String> stopWords, ArrayList<Documento> docs, Double md){
		
			Extrator.getTFConsulta(this, stopWords); //extrai os termos da consulta
			vetorQuery = new ArrayList<>();
			vetorQueryTermos = new ArrayList<>();
			vetoresDocumentos = new ArrayList<>();
			mediaDocumentos = md;
			documentos = docs;
			
			Iterator it = hashTermosConsulta.entrySet().iterator();
			
		    while (it.hasNext()) {
		        HashMap.Entry pairs = (HashMap.Entry)it.next();
	   
		        TermoDocumentos t = hashTermosDocumentos.get(pairs.getKey());
		        
		        if(t != null){
		        	
			        Double idf = t.getIDFDocumentos();
			        
			        ArrayList<Frequencia> fr = t.getFrequenciasDocumentos();
			        	        
			        for (Frequencia frequencia : fr) {
			        	
			        	if(frequencia.getDocumento() > vetoresDocumentos.size()){
			        		ArrayList<Double> v = new ArrayList<>();
			        		v.add(frequencia.getFrequencia() * t.getIDFDocumentos());
			        		vetoresDocumentos.add(v);
			        	}
			        	else{
			        		vetoresDocumentos.get(frequencia.getDocumento()-1).add(frequencia.getFrequencia() * t.getIDFDocumentos());
			        	}
						
//			        	System.out.println(t.getTermo() + " - " + vetoresDocumentos.get(frequencia.getDocumento()-1).size());
					}
		        		        	
		        }
		        else{ //entra aqui quando algum termo na consulta não consta na lista invertida dos termos dos documentos
		        	
		        	for (ArrayList<Double> vd : vetoresDocumentos) {
						vd.add(0.0);
					}
		        }

		    }
				    
		    it = hashTermosConsulta.entrySet().iterator();
		    while (it.hasNext()) {
		        HashMap.Entry pairs = (HashMap.Entry)it.next();
		        
		        
		        TermoConsultas t = (TermoConsultas) pairs.getValue();
		        
		        if(hashTermosDocumentos.get(t.getTermo()) == null){
		        	t.setIDFDocumentos(0.0);
		        }
		        else{
		        	t.setIDFDocumentos(hashTermosDocumentos.get(t.getTermo()).getIDFDocumentos());
		        }
		        
		        vetorQuery.add(t.getIDFDocumentos() * t.getFrequencia().getFrequencia());

		        TermoDocumentos tc = hashTermosDocumentos.get(pairs.getKey());
		        if(tc != null) {
		        		vetorQueryTermos.add(tc.getFrequenciasDocumentos());
		        }
		        else {
		        		vetorQueryTermos.add(null);
		        }
		    }
		    
//		    calcularSimilaridades(); //calcula a similaridade
		    calcularSimilaridadesBM25(); //calcula a similaridade
		    
		    retornados = new ArrayList<>();
		    
		    //gera uma lista apenas com os 10 documentos mais similares
		    
		    for (Similaridade s : similaridades) {
		    	if(s.getSim() > 0)
			    	retornados.add(s);
			}
		   	    
		}
	
	//Método que calcula a similaridade entre a consulta e os documentos
	public void calcularSimilaridades(){
		
		similaridades = new ArrayList<>();
		

		int j = 1;
		for (ArrayList<Double> v : vetoresDocumentos) {
			
			Double A = (double) 0;
			Double B = (double) 0;
			Double C = (double) 0;
			
			for(int i = 0; i < vetorQuery.size(); i++){
				
				A = A + (vetorQuery.get(i) * v.get(i));
				B = B + (Math.pow(vetorQuery.get(i), 2));
				C = C + (Math.pow(v.get(i), 2));
			}
			
			B = Math.sqrt(B);
			C = Math.sqrt(C);
			
			Double sim = A / (B * C);
			
			Similaridade s = new Similaridade();
			
			if(sim.isNaN())
				s.setSim(0.0);
			else
				s.setSim(sim);
			s.setDocumento(j);
			similaridades.add(s);
			
			j++;
		}	

		//ordena os valores de similaridade
		Collections.sort(similaridades, Collections.reverseOrder(new Comparator<Similaridade>() {

			@Override
			public int compare(Similaridade o1, Similaridade o2) {
				// TODO Auto-generated method stub
				return o1.getSim().compareTo(o2.getSim());
			}
		}));
		
	}
	
	//Método que calcula a similaridade entre a consulta e os documentos
		public void calcularSimilaridadesBM25(){
			
			similaridades = new ArrayList<>();
			

			int j = 1;
			for (Documento v : documentos) {
				
				Double Fqd = 0.0;
				Double k1 = 1.7;
				Double b = 0.75;
				Double tamD = (double) v.getTamanho();
				
				Double score = 0.0;
				
				Double idf = 0.0;
				
				for(int i = 0; i < vetorQueryTermos.size(); i++){
					
					if(vetorQueryTermos.get(i) != null) {
						Fqd = (double) vetorQueryTermos.get(i).get(j-1).getFrequencia();
					}
					
					Double A = Fqd * (k1+1);
					Double B = 1 - b + (b * (tamD / mediaDocumentos));
					Double C = Fqd + (k1 * B);
					
//					System.out.println("FREQ => " + vetorQueryFreq.get(i));
//					System.out.println("FREQ => " + vetorQueryTermos.get(i).size());
					
					int ndq = 0;
					if(vetorQueryTermos.get(i) != null) {
						for(Frequencia f : vetorQueryTermos.get(i)) {
							if(f.getFrequencia() > 0)
								ndq++;
						}
					}
					
					idf = Math.log((documentos.size() - (double)ndq + 0.5) / (ndq + 0.5));
					
//					System.out.println("IDF => " + idf);
//					System.out.println("A => " + A);
//					System.out.println("Fqd => " + Fqd);
//					System.out.println("k1 => " + k1);
//					System.out.println("B => " + B);
					
					if(C != 0)
						score += idf * (A / C);
					
					
				}
				
				
				Similaridade s = new Similaridade();
				
				if(score == 0.0)
					s.setSim(0.0);
				else
					s.setSim(score);
				s.setDocumento(j);
				similaridades.add(s);
				
				j++;
			}	

			//ordena os valores de similaridade
			Collections.sort(similaridades, Collections.reverseOrder(new Comparator<Similaridade>() {

				@Override
				public int compare(Similaridade o1, Similaridade o2) {
					// TODO Auto-generated method stub
					return o1.getSim().compareTo(o2.getSim());
				}
			}));
			
		}
		
}

