package application;

import java.sql.Date;

public class Kitap_kayit {
 private String kitapAdi;
 private String yazarAdi;
 private int sayfaSayisi;
 private String yorumAlinti;
 private Date baslamaTarih;
 private Date bitisTarih;
 
 
 Kitap_kayit(String kitapAdi, String yazarAdi, int sayfaSayisi,Date baslamaTarih, Date bitisTarih, String yorumAlinti){
	 this.kitapAdi=kitapAdi;
	 this.yazarAdi=yazarAdi;
	 this.sayfaSayisi=sayfaSayisi;
	 this.yorumAlinti=yorumAlinti;
	 this.baslamaTarih=baslamaTarih;
	 this.bitisTarih=bitisTarih;
	
 }


public Kitap_kayit(String kitapAdi2, String yazarAdi2, java.util.Date baslamaTarih2, java.util.Date bitisTarih2,
		int sayfaSayisi2, String yorumAlinti2) {
}


public String getKitapAdi() {
	return kitapAdi;
}


public void setKitapAdi(String kitapAdi) {
	this.kitapAdi = kitapAdi;
}


public String getYazarAdi() {
	return yazarAdi;
}


public void setYazarAdi(String yazarAdi) {
	this.yazarAdi = yazarAdi;
}


public int getSayfaSayisi() {
	return sayfaSayisi;
}


public void setSayfaSayisi(int sayfaSayisi) {
	this.sayfaSayisi = sayfaSayisi;
}


public String getYorumAlinti() {
	return yorumAlinti;
}


public void setYorumAlinti(String yorumAlinti) {
	this.yorumAlinti = yorumAlinti;
}


public Date getBaslamaTarih() {
	return baslamaTarih;
}


public void setBaslamaTarih(Date baslamaTarih) {
	this.baslamaTarih = baslamaTarih;
}


public Date getBitisTarih() {
	return bitisTarih;
}


public void setBitisTarih(Date bitisTarih) {
	this.bitisTarih = bitisTarih;
}





}
