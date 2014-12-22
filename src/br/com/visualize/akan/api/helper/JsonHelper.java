package br.com.visualize.akan.api.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.model.Congressman;
import br.com.visualize.akan.domain.model.Quota;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonHelper {
	
	private static List<Congressman> congressmanList = new ArrayList<Congressman>();

	public static List<Congressman> listCongressmanFromJSON(String jsonCongressmanList) throws NullCongressmanException, JSONException{
		Log.e("entrei na tradução", "entrei na tradução");
		Log.e("json aqui",jsonCongressmanList);
		JSONArray jArray = new JSONArray(jsonCongressmanList);
		Log.e(jArray.getJSONObject(0).getJSONObject("fields").getString("nomeparlamentar"), "peguei nome do parlamentar");
		
	
		try{
			Congressman congressman = null;
			
			for(int i = 0; i < jArray.length(); i++){
				congressman = new Congressman();
				 congressman.setIdCongressman(jArray.getJSONObject(i).getInt("pk"));
				 congressman.setTotalSpentCongressman(jArray.getJSONObject(i).getJSONObject("fields").getDouble("valor"));
				 congressman.setRankingCongressman(jArray.getJSONObject(i).getJSONObject("fields").getInt("ranking"));
				 congressman.setUfCongressman(jArray.getJSONObject(i).getJSONObject("fields").getString("ufparlamentar"));
				 congressman.setPartyCongressman(jArray.getJSONObject(i).getJSONObject("fields").getString("partidoparlamentar"));
				 congressman.setNameCongressman(jArray.getJSONObject(i).getJSONObject("fields").getString("nomeparlamentar"));
				 congressmanList.add(congressman);
				 Log.e(congressman.getNameCongressman(), "Peguei parlamentar do servidor");
				  }
			
		}catch(NullPointerException e){
			throw new NullCongressmanException();
		}
		Log.e(congressmanList.get(0).getNameCongressman(), "peguei parlamentar");
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
