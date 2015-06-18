package weight_simulator.old.data;

public class WeightDTO {
	private double gross = 0;
	private double tare = 0;
	private String serverIp = "";
	private String clientIp = "";

	public double getGross() {
		return gross;
	}

	public void setGross(double gross) {
		String number = String.valueOf(gross);
		if(number.length() > 5){
			this.gross = Double.valueOf(number.substring(0, 5));
			if(Double.valueOf(number.substring(5, 6)) >= 5){
				this.gross += 0.001;
			}
		}
		else
			this.gross = gross;
	}

	public double getTare() {
		return tare;
	}

	public void setTare(double tare) {
		String number = String.valueOf(tare);
		if(number.length() > 5){
			this.tare = Double.valueOf(number.substring(0, 4));
			if(Double.valueOf(number.substring(5, 6)) >= 5){
				this.tare += 0.001;
			}
		}
		else
			this.tare = tare;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

}
