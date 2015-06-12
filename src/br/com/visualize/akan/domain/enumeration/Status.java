package br.com.visualize.akan.domain.enumeration;

public enum Status {
	ACTIVE(0),
	INACTIVE(1);
	
	int value;
	
	Status(int value){
		this.value = value;
	}
	
	int getValue(){
		return value;
	}
}
