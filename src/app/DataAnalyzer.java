package app;


import java.io.IOException;
import app.StdDraw;

public class DataAnalyzer {

	private DataCrawlers dataCrawled;
	
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
		this.setDataCrawled(data);
	}
	
	
	public void briefCurrentXRate() {
		System.out.println("$1 is " + this.getDataCrawled().currentXrate.getRate() + "KRW at " + this.getDataCrawled().currentXrate.getTime());
	}
	
	public void recentXRateTrend() {
		System.out.println("Data from " + this.getDataCrawled().getXRateHist()[this.getDataCrawled().getXRateHist().length - 1].time + " ~ " + this.getDataCrawled().getXRateHist()[0].time + " (" + this.getDataCrawled().XRateHistSize + ")");
	}
	
	public double XRateMean() {
		double total = 0;
		for (int i = 0; i < this.getDataCrawled().getXRateHist().length; i++) {
			total = total + this.getDataCrawled().getXRateHist()[i].rate;
		}
		double ave1 = total/this.getDataCrawled().XRateHistSize;
		double ave = Math.round(ave1*1000)/1000;
		System.out.println("Average during this time period is " + ave + "KRW.");
		return ave;
	}
	
	public void briefCurrentWeather(String where) {
		if (where == "St.Louis") {
			System.out.println("St.Louis's current temperature is " + this.getDataCrawled().stlWeatherDat.getCurrent() +"¡ÆC. High of " + this.getDataCrawled().stlWeatherDat.getMax() + "¡ÆC and low of " + this.getDataCrawled().stlWeatherDat.getMin() + "¡ÆC. Feels like " + this.getDataCrawled().stlWeatherDat.getFeels() +"¡ÆC.");
		}
		else if (where == "Seoul"){
			System.out.println("Seoul's current temperature is " + this.getDataCrawled().seoulWeatherDat.getCurrent() +"¡ÆC. High of " + this.getDataCrawled().seoulWeatherDat.getMax() + "¡ÆC and low of " + this.getDataCrawled().seoulWeatherDat.getMin() + "¡ÆC. Feels like " + this.getDataCrawled().seoulWeatherDat.getFeels() + "¡ÆC.");
		}
	}
	
	public void feelsDifferent() {
		if(this.getDataCrawled().stlWeatherDat.feelsDiff) {
			if(this.getDataCrawled().stlWeatherDat.current < this.getDataCrawled().stlWeatherDat.feels) {
				System.out.println("St.Louis weather feels significantly hotter than it actually is.");
			}
			if(this.getDataCrawled().stlWeatherDat.current > this.getDataCrawled().stlWeatherDat.feels)  {
				System.out.println("St.Louis weather feels significantly colder than it actually is.");
			}
		}
		if(this.getDataCrawled().seoulWeatherDat.feelsDiff) {
			if(this.getDataCrawled().seoulWeatherDat.current < this.getDataCrawled().seoulWeatherDat.feels) {
				System.out.println("Seoul weather feels significantly hotter than it actually is.");
			}
			if(this.getDataCrawled().seoulWeatherDat.current > this.getDataCrawled().seoulWeatherDat.feels)  {
				System.out.println("Seoul weather feels significantly colder than it actually is.");
			}
		}
	}
	
	public void displayKorHeadlines() {
		System.out.println("At " + this.getDataCrawled().korHeadlines.date + "...");
		for (int i = 0; i < this.getDataCrawled().korHeadlines.headlines.length; i++) {
			System.out.println(this.getDataCrawled().korHeadlines.headlines[i]);
		}
	}
	
	public void displayUSHeadlines() {
		System.out.println("At " + this.getDataCrawled().usHeadlines.date + "...");
		for (int i = 0; i < this.getDataCrawled().usHeadlines.headlines.length; i++) {
			System.out.println(this.getDataCrawled().usHeadlines.headlines[i]);
		}
	}
	
	public void setCanvas() {
		StdDraw.setCanvasSize(800,800);
		StdDraw.setXscale(0, this.getDataCrawled().getXRateHist().length + 3);
		StdDraw.line(0,1,1,0);
		StdDraw.show();
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DataAnalyzer k = new DataAnalyzer(new DataCrawlers());
		//k.setCanvas();
		k.briefCurrentXRate();
		k.recentXRateTrend();
		k.XRateMean();
		System.out.println("\n");
		k.briefCurrentWeather("St.Louis");
		k.briefCurrentWeather("Seoul");
		k.feelsDifferent();
		//System.out.println("\n");
		//k.displayKorHeadlines();
		//System.out.println("");
		//k.displayUSHeadlines();
	}


	public DataCrawlers getDataCrawled() {
		return dataCrawled;
	}


	public void setDataCrawled(DataCrawlers dataCrawled) {
		this.dataCrawled = dataCrawled;
	}

}
