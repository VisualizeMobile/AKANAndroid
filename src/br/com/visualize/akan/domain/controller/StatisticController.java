package br.com.visualize.akan.domain.controller;

import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;
import android.util.Log;
import br.com.visualize.akan.api.dao.StatisticDao;
import br.com.visualize.akan.api.helper.JsonHelper;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.model.Statistic;

public class StatisticController {
	private UrlController urlController;
	private static Statistic statistic; //Verify necessary after
	private static List<Statistic> statisticList;
	private static StatisticController instanceStatisticController = null;
	private StatisticDao statisticDao = null;
	
	private StatisticController( Context context ) {
		
		statisticDao = StatisticDao.getInstance( context );
		urlController = UrlController.getInstance( context );
	}
	
	/**
	 * Return the unique instance of QuotaDao active in the project.
	 * <p>
	 * @return The unique instance of QuotaDao.
	 */
	public static StatisticController getInstance( Context context ) {
		if( instanceStatisticController != null ) {
			/* !Nothing To Do. */
			
		} else {
			instanceStatisticController = new StatisticController( context );
		}
		
		return instanceStatisticController;
	}
	
	/* TODO: Write JAVADOC. */
	public void requestStatistics(ResponseHandler<String> responseHandler ) 
			throws Exception {
			requestStatisticPerMonth(responseHandler);
			requestStatisticStdDeviation(responseHandler);
	}
		
	/* TODO: Write JAVADOC. */
	private void requestStatisticPerMonth(ResponseHandler<String> responseHandler ) 
			throws Exception {
		
		String url = urlController.statisticsPerMonthUrl();
		
		String jsonStatisticList;
		
		jsonStatisticList = HttpConnection.request( responseHandler, url );
		insertStatistics( JsonHelper.listStatisticsFromJSON(jsonStatisticList));
	}
	
	/* TODO: Write JAVADOC. */
	private void requestStatisticStdDeviation(ResponseHandler<String> responseHandler ) 
			throws Exception {
		
		String url = urlController.statisticsStdDeviationUrl();
		
		String jsonStatisticList;
		
		jsonStatisticList = HttpConnection.request( responseHandler, url );
		insertStatistics( JsonHelper.listStatisticsFromJSON(jsonStatisticList));
	}
	
	public Statistic getGeneralStatistic(int subquota){
		return statisticDao.getGeneralStatistic(subquota);
	}
	
	public List<Statistic> getStatisticByYearAndType(int subquota, int year){
		return statisticDao.getStatisticByYearAndType(year, subquota);
	}
	
	public void setStatistic(Statistic statistic){
		StatisticController.statistic = statistic;
	}
	
	public Statistic getStatistic(){
			return StatisticController.statistic;
	}

	public static List<Statistic> getStatisticList() {
		return statisticList;
	}

	public static void setStatisticList(List<Statistic> statisticList) {
		StatisticController.statisticList = statisticList;
	}
	
	public void insertStatistics(List<Statistic> statisticList) {
		boolean result = statisticDao.insertStatisticsList(statisticList);
		Log.e("INSERT STATUS",""+result);
	}

}
