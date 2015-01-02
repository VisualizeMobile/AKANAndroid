package br.com.visualize.akan.domain.controller;

import android.content.Context;
import br.com.visualize.akan.api.dao.StatisticDao;
import br.com.visualize.akan.domain.model.Statistic;

public class StatisticController {
	private static StatisticController instanceStatisticController = null;
	private StatisticDao statisticDao = null;
	
	private StatisticController( Context context ) {
		statisticDao = StatisticDao.getInstance( context );
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
	
	public Statistic getStatisticByYear( int year ) {
		Statistic statisticYear = statisticDao.getStatisticByYear( year );
		
		return statisticYear;
	}	
}
