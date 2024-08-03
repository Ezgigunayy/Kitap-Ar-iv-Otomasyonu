package application;


public class Login_kayit {
	private String Kul_ad;
	private String Sifre;
	
	Login_kayit(String Kul_ad, String Sifre){
		this.Kul_ad=Kul_ad;
		this.Sifre=Sifre;
	}

	public String getKul_ad() {
		return Kul_ad;
	}

	public void setKul_Ad(String Kul_ad) {
		this.Kul_ad = Kul_ad;
	}

	public String getSifre() {
		return Sifre;
	}

	public void setSifre(String Sifre) {
		this.Sifre = Sifre;
	}

}
