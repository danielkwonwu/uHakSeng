package app;


public class WeatherDat {
	
	String where;
	int current;
	int min;
	int max;
	double feels;
	boolean feelsDiff;
	
	
	public WeatherDat(String where, int current, int min, int max, double feels) {
		this.where = where;
		this.current = current;
		this.min = min;
		this.max = max;
		this.feels = feels;
		this.feelsDiff = Math.abs(this.feels - this.current) > 3;
	}


	public int getCurrent() {
		return current;
	}


	public void setCurrent(int current) {
		this.current = current;
	}


	public int getMin() {
		return min;
	}


	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}


	public void setMax(int max) {
		this.max = max;
	}


	public double getFeels() {
		return feels;
	}



	public void setFeels(double feels) {
		this.feels = feels;
	}


	
	public boolean getFeelsDiff() {
		return this.feelsDiff;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
