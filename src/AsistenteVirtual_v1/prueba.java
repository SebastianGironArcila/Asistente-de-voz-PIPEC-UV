package AsistenteVirtual_v1;

import java.text.Normalizer;

public class prueba {
	
	public prueba() {
		System.out.println("entre");
		String prueba, resultado;
		prueba = "el 16 del 07 del 2020";
		resultado = CleanData(prueba);
		System.out.println(resultado);
	}
	
	  public String CleanData(String frase){
	        String cadena = frase;
	        cadena = cadena.replaceAll("\\D+","");
	        cadena = cadena.replaceAll("^\\s*","");  //quita espacio vacios del principio
	        cadena = cadena.replaceAll("\\s*$","");  //quita espacio vacios del final
	        cadena = cadena.toLowerCase();           // convierte todo a minisculas
	        cadena = Normalizer.normalize(cadena, Normalizer.Form.NFD);
	        cadena = cadena.replaceAll("[^\\p{ASCII}]", "");  //quita tildes 
	        
	        return cadena;
	        
	  }
	
	
	
	public static void main(String []args) {
		new prueba();
	
		
	}
	
	
	
	
	

}
