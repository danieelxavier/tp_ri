
public class Frequencia {

	private int documento; //Recorn Number ou Query Number
	private int frequencia; //TF
	
	public void incrementaFrequencia(){
		this.frequencia++;
	}
	
	public int getDocumento() {
		return documento;
	}
	public void setDocumento(int documento) {
		this.documento = documento;
	}
	public int getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(int frequencia) {
		this.frequencia = frequencia;
	}
	
}
