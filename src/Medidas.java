
import java.util.ArrayList;

public class Medidas {
	
	//Método que calcula a precisão no ponto N
	public static Double precisaoN(Consulta consulta, int N){
		
		int c = relevantesRetornados(consulta, N);
		
		return (double)c/N;
	
	}
	
	//Método que calcula o MAP
	public static Double MAP(Consulta consulta){
		
		Double ap = 0.0;
		int qtd = 0;
		
		ArrayList<Similaridade> similaridades = consulta.getSimilaridades();
		
		for (int i = 0; i < similaridades.size(); i++) {
			if(isRelevante(consulta, similaridades.get(i).getDocumento())){
				qtd++;
				ap = ap + precisaoN(consulta, i+1);
			}
		}
				
		ap = ap/qtd;
		
		return ap;
	}
	
	//Método que retorna o número de documentos relevantes em um ponto N do ranking de similaridades
	public static int relevantesRetornados(Consulta consulta, int N){
		
		int c = 0;
		for (int i = 0; i < N; i++) {
			if(isRelevante(consulta, consulta.getSimilaridades().get(i).getDocumento())){
					c++;
			}
		}
		return c;
	}
	
	//Método que verifica se um documento é relevante para uma consulta
	public static boolean isRelevante(Consulta consulta, int documento){

		for (AvaliacaoConsulta ac : consulta.getAvaliacoes()) {
			
			if(ac.getRecordNumber() == documento){
				return true;
			}
		}
		return false;
	}
	
}
