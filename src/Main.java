import java.util.ArrayList;

public class Main {

	public static ArrayList<Documento> documentos = new ArrayList<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		documentos = Extrator.extrairDocumentos();
		
		System.out.println(documentos.size());
		
		int o = 0;
		for (Documento d : documentos) {
			

				System.out.println(d.getPaperNumber() + " -> " + d.getAbstract());

			o++;
		}
		
		System.out.println(o);

		
	}
	
	

}
