package br.com.visualize.akan.api.dao;

import android.content.Context;
import br.com.visualize.akan.domain.enumeration.SubQuota;
import br.com.visualize.akan.domain.model.Statistic;

public class StatisticDao {
	private static StatisticDao instanceStatisticDao = null; 
	
	private StatisticDao( Context context ) {
		QuotaDao.context = context;
	}
	
	/**
	 * Return the unique instance of QuotaDao active in the project.
	 * <p>
	 * @return The unique instance of QuotaDao.
	 */
	public static StatisticDao getInstance( Context context ) {
		if( instanceStatisticDao != null ) {
			/* !Nothing To Do. */
			
		} else {
			instanceStatisticDao = new StatisticDao( context );
		}
		
		return instanceStatisticDao;
	}
	
	public Statistic getStatisticByYear( int year ) {
		/* TODO: remove mock. */
		Statistic statisticYear = new Statistic();
		statisticYear.setAverage( 20000 );
		statisticYear.setMaxValue( 25000 );
		statisticYear.setStdDeviation( 200 );
		statisticYear.setSubquota( SubQuota.POSTAL_SERVICES );
		statisticYear.setYear( year );
		
		return statisticYear;
	}
}
