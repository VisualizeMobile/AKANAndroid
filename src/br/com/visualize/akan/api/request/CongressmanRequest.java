package br.com.visualize.akan.api.request;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;
import br.com.visualize.akan.api.helper.JsonHelper;
import br.com.visualize.akan.domain.controller.UrlController;
import br.com.visualize.akan.domain.exception.ConnectionFailedException;
import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.exception.RequestFailedException;
import br.com.visualize.akan.domain.model.Congressman;

public class CongressmanRequest {
	

	private static CongressmanRequest instance = null;

	private UrlController urlController;

	private CongressmanRequest(Context context) {
		this.urlController = UrlController.getInstance(context);
	}

	public List<Congressman> doRequest(ResponseHandler<String> responseHandler)
			throws NullCongressmanException, ConnectionFailedException,
			RequestFailedException {
		List<Congressman> congressmanList = null;
		if(responseHandler != null){
			String url = urlController.getUrl().getDefaultUrl();
			url += "/parlamentares";
			String jsonCongressmanList = HttpConnection.request(responseHandler, url);
			congressmanList = JsonHelper.listCongressmanFromJSON(jsonCongressmanList);
		}
		return congressmanList;
	}

}
