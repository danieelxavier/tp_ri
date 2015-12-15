import java.util.ArrayList;

public class Consultas {
	
	private String QueryNumber;
	private String Query;
	private int RecordNumber;
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

	public ArrayList<AvaliacaoConsulta> getAvaliacoes() {
		return avaliacoes;
	}
	public void setAvaliacoes(ArrayList<AvaliacaoConsulta> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}
	public int getRecordNumber() {
		return RecordNumber;
	}
	public void setRecordNumber(int recordNumber) {
		RecordNumber = recordNumber;
	}


	
}
