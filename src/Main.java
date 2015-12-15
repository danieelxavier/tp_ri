import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static ArrayList<Documento> documentos = new ArrayList<>();
	public static ArrayList<Consultas> consultas = new ArrayList<>();
	public static HashMap<String, ArrayList<Frequencia>> hashFrequencias = new HashMap<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		documentos = Extrator.extrairDocumentos();
		consultas = Extrator.extrairConsultas();
		
//		System.out.println(documentos.size());
//		int o = 0;
//		for (Documento c : documentos) {
//
//			System.out.println(c.getRecordNumber() + " -> " + c.getAbstract());
//
//			o++;
//		}
//		
//		System.out.println(o);
		
			
		for (Documento d : documentos) {
			
			String aux;
			
			aux = d.getTitle();
			String[] parts = aux.split("[\\W]");
			
			
			for (int i = 0; i < parts.length; i++) {
				
				if(!parts[i].equals("")){
					
					if (hashFrequencias.get(parts[i]) == null){
						
						ArrayList<Frequencia> af = iniciaFrequencias();
						hashFrequencias.put(parts[i], af);
						hashFrequencias.get(parts[i]).get(d.getRecordNumber()-1).incrementaFrequencia();
					}
					else if(hashFrequencias.get(parts[i]) != null){
						hashFrequencias.get(parts[i]).get(d.getRecordNumber()-1).incrementaFrequencia();
						
					}
					
				}
			}
			
			aux = d.getAbstract();
			parts = aux.split("[\\W]");
			
			
			for (int i = 0; i < parts.length; i++) {
				
				if(!parts[i].equals("")){
					
					if (hashFrequencias.get(parts[i]) == null){
						
						ArrayList<Frequencia> af = iniciaFrequencias();
						hashFrequencias.put(parts[i], af);
						hashFrequencias.get(parts[i]).get(d.getRecordNumber()-1).incrementaFrequencia();
					}
					else{
						hashFrequencias.get(parts[i]).get(d.getRecordNumber()-1).incrementaFrequencia();
	
						System.out.println(hashFrequencias.get(parts[i]).get(d.getRecordNumber()-1).getDocumento()+" - " +
								hashFrequencias.get(parts[i]).get(d.getRecordNumber()-1).getFrequencia()
								+" --- "+parts[i] );
					}
				}
			}
			
			
		}
		
		
		
		
	}
	
	
	public static ArrayList<Frequencia> iniciaFrequencias(){
		
		ArrayList<Frequencia> af = new ArrayList<>();
		
		for (Documento d : documentos) {
					
			Frequencia f = new Frequencia();
			
			f.setDocumento(d.getRecordNumber());
			f.setFrequencia(0);
			
			af.add(f);
		}
		
		return af;
	}
	
	

}
