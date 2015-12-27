import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Main {

	public static ArrayList<Documento> documentos = new ArrayList<>(); //arraylist que guarda todos os docmentos da coleção
	public static ArrayList<Consultas> consultas = new ArrayList<>();  //arraylist que guarda todas as consultas
	public static HashMap<String, ArrayList<Frequencia>> hashDocumentosFrequencias = new HashMap<>(); // map que guarda todos os termos e informa quantas vezes ocorre em cada documento
	public static HashMap<String, Double> hashDocumentosIDF = new HashMap<>(); // map que guarda todos os termos e informa o IDF
	public static HashMap<String, ArrayList<Frequencia>> hashConsultasFrequencias = new HashMap<>();
	public static HashMap<String, Double> hashConsultasIDF = new HashMap<>(); 
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		
		System.out.println("carregando...");
		
		documentos = Extrator.extrairDocumentos();
		consultas = Extrator.extrairConsultas();
		hashDocumentosFrequencias = Extrator.getTFDocumentos(documentos);
		hashDocumentosIDF = Extrator.calculaIDF(hashDocumentosFrequencias, documentos.size());
		hashConsultasFrequencias = Extrator.getTFConsultas(consultas);
		hashConsultasIDF = Extrator.calculaIDF(hashConsultasFrequencias, consultas.size());
		

		System.out.println("Carregamento Finalizado");
		
		
//		System.out.println(documentos.size());
//		int o = 0;
//		for (Documento c : documentos) {
//
//			System.out.println(c.getRecordNumber() + " -> " + c.getAbstract());
//
//			o++;
//		}
//		
//		System.out.println(o);
		
			
		
		
		
		
	}
	
	
}
