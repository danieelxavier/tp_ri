
import java.util.ArrayList;

public class TermoDocumentos {
	
	private String termo;
	private ArrayList<Frequencia> frequenciaDocumentos;
	private Double IDFDocumentos;
	
	public String getTermo() {
		return termo;
	}
	public void setTermo(String termo) {
		this.termo = termo;
	}
	public ArrayList<Frequencia> getFrequenciasDocumentos() {
		return frequenciaDocumentos;
	}
	public void setFrequenciasDocumentos(ArrayList<Frequencia> frequenciasDocumentos) {
		this.frequenciaDocumentos = frequenciasDocumentos;
	}
	public Double getIDFDocumentos() {
		return IDFDocumentos;
	}
	public void setIDFDocumentos(Double iDFDocumentos) {
		IDFDocumentos = iDFDocumentos;
	}
	
	
}
