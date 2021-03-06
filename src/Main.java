
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
	
	static Double mediaMAPV;
	static Double mediaPV;
	static Double mediaTempoV;
	
	static Double mediaMAPB;
	static Double mediaPB;
	static Double mediaTempoB;
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		tempoInicial = System.currentTimeMillis();
		
//		String arg = args[0];
		String arg = "cfc";
		
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
			
			int tamTotal = 0;
			for(Documento d : documentos) {
				tamTotal += d.getTamanho();
			}
			Double mediaDocumentos = (double) (tamTotal / documentos.size());
			
			arq = new FileWriter("resultados.txt");
			gravarArq = new PrintWriter(arq);
			
			
			gravarArq.printf("--------------------> RESULTADOS DAS CONSULTAS <--------------------\n\n\n");
			
			mediaMAPV = 0.0;
			mediaPV = 0.0;
			mediaTempoV = 0.0;
			
			mediaMAPB = 0.0;
			mediaPB = 0.0;
			mediaTempoB = 0.0;
			//executa as consultas
			for (Consulta c : consultas) {
				processa(c, mediaDocumentos, 1);
			}
//			processa(consultas.get(0), mediaDocumentos);
			
			for (Consulta c : consultas) {
				processa(c, mediaDocumentos, 2);
			}
			
			
			gravarArq.printf("\n\t\t Media MAP Vetorial:\t" + mediaMAPV/consultas.size());
			gravarArq.printf("\n\t\t Media P@10 Vetorial:\t" + mediaPV/consultas.size());
			gravarArq.printf("\n\t\t Consultas processadas em: %.3f segundos", mediaTempoV);
			gravarArq.printf("\n\t\t Media Tempo por consulta: %.3f segundos", mediaTempoV/consultas.size());
			

			gravarArq.printf("\n\n ------------------------------------------------------------------\n", mediaTempoB/consultas.size());
			
			gravarArq.printf("\n\t\t Media MAP BM25:\t" + mediaMAPB/consultas.size());
			gravarArq.printf("\n\t\t Media P@10 BM25:\t" + mediaPB/consultas.size());
			gravarArq.printf("\n\t\t Consultas processadas em: %.3f segundos", mediaTempoB);
			gravarArq.printf("\n\t\t Media Tempo por consulta: %.3f segundos", mediaTempoB/consultas.size());
			
			tempoAtual = System.currentTimeMillis();
			gravarArq.printf("\n\n\t\t Processo finalizado em " + timeToSeconds(tempoAtual, tempoInicial) + " segundos.\n\n");
			arq.close();
			
			
			java.awt.Desktop.getDesktop().open( new File( "resultados.txt" ) );
			System.out.println("Finalizado");
			System.out.println("Processo finalizado em " + timeToSeconds(tempoAtual, tempoInicial) + " segundos.");
		}
		
		
	}
	
	
	static void processa(Consulta consulta, Double mediaDocumentos, int tipo){
		
		long tempo = System.currentTimeMillis();
		
		System.out.println("processando query " + consulta.getQueryNumber() + "...");
		gravarArq.printf("CONSULTA " + consulta.getQueryNumber());
		gravarArq.printf("\n");
		
		if(tipo == 1) {
			consulta.processaConsulta(listaInvertidaDocumentos, stopWords, documentos, mediaDocumentos); //gera o ranking de similaridade para a consulta					
		}
		else {
			consulta.processaConsultaBM25(listaInvertidaDocumentos, stopWords, documentos, mediaDocumentos); //gera o ranking de similaridade para a consulta
		}
		
//		int y=1;
//		for (Similaridade s : consulta.getRetornados()) {
//			gravarArq.printf(y + ") " +s.getDocumento() + " - " + s.getSim());
//			gravarArq.printf("\n");
//			y++;
//		}
		
		Double MAP = Medidas.MAP(consulta); //Calcula o MAP de uma consulta
		Double P10 = Medidas.precisaoN(consulta, 10); //Calcula a precição em N de uma consulta
		
		if(tipo == 1) {
			mediaMAPV = mediaMAPV + MAP;
			mediaPV = mediaPV + P10;
		}
		else {
			mediaMAPB = mediaMAPB + MAP;
			mediaPB = mediaPB + P10;
		}
		
		gravarArq.printf("\n" + consulta.getRetornados().size() + " documentos retornados\n"); 
		gravarArq.printf("\nP@10 : " + P10); 
		gravarArq.printf("\nMAP : " + MAP); 
		
		tempoAtual = System.currentTimeMillis();
		
		Double tempoProcess = timeToSeconds(tempoAtual, tempo);		
		gravarArq.printf("\n\nConsulta " + consulta.getQueryNumber() + " realizada em " + tempoProcess + " segundos.");
		gravarArq.printf("\n\n------------------------------------------\n\n");
		
		if(tipo == 1)
			mediaTempoV = mediaTempoV + tempoProcess;
		else
			mediaTempoB = mediaTempoB + tempoProcess;
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
