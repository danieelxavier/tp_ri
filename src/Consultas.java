import java.util.ArrayList;

public class Consultas {
	
	private int QueryNumber;
	private String Query;
	private int NR;
	private ArrayList<AvaliacaoConsulta> avaliacoes;
			
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

	
}
