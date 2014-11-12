/*
 * File:		Congressman.java 
 * Purpose: Bringing the implementation of model Congressman, a class that 
 * directly references the business domain.
 */
package br.com.visualize.akan.domain.model;


import java.util.List;


/**
 * This class represents the model for a congressman in application. They 
 * contain the essential business-related Congressman data such as name, party 
 * and total expenditures by using quotas.
 */
public abstract class Congressman {
	static final boolean FOLLOWED = true;
	static final boolean NOT_FOLLOWED = false;
	
	protected int id = 0;
	protected int idUpdateCongressman = 0;
	protected boolean statusCogressman = NOT_FOLLOWED;
	protected byte[] photoCongressman = null;
	
	protected String nameCongressman = "";
	protected String partyCongressman = "";
	protected String ufCongressman = "";
	protected int ranking = 0; 
	protected List<Quota> quotasCongressman = null;
	protected double totalSpentCongressman = 0;
	
	public int getIdCongressman() {
		return id;
	}
	
	public void setIdCongressman( int idCongressman ) {
		this.id = idCongressman;
	}
	
	public int getIdUpdateCongressman() {
		return idUpdateCongressman;
	}
	
	public void setIdUpdateCongressman( int idUpdateCongressman ) {
		this.idUpdateCongressman = idUpdateCongressman;
	}
	
	public boolean isStatusCogressman() {
		return statusCogressman;
	}
	
	public void setStatusCogressman( boolean statusCogressman ) {
		this.statusCogressman = statusCogressman;
	}
	
	public byte[] getPhotoCongressman() {
		return photoCongressman;
	}
	
	public void setPhotoCongressman( byte[] photoCongressman ) {
		this.photoCongressman = photoCongressman;
	}
	
	public String getNameCongressman() {
		return nameCongressman;
	}
	
	public void setNameCongressman( String nameCongressman ) {
		this.nameCongressman = nameCongressman;
	}
	
	public String getPartyCongressman() {
		return partyCongressman;
	}
	
	public void setPartyCongressman( String partyCongressman ) {
		this.partyCongressman = partyCongressman;
	}
	
	public String getUfCongressman() {
		return ufCongressman;
	}
	
	public void setUfCongressman( String ufCongressman ) {
		this.ufCongressman = ufCongressman;
	}
	
	public List<Quota> getQuotasCongressman() {
		return quotasCongressman;
	}
	
	public void setQuotasCongressman( List<Quota> quotasCongressman ) {
		this.quotasCongressman = quotasCongressman;
	}
	
	public double getTotalSpentCongressman() {
		return totalSpentCongressman;
	}
	
	public void setTotalSpentCongressman( double totalSpentCongressman ) {
		this.totalSpentCongressman = totalSpentCongressman;
	}
	
	public int getRankingCongressman() {
		return ranking;
	}
	
	public void setRankingCongressman(int rankingCongressman) {
		this.ranking = rankingCongressman;
		
	}
}
