package app;


import java.io.IOException;

public class DataAnalyzer {

	DataCrawlers dataCrawled;
	
	double XRate3DayMin;
	double XRateWeeklyMin;
	double XRateMonthlyMin;
	double XRate3DaySlope;
	double XRateWeeklySlope;
	double XRateMonthlySlope;
	
	boolean StlFeelsColder;
	boolean StlFeelsHotter;
	boolean SeoulFeelsColder;
	boolean SeoulFeelsHotter;
	
	public DataAnalyzer(DataCrawlers data) throws IOException {
		this.dataCrawled = data;
	}
	
	
	public void briefCurrentXRate() {
		System.out.println("$1 is " + this.dataCrawled.currentXrate.getRate() + "KRW at " + this.dataCrawled.currentXrate.getTime());
	}
	
	public void recentXRateTrend() {
		System.out.println("Data from " + this.dataCrawled.XRateHist[this.dataCrawled.XRateHist.length - 1].time + " ~ " + this.dataCrawled.XRateHist[0].time + " (" + this.dataCrawled.XRateHistSize + ")");
	}
	
	public double XRateMean() {
		double total = 0;
		for (int i = 0; i < this.dataCrawled.XRateHist.length; i++) {
			total = total + this.dataCrawled.XRateHist[i].rate;
		}
		double ave1 = total/this.dataCrawled.XRateHistSize;
		double ave = Math.round(ave1*1000)/1000;
		System.out.println("Average during this time period is " + ave + "KRW.");
		return ave;
	}
	
	public void briefCurrentWeather(String where) {
		if (where == "St.Louis") {
			System.out.println("St.Louis's current temperature is " + this.dataCrawled.stlWeatherDat.getCurrent() +"¡ÆC. High of " + this.dataCrawled.stlWeatherDat.getMax() + "¡ÆC and low of " + this.dataCrawled.stlWeatherDat.getMin() + "¡ÆC. Feels like " + this.dataCrawled.stlWeatherDat.getFeels() +"¡ÆC.");
		}
		else if (where == "Seoul"){
			System.out.println("Seoul's current temperature is " + this.dataCrawled.seoulWeatherDat.getCurrent() +"¡ÆC. High of " + this.dataCrawled.seoulWeatherDat.getMax() + "¡ÆC and low of " + this.dataCrawled.seoulWeatherDat.getMin() + "¡ÆC. Feels like " + this.dataCrawled.seoulWeatherDat.getFeels() + "¡ÆC.");
		}
	}
	
	public void feelsDifferent() {
		if(this.dataCrawled.stlWeatherDat.feelsDiff) {
			if(this.dataCrawled.stlWeatherDat.current < this.dataCrawled.stlWeatherDat.feels) {
				System.out.println("St.Louis weather feels significantly hotter than it actually is.");
			}
			if(this.dataCrawled.stlWeatherDat.current > this.dataCrawled.stlWeatherDat.feels)  {
				System.out.println("St.Louis weather feels significantly colder than it actually is.");
			}
		}
		if(this.dataCrawled.seoulWeatherDat.feelsDiff) {
			if(this.dataCrawled.seoulWeatherDat.current < this.dataCrawled.seoulWeatherDat.feels) {
				System.out.println("Seoul weather feels significantly hotter than it actually is.");
			}
			if(this.dataCrawled.seoulWeatherDat.current > this.dataCrawled.seoulWeatherDat.feels)  {
				System.out.println("Seoul weather feels significantly colder than it actually is.");
			}
		}
	}
	
	public void displayKorHeadlines() {
		System.out.println("At " + this.dataCrawled.korHeadlines.date + "...");
		for (int i = 0; i < this.dataCrawled.korHeadlines.headlines.length; i++) {
			System.out.println(this.dataCrawled.korHeadlines.headlines[i]);
		}
	}
	
	public void displayUSHeadlines() {
		System.out.println("At " + this.dataCrawled.usHeadlines.date + "...");
		for (int i = 0; i < this.dataCrawled.usHeadlines.headlines.length; i++) {
			System.out.println(this.dataCrawled.usHeadlines.headlines[i]);
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DataAnalyzer k = new DataAnalyzer(new DataCrawlers());
		k.briefCurrentXRate();
		k.recentXRateTrend();
		k.XRateMean();
		System.out.println("\n");
		k.briefCurrentWeather("St.Louis");
		k.briefCurrentWeather("Seoul");
		k.feelsDifferent();
		System.out.println("\n");
		k.displayKorHeadlines();
		System.out.println("");
		k.displayUSHeadlines();
	}

}
