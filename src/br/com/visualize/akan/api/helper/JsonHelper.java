package br.com.visualize.akan.api.helper;

import java.util.List;

import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.model.Congressman;
import br.com.visualize.akan.domain.model.Quota;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonHelper {
	
	public static List<Congressman> listCongressmanFromJSON(String jsonCongressmanList) throws NullCongressmanException{
		List<Congressman> congressmanList;
		try{
			Gson gson = new Gson();
			congressmanList = gson.fromJson(jsonCongressmanList, new TypeToken<List<Congressman>>(){}.getType());
		}catch(NullPointerException e){
			throw new NullCongressmanException();
		}
		return congressmanList;
	}
	
	public static List<Quota> listQuotaByIdCongressmanFromJSON(String jsonQuotaByIdCongressmanList) throws NullCongressmanException{
		List<Quota> quotaList = null;
		try{
			Gson gson = new Gson();
			quotaList = gson.fromJson(jsonQuotaByIdCongressmanList, new TypeToken<List<Quota>>(){}.getType());
		}catch(NullPointerException e){
			throw new NullCongressmanException();
		}
		return quotaList;
	}

}
