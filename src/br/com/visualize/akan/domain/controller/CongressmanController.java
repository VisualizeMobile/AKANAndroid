package br.com.visualize.akan.domain.controller;

import java.util.List;

import org.apache.http.client.ResponseHandler;

import br.com.visualize.akan.domain.exception.ConnectionFailedException;
import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.exception.RequestFailedException;
import br.com.visualize.akan.domain.model.Congressman;

public interface CongressmanController {
	

	public List<Congressman> getAllCongressman( 
			ResponseHandler<String> responseHandler ) throws 
			ConnectionFailedException, RequestFailedException, 
			NullCongressmanException;

	public List<Congressman> getAll();

	public List<Congressman> getByName ( String congressmanName );
}
