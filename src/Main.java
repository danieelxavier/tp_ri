import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Main {

	public static ArrayList<Documento> documentos = new ArrayList<>(); //arraylist que guarda todos os docmentos da coleção
	public static ArrayList<Consulta> consultas = new ArrayList<>();  //arraylist que guarda todas as consultas
	public static HashMap<String, TermoDocumentos> hashTermosDocumentos = new HashMap<>(); // map que guarda todos os termos, com seu TF pra cada dodumento e idf dos documentos
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		
		System.out.println("carregando...");
		
		documentos = Extrator.extrairDocumentos();
		consultas = Extrator.extrairConsultas();
		hashTermosDocumentos = Extrator.getTFDocumentos(documentos);

		System.out.println("Carregamento Finalizado");
		

		consultas.get(0).processaConsulta(hashTermosDocumentos);
		
		System.out.println(consultas.get(0).vetorQuery);
		
			
		for (int i = 0; i < consultas.get(0).getSimilaridades().size(); i++) {
			System.out.println(consultas.get(0).getSimilaridades().get(i).getDocumento() + " - " + consultas.get(0).getSimilaridades().get(i).getSim());
		//	System.out.println(i+1  + " - " + similaridades.get(i).getSimilaridade());
		}
		
		
//		int c=1;
//	    for (ArrayList<Double> vd : consultas.get(0).getVetoresDocumentos()) {
//			
//	    	System.out.println(c + " - " + vd);
//	    	c++;
//		}
	}
	
	
}
