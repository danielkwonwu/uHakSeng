package app;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DataCrawlers {
	
	KorExRateDat currentXrate;
	KorExRateDat[] XRateHist;
	int XRateHistSize;
	WeatherDat seoulWeatherDat;
	WeatherDat stlWeatherDat;
	Headlines korHeadlines;
	Headlines usHeadlines;
	
	public DataCrawlers() throws IOException {
		this.currentXrate = KorXRate();
		this.XRateHist = KorXRateHist();
		this.XRateHistSize = this.XRateHist.length;
		this.seoulWeatherDat = SeoulWeather();
		this.stlWeatherDat = StlWeather();
		this.korHeadlines = new Headlines(getKorHeadlines(), this.currentXrate.time);
		this.usHeadlines = new Headlines(getUSHeadlines(),this.currentXrate.time);
		System.out.println("Crawl Complete");
	}
	/**
	 * 
	 * @param adress, className
	 * @param jsoupCrawler
	 * @return Element for the html class name
	 * @throws IOException
	 */
	public static Elements jsoupCrawler(String adress, String className) throws IOException {
		Document doc = Jsoup.connect(adress).get();
		Elements cut = doc.getElementsByClass(className);
		return cut;
	}
	
	public static KorExRateDat KorXRate() throws IOException {
		
		Elements time = jsoupCrawler("https://ko.valutafx.com/USD-KRW.htm", "utc-value");
		String [] time1 = time.toString().split("dt=\"");
		String [] time2 = time1[1].split("\">");
		String q = time2[0];//.replaceAll("-", "");
		
		
		Elements korEx = jsoupCrawler("https://ko.valutafx.com/USD-KRW.htm", "rate-value");
		String [] korEx1 = korEx.toString().split(">");
		String [] korEx2 = korEx1[1].split("<");
		String st = korEx2[0].replaceAll(",", "");
		double rate = Double.parseDouble(st);
		
		KorExRateDat ans = new KorExRateDat (rate, q);
		
		return ans;
	}
	
	public static KorExRateDat[] KorXRateHist() throws IOException {		
		Elements dates = jsoupCrawler("https://ko.valutafx.com/USD-KRW-history.htm", "date             pure-u-6-24  pure-u-sm-5-24  pure-u-md-5-24   pure-u-lg-5-24");
		String [] dates1 = dates.toString().split("<div class=\"pure-g-cell\">\n  ");
		String [] datesFinal = new String[dates1.length - 1];
			for (int i = 1; i < dates1.length; i++) {
				datesFinal[i-1] = dates1[i].split("\n")[0];
			}
		Elements rates = jsoupCrawler("https://ko.valutafx.com/USD-KRW-history.htm", "currency-rate    pure-u-8-24  pure-u-sm-8-24  pure-u-md-8-24   pure-u-lg-6-24");
		String [] rates1 = rates.toString().split("<div class=\"pure-g-cell\">\n  ");
		double [] ratesFinal = new double[dates1.length - 1];
			for (int i = 1; i < rates1.length; i++) {
				String a = rates1[i].split("\n")[0];
				String b = a.split("= ")[1];
				String c = b.split(" ")[0].replaceAll(",", "");
				ratesFinal[i-1] = Double.parseDouble(c);
			}
		
		KorExRateDat[] ans = new KorExRateDat[datesFinal.length];
			
		for (int r = 0; r < datesFinal.length; r++) {
			ans [r] = new KorExRateDat (ratesFinal[r], datesFinal[r]);
		}
		return ans;
	}
	
	
	public static WeatherDat SeoulWeather() throws IOException {
		Elements current = jsoupCrawler("https://search.naver.com/search.naver?where=nexearch&sm=tab_jum&query=%EC%84%9C%EC%9A%B8+%EB%82%A0%EC%94%A8", "todaytemp");
		String [] current1 = current.toString().split("\">");
		String [] current2 = current1[1].split("</");
		int currentTemp = Integer.parseInt(current2[0]);
		
		
		Elements min = jsoupCrawler("https://search.naver.com/search.naver?where=nexearch&sm=tab_jum&query=%EC%84%9C%EC%9A%B8+%EB%82%A0%EC%94%A8", "min");
		String [] min1 = min.toString().split("m\">");
		String [] min2 = min1[1].split("</");
		int minTemp = Integer.parseInt(min2[0]);
		
		
		Elements max = jsoupCrawler("https://search.naver.com/search.naver?where=nexearch&sm=tab_jum&query=%EC%84%9C%EC%9A%B8+%EB%82%A0%EC%94%A8", "max");
		String [] max1 = max.toString().split("m\">");
		String [] max2 = max1[1].split("</");
		int maxTemp = Integer.parseInt(max2[0]);
		
		
		Elements feel = jsoupCrawler("https://search.naver.com/search.naver?where=nexearch&sm=tab_jum&query=%EC%84%9C%EC%9A%B8+%EB%82%A0%EC%94%A8", "sensible");	
		String [] feel1 = feel.toString().split("m\">");
		String [] feel2 = feel1[1].split("</");
		double feelTemp = Double.parseDouble(feel2[0]);
		
		WeatherDat ans = new WeatherDat("Seoul", currentTemp, minTemp, maxTemp, feelTemp);
		return ans;
	}
	
	public static WeatherDat StlWeather() throws IOException {
		Elements current = jsoupCrawler("https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=%EC%84%B8%EC%9D%B8%ED%8A%B8%EB%A3%A8%EC%9D%B4%EC%8A%A4+%EB%82%A0%EC%94%A8&oquery=%EC%84%9C%EC%9A%B8+%EB%82%A0%EC%94%A8&tqi=T%2FA0%2FdpySDosssgGQONssssstZh-022497", "todaytemp");
		String [] current1 = current.toString().split("\">");
		String [] current2 = current1[1].split("</");
		int currentTemp = Integer.parseInt(current2[0]);
		
		
		Elements min = jsoupCrawler("https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=%EC%84%B8%EC%9D%B8%ED%8A%B8%EB%A3%A8%EC%9D%B4%EC%8A%A4+%EB%82%A0%EC%94%A8&oquery=%EC%84%9C%EC%9A%B8+%EB%82%A0%EC%94%A8&tqi=T%2FA0%2FdpySDosssgGQONssssstZh-022497", "min");
		String [] min1 = min.toString().split("m\">");
		String [] min2 = min1[1].split("</");
		int minTemp = Integer.parseInt(min2[0]);
		
		Elements max = jsoupCrawler("https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=%EC%84%B8%EC%9D%B8%ED%8A%B8%EB%A3%A8%EC%9D%B4%EC%8A%A4+%EB%82%A0%EC%94%A8&oquery=%EC%84%9C%EC%9A%B8+%EB%82%A0%EC%94%A8&tqi=T%2FA0%2FdpySDosssgGQONssssstZh-022497", "max");
		String [] max1 = max.toString().split("m\">");
		String [] max2 = max1[1].split("</");
		int maxTemp = Integer.parseInt(max2[0]);
		
		Elements feel = jsoupCrawler("https://weather.com/weather/today/l/Saint+Louis+MO+USMO0787:1:US", "deg-hilo-nowcard");	
		String [] feel1 = feel.toString().split("\"\">");
		String [] feel2 = feel1[1].split("<sup");
		double feelTemp = Double.parseDouble(feel2[0]);
		double feelTempC = (feelTemp - 32)*(0.5556);
		String feelTempCC = String.format("%.1f", feelTempC);
		double feelTempCCC = Double.parseDouble(feelTempCC);
		
		
		WeatherDat ans = new WeatherDat("St.Louis", currentTemp, minTemp, maxTemp, feelTempCCC);
		return ans;
	}
	
	public int getXRateHistSize() {
		return this.XRateHistSize;
	}
	
	public String[] getKorHeadlines() throws IOException {
		Elements headlines = jsoupCrawler("http://m.chosun.com/svc/ranking.html","tt");
		String[] headlines1 = headlines.toString().split("<span class=\"tt\"> ");
		String[] headlines2 = new String[headlines1.length - 1];
		for (int i = 1; i < headlines1.length; i++) {
			headlines2[i-1] = headlines1[i].split(" </span>")[0];
		}
		return headlines2;
	}
	
	public String[] getUSHeadlines() throws IOException {
		Elements headlines = jsoupCrawler("http://m.cnn.com/en","afe4286c");
		String[] headlines1 = headlines.toString().split("\">");
		String[] headlines2 = new String[headlines1.length - 2];
		for (int i = 2; i < headlines1.length; i++) {
			headlines2[i-2] = headlines1[i].split("</a></li>")[0];
		}
		return headlines2;
	}
	
	
	public static void main(String[] args) throws IOException, InterruptedException {

	}

}
