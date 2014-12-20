package br.com.visualize.akan.domain.controller;

import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;
import br.com.visualize.akan.api.dao.CongressmanDao;
import br.com.visualize.akan.api.helper.JsonHelper;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.model.Congressman;

public class CongressmanController {

	private static CongressmanController instance = null;
	private static Congressman congressman;
	private static List<Congressman> congressmanList;

	private Context context;
	private QuotaController quotaController;
	private UrlController urlController;
	private CongressmanDao congressmanDao;
	
	private CongressmanController(Context context) {
		quotaController = QuotaController.getInstance(context);
		congressmanDao = CongressmanDao.getInstance(context);
		urlController = UrlController.getInstance(context);

		this.context = context;
	}

	public static CongressmanController getInstance( Context context ) {
		if ( instance == null ) {
			instance = new CongressmanController( context );
		}
		
		return instance;
	}
	
	public void setCongressman( Congressman congressman){
		CongressmanController.congressman = congressman;
	}
	
	public List<Congressman> requestAllCongressman( 
			ResponseHandler<String> responseHandler )
			throws Exception {
		
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
		
		return congressmanList;
	}
	
	public List<Congressman> getAllCongressman() {
		congressmanList = congressmanDao.getAll();
		
		return congressmanList;
	}

	private static void setCongressmanList( List<Congressman> congressmanList ) {
		CongressmanController.congressmanList = congressmanList;
	}
	
	public List<Congressman> getByName ( String congressmanName ) {
		congressmanList = congressmanDao.selectCongressmanByName( congressmanName );
		
		return congressmanList;
	}
	
	public Congressman getCongresman() {
		return CongressmanController.congressman;
	}
}
