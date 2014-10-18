package br.com.visualize.akan.domain.controller;

import br.com.visualize.akan.api.dao.UrlDao;
import br.com.visualize.akan.domain.model.Url;
import android.content.Context;

public class UrlController {

	
	private static UrlController instance = null;
	private static Url url;
	
	private UrlDao dao;
	
	private UrlController(Context context){
		this.dao = UrlDao.getInstance(context);
	}
	
	public static UrlController getInstance(Context context){
		if(instance == null){
			instance = new UrlController(context);
		}
		return instance;
	}
	
	public Url getUrl(){
		url = dao.getUrl();
		return url;
	}
	
	public String getAllCongressmanUrl(){
		//return getUrl().getDefaultUrl() + "/congressmen.json";
		return "http://192.168.1.4:3000/congressmen.json";
	}
	
	//TODO quotasWithCongressmanIdUrl
	public String quotasWithCongressmanIdUrl(String id){
		return getUrl().getDefaultUrl() + "/cota" + "?id=" + id;
	}
}
