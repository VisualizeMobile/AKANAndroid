package br.com.visualize.akan.domain.controller;

import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;
import br.com.visualize.akan.api.dao.CongressmanDao;
import br.com.visualize.akan.api.helper.JsonHelper;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.exception.ConnectionFailedException;
import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.exception.RequestFailedException;
import br.com.visualize.akan.domain.model.Congressman;

public class DeputyController implements CongressmanController {

	private static DeputyController instance = null;
	private static Congressman deputy;
	private static List<Congressman> deputyList;

	private Context context;
	private QuotaController quotaController;
	private UrlController urlController;
	private CongressmanDao congressmanDao;
	
	private DeputyController(Context context) {
		quotaController = QuotaController.getInstance(context);
		congressmanDao = CongressmanDao.getInstance(context);
		urlController = UrlController.getInstance(context);

		this.context = context;
	}

	public static DeputyController getInstance( Context context ) {
		if ( instance == null ) {
			instance = new DeputyController( context );
		}
		
		return instance;
	}
	
	public List<Congressman> requestAllCongressman( 
			ResponseHandler<String> responseHandler )
			throws ConnectionFailedException, RequestFailedException,
			NullCongressmanException {
		
		if ( responseHandler != null ) {
			
			if ( congressmanDao.checkEmptyLocalDb() ) {

				String url = urlController.getAllCongressmanUrl();
				String jsonCongressman = HttpConnection.request(
						responseHandler, url );
				
				setCongressmanList( JsonHelper
						.listCongressmanFromJSON( jsonCongressman ) );
				
				congressmanDao.insertAllCongressman( getCongressmanList() );
				
			} else {
				
				/*! Nothing To Do.*/
			}
		}
		
		return getCongressmanList();
	}
	
	public static List<Congressman> getCongressmanList() {
		
		return deputyList;
	}
	
	public List<Congressman> getAllCongressman() {
		deputyList = congressmanDao.getAll();
		
		return deputyList;
	}

	private static void setCongressmanList( List<Congressman> congressmanList ) {
		DeputyController.deputyList = congressmanList;
	}
	
	public List<Congressman> getByName ( String congressmanName ) {
		deputyList = congressmanDao.selectCongressmanByName( congressmanName );
		
		return deputyList;
	}
}
