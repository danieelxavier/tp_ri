import java.util.ArrayList;

public class Consultas {
	
	private String QueryNumber;
	private String Query;
	private String NR;
	private ArrayList<AvaliacaoConsulta> avaliacoes;
	public String getQueryNumber() {
		return QueryNumber;
	}
	public void setQueryNumber(String queryNumber) {
		QueryNumber = queryNumber;
	}
	public String getQuery() {
		return Query;
	}
	public void setQuery(String query) {
		Query = query;
	}
	public String getNR() {
		return NR;
	}
	public void setNR(String nR) {
		NR = nR;
	}
	public ArrayList<AvaliacaoConsulta> getAvaliacoes() {
		return avaliacoes;
	}
	public void setAvaliacoes(ArrayList<AvaliacaoConsulta> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}


	
}
