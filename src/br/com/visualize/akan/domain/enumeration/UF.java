/*
 * File: 	UF.java 
 * Purpose: Brings the implementation of a enumeration to support
 * the development.
 */
package br.com.visualize.akan.domain.enumeration;


/**
 * This enumeration serves to support the development. Through this enumeration 
 * is possible to easily identify the federal units present in Brazil.
 */
public enum UF {
	WHIOUT_UF, // Represent the lack of a set federal unit.
	
	/* Northern Region. */
	AC, AM, RR, RO, AP, PA,
	
	/* Northeast Region. */
	MA, PI, CE, RN, PB, PE, AL, SE, BA,
	
	/* Southeast Region. */
	ES, RJ, SP, MG,
	
	/* Southern Region. */
	PR, SC, RS,
	
	/* Midwest Region. */
	MT, MS, TO, GO, DF;
	
	/**
	 * Does the description of the state-related acronym.
	 * 
	 * @return String The name of the State related to the acronym in question.
	 */
	public String getDescriptionUf() {
		String description = "";
		
		switch( this ) {
			case AC:
				description = "Acre";
				break;
			
			case AM:
				description = "Amazonas";
				break;
			
			case RR:
				description = "Roraima";
				break;
			
			case RO:
				description = "Rondônia";
				break;
			
			case AP:
				description = "Amapa";
				break;
			
			case PA:
				description = "Pará";
				break;
			
			case MA:
				description = "Maranhão";
				break;
			
			case PI:
				description = "Piauí";
				break;
			
			case CE:
				description = "Ceará";
				break;
			
			case RN:
				description = "Rio Grande do Norte";
				break;
			
			case PB:
				description = "Paraíba";
				break;
			
			case PE:
				description = "Pernambuco";
				break;
			
			case AL:
				description = "Alagoas";
				break;
			
			case SE:
				description = "Sergipe";
				break;
			
			case BA:
				description = "Bahia";
				break;
			
			case ES:
				description = "Espírito Santo";
				break;
			
			case RJ:
				description = "Rio de Janeiro";
				break;
			
			case SP:
				description = "São Paulo";
				break;
			
			case MG:
				description = "Minas Gerais";
				break;
			
			case PR:
				description = "Paraná";
				break;
			
			case SC:
				description = "Santa Catarina";
				break;
			
			case RS:
				description = "Rio Grande do Sul";
				break;
			
			case MT:
				description = "Mato Grosso";
				break;
			
			case MS:
				description = "Mato Grosso do Sul";
				break;
			
			case TO:
				description = "Tocantis";
				break;
			
			case GO:
				description = "Goiás";
				break;
			
			case DF:
				description = "Distrito Federal";
				break;
			
			default:
				description = "";
		}
		
		return description;
	}
}
