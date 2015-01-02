package br.com.visualize.akan.domain.controller;

import br.com.visualize.akan.domain.model.Statistic;

public class StatisticController {
	StatisticDao statisticDao = StatisticDao.getInstance();
	
	public Statistic getStatisticByYear() {
		Statistic statisticYear = statisticDao.getStatisticByYear();
		
		return statisticYear;
	}	
}
