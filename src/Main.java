import java.util.ArrayList;

public class Main {

	public static ArrayList<Documento> documentos = new ArrayList<>();
	public static ArrayList<Consultas> consultas = new ArrayList<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		documentos = Extrator.extrairDocumentos();

		consultas = Extrator.extrairConsultas();
		
		System.out.println(consultas.size());
		int o = 0;
		for (Consultas c : consultas) {

			System.out.println(c.getQueryNumber() + " -> " + c.getQuery());

			o++;
		}
		
		System.out.println(o);
		
		
	}
	
	

}
