package br.com.visualize.akan.domain.controller;

import org.apache.http.client.ResponseHandler;
import java.util.List;
import android.content.Context;
import android.util.Log;
import br.com.visualize.akan.api.dao.CongressmanDao;
import br.com.visualize.akan.api.helper.JsonHelper;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.exception.ConnectionFailedException;
import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.exception.RequestFailedException;
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

	public static CongressmanController getInstance(Context context) {
		if (instance == null) {
			instance = new CongressmanController(context);
		}
		return instance;
	}

	public List<Congressman> getAllCongressman(
			ResponseHandler<String> responseHandler)
			throws ConnectionFailedException, RequestFailedException,
			NullCongressmanException {
		if (responseHandler != null) {
			Log.i("connection stabilished", "connection stabilished, recieving data...");
			if (congressmanDao.checkEmptyLocalDb()) {
				// popula o banco
				Log.i("dataBase insetition", "Empty database, inserting congressmen...");
				String url = urlController.getAllCongressmanUrl();
				Log.i("urlrails",url );
				
				String jsonCongressman = HttpConnection.request(
						responseHandler, url);
				Log.i("jsonCongressman", jsonCongressman);	
				setCongressmanList(JsonHelper
						.listCongressmanFromJSON(jsonCongressman));
				
				congressmanDao.insertAllCongressman(getCongressmanList());
				
			}
			else{
				//nothing here.
			}
		}
		return getCongressmanList();
	}

	public static List<Congressman> getCongressmanList() {
		
		return congressmanList;
	}
	public List<Congressman> getAll(){
		congressmanList = congressmanDao.getAll();
		return congressmanList;
		
	}

	private static void setCongressmanList(List<Congressman> congressmanList) {
		CongressmanController.congressmanList = congressmanList;
	}
	
	public List<Congressman> getByName (String congressmanName){
		congressmanList = congressmanDao.selectCongressmanByName(congressmanName);
		return congressmanList;
	}

}
