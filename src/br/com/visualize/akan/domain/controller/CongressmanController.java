package br.com.visualize.akan.domain.controller;

import java.util.List;

import org.apache.http.client.ResponseHandler;

import br.com.visualize.akan.domain.model.Congressman;

public interface CongressmanController {
	

	public List<Congressman> requestAllCongressman( 
			ResponseHandler<String> responseHandler ) throws Exception;

	public List<Congressman> getAllCongressman();

	public List<Congressman> getByName ( String congressmanName );
}
