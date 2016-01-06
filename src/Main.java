
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static ArrayList<Documento> documentos = new ArrayList<>(); //arraylist que guarda todos os docmentos da coleção
	public static ArrayList<Consulta> consultas = new ArrayList<>();  //arraylist que guarda todas as consultas
	public static HashMap<String, TermoDocumentos> listaInvertidaDocumentos = new HashMap<>(); // map que guarda todos os termos, com seu TF pra cada dodumento e idf dos documentos
	
	static FileWriter arq; 
	static PrintWriter gravarArq;
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		System.out.println("Extraindo dados ...");
		
		String arg = args[0];
		
		if(!arg.endsWith("/"))
			arg = arg + "/";
		

		documentos = Extrator.extrairDocumentos(arg); //extrai os dados dos documentos do arquivo txt
		consultas = Extrator.extrairConsultas(arg); //extrai os dados das consultas do arquivo txt
		listaInvertidaDocumentos = Extrator.getListaInvertida(documentos); //extrai todos os termos dos documentos, calculando TF e IDF
		
		System.out.println("Extração Finalizada");
		
		arq = new FileWriter("resultados.txt");
		gravarArq = new PrintWriter(arq);
		
		gravarArq.printf("--------------------> RESULTADOS DAS CONSULTAS <--------------------\n\n\n");
		
		//executa as consultas
		for (Consulta c : consultas) {
			processa(c);
		}
		
		
		arq.close();
		
		
		java.awt.Desktop.getDesktop().open( new File( "resultados.txt" ) );
		System.out.println("Finalizado");
		
		
	}
	
	
	static void processa(Consulta consulta){
		
		System.out.println("processando query " + consulta.getQueryNumber() + "...");
		gravarArq.printf("Consulta " + consulta.getQueryNumber());
		gravarArq.printf("\n");
		
		consulta.processaConsulta(listaInvertidaDocumentos); //gera o ranking de similaridade para a consulta
		
		int y=1;
		for (Similaridade s : consulta.getRetornados()) {
			gravarArq.printf(y + ") " +s.getDocumento() + " - " + s.getSim());
			gravarArq.printf("\n");
			y++;
		}
		
		gravarArq.printf("\nP@10 : " + Medidas.precisaoN(consulta, 10)); //Calcula a precição em N de uma consulta
		gravarArq.printf("\nMAP : " + Medidas.MAP(consulta)); //Calcula o MAP de uma consulta
		gravarArq.printf("\n\n------------------------------------------\n\n");
	}
	
	
}
