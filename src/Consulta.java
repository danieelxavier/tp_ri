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
	
	private static HashMap<String, TermoConsultas> hashTermosConsulta;		
	private static ArrayList<ArrayList<Double>> vetoresDocumentos;
	private static ArrayList<Similaridade> similaridades;
	
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
	public void setRecordNumber(int NR) {
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

	
	public void processaConsulta(HashMap<String, TermoDocumentos> hashTermosDocumentos){
	
		Extrator.getTFConsulta(this);
		vetorQuery = new ArrayList<>();
		vetoresDocumentos = new ArrayList<>();
		
	
		Iterator it = hashTermosConsulta.entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry pairs = (HashMap.Entry)it.next();
   
	        TermoDocumentos t = hashTermosDocumentos.get(pairs.getKey());
			
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
				
	        	//System.out.println(t.getTermo() + " - " + vetoresDocumentos.get(frequencia.getDocumento()-1).size());
			}
	        
	        
	    }
		
	    it = hashTermosConsulta.entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry pairs = (HashMap.Entry)it.next();
	        
	        TermoConsultas t = (TermoConsultas) pairs.getValue();
	        
	        t.setIDFDocumentos(hashTermosDocumentos.get(t.getTermo()).getIDFDocumentos());
	        
	        vetorQuery.add(t.getIDFDocumentos() * t.getFrequencia().getFrequencia());
	        
	    }
	    
	    calcularSimilaridades();
	}
	
	
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

		
		Collections.sort(similaridades, Collections.reverseOrder(new Comparator<Similaridade>() {

			@Override
			public int compare(Similaridade o1, Similaridade o2) {
				// TODO Auto-generated method stub
				return o1.getSim().compareTo(o2.getSim());
			}
		}));
		
	}
		
}

