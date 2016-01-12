
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
	public static ArrayList<String> stopWords = new ArrayList<>();
	
	static FileWriter arq; 
	static PrintWriter gravarArq;
	
	static long tempoInicial;
	static long tempoAtual;
	static Double tempoResult;
	
	static Double mediaMAP;
	static Double mediaP;
	static Double mediaTempo;
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		tempoInicial = System.currentTimeMillis();
		
		String arg = args[0];
		
		if(!arg.endsWith("/"))
			arg = arg + "/";
		
		
		if (verificaDados(arg)){
			System.out.println("Extraindo dados ...");
			
			stopWords = Extrator.extrairStopWords();
			documentos = Extrator.extrairDocumentos(arg); //extrai os dados dos documentos do arquivo txt
			listaInvertidaDocumentos = Extrator.getListaInvertida(documentos, stopWords); //extrai todos os termos dos documentos, calculando TF e IDF
			consultas = Extrator.extrairConsultas(arg); //extrai os dados das consultas do arquivo txt
			
			tempoAtual = System.currentTimeMillis();
			System.out.println("Extração Finalizada em " + timeToSeconds(tempoAtual, tempoInicial) + " segundos.");
			
			arq = new FileWriter("resultados.txt");
			gravarArq = new PrintWriter(arq);
			
			gravarArq.printf("--------------------> RESULTADOS DAS CONSULTAS <--------------------\n\n\n");
			
			mediaMAP = 0.0;
			mediaP = 0.0;
			mediaTempo = 0.0;
			//executa as consultas
			for (Consulta c : consultas) {
				processa(c);
			}
			
			
			gravarArq.printf("\n\t\t Media MAP:\t" + mediaMAP/consultas.size());
			gravarArq.printf("\n\t\t Media P@10:\t" + mediaP/consultas.size());
			gravarArq.printf("\n\t\t Media Tempo:\t" + mediaTempo/consultas.size());
			
			tempoAtual = System.currentTimeMillis();
			gravarArq.printf("\n\n\t\t Processo finalizado em " + timeToSeconds(tempoAtual, tempoInicial) + " segundos.\n\n");
			arq.close();
			
			
			java.awt.Desktop.getDesktop().open( new File( "resultados.txt" ) );
			System.out.println("Finalizado");
			System.out.println("Processo finalizado em " + timeToSeconds(tempoAtual, tempoInicial) + " segundos.");
		}
		
		
	}
	
	
	static void processa(Consulta consulta){
		
		long tempo = System.currentTimeMillis();
		
		System.out.println("processando query " + consulta.getQueryNumber() + "...");
		gravarArq.printf("CONSULTA " + consulta.getQueryNumber());
		gravarArq.printf("\n");
		
		consulta.processaConsulta(listaInvertidaDocumentos, stopWords); //gera o ranking de similaridade para a consulta
		
		int y=1;
		for (Similaridade s : consulta.getRetornados()) {
			gravarArq.printf(y + ") " +s.getDocumento() + " - " + s.getSim());
			gravarArq.printf("\n");
			y++;
		}
		
		Double MAP = Medidas.MAP(consulta); //Calcula o MAP de uma consulta
		Double P10 = Medidas.precisaoN(consulta, 10); //Calcula a precição em N de uma consulta
		
		mediaMAP = mediaMAP + MAP;
		mediaP = mediaP + P10;
		
		gravarArq.printf("\n" + consulta.getRetornados().size() + " documentos retornados\n"); 
		gravarArq.printf("\nP@10 : " + P10); 
		gravarArq.printf("\nMAP : " + MAP); 
		
		tempoAtual = System.currentTimeMillis();
		
		Double tempoProcess = timeToSeconds(tempoAtual, tempo);		
		gravarArq.printf("\n\nConsulta " + consulta.getQueryNumber() + " realizada em " + tempoProcess + " segundos.");
		gravarArq.printf("\n\n------------------------------------------\n\n");
		
		mediaTempo = mediaTempo + tempoProcess;
	}
	
	static Double timeToSeconds(long a, long b){
		Double x  = (double) a;
		Double y  = (double) b;
		
		return (x - y)/1000;
	}
	
	static boolean verificaDados(String path){

		File file1 = new File(path);
		if(!file1.exists()){
			System.out.println("o diretório \'" + path + "\' não foi encontrado");
			return false;
		}
		
		for(int i=74; i < 80; i++){
			File f = new File(path + "cf" + i);
			if(!f.exists()){
				System.out.println("O arquivo \'cf" + i + "\' não foi encontrado no diretório \'" + path + "\'");
				return false;
			}
		}
		
		File file2 = new File(path + "cfquery");
		if(!file2.exists()){
			System.out.println("O arquivo \'cfquery\' não foi encontrado no diretório \'" + path + "\'");
			return false;
		}
		
		return true;
	}
	
}
