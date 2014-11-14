package br.com.visualize.akan.domain.controller;

import br.com.visualize.akan.domain.model.Congressman;
import br.com.visualize.akan.domain.model.Deputy;

public class CongressmanFactory {
	
	public Congressman getCongressman(Congressman congressman ){
		
		if (congressman.toString() == null){
			return null;
		}
		
		if(congressman.toString() == "Congressman"){
			
			return new Deputy();
		}
		else{
			//Verify senator
		}
		return null;
		
		
		
	}

}
