package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.isteMySQL.Util.VeritabaniUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;


import java.sql.*;

public class AramaController {
	public AramaController(){
		baglanti= VeritabaniUtil.Baglan();
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_ara;

    @FXML
    private TableColumn<Kitap_kayit, Date> columnAra_baslama;

    @FXML
    private TableColumn<Kitap_kayit, Date> columnAra_bitis;

    @FXML
    private TableColumn<Kitap_kayit, String> columnAra_kitapAd;

    @FXML
    private TableColumn<Kitap_kayit, Integer> columnAra_sayfas;

    @FXML
    private TableColumn<Kitap_kayit, String> columnAra_yazarAd;

    @FXML
    private TableColumn<Kitap_kayit, String> columnAra_yorum;

    @FXML
    private TableView<Kitap_kayit> tableview_arama;

    @FXML
    private TextField txt_aranacak_metin;
    
    @FXML
    void btn_ara_Click(ActionEvent event) {
    	sql = "select * from kitapkayýt where kitapAdi like '%" + txt_aranacak_metin.getText() + "%' or yazarAdi like '%" + txt_aranacak_metin.getText() + "%' or yorumAlinti like '%" + txt_aranacak_metin.getText() + "%' ";
    	 
        try {
        	
        	sorguIfadesi=baglanti.prepareStatement(sql);

            DegerleriGetir2(tableview_arama, sorguIfadesi);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    
   @FXML
    void txt_aranacak_metin_KeyPressed(KeyEvent event) {
    	if(txt_aranacak_metin.getText().equals("")) {
     		sql="select * from kitapkayýt";
     	}else {
     		sql = "select * from kitapkayýt where kitapAdi like '%" + txt_aranacak_metin.getText() + "%' or yazarAdi like '%" + txt_aranacak_metin.getText() + "%' or yorumAlinti like '%" + txt_aranacak_metin.getText() + "%' ";

     	}
          
          DegerleriGetir(tableview_arama,sql);
    }

    Connection baglanti=null;  
    PreparedStatement sorguIfadesi=null;
    ResultSet getirilen=null;
    String sql;
    
    public void DegerleriGetir(TableView tablo, String sql) {
        sql="select *  from kitapkayýt "	;
        ObservableList<Kitap_kayit> kayitlarliste = FXCollections.observableArrayList();
        try {
 		sorguIfadesi=baglanti.prepareStatement(sql);
 		ResultSet getirilen=sorguIfadesi.executeQuery();
 		while(getirilen.next()) {
 			kayitlarliste.add(new Kitap_kayit(getirilen.getString("kitapAdi"),getirilen.getString("yazarAdi"),getirilen.getInt("sayfaSayisi"),getirilen.getDate("baslamaTarih"),getirilen.getDate("bitisTarih"),getirilen.getString("yorumAlinti")));
 		
 			getirilen.getString("kitapAdi");
            getirilen.getString("yazarAdi");
            getirilen.getInt("sayfaSayisi");
            getirilen.getDate("baslamaTarih");
            getirilen.getDate("bitisTarih");
            getirilen.getString("yorumAlinti");
 		
 		}
 		
 	columnAra_kitapAd.setCellValueFactory(new PropertyValueFactory< >("kitapAdi"));
 	columnAra_yazarAd.setCellValueFactory(new PropertyValueFactory< >("yazarAdi"));
 	columnAra_sayfas.setCellValueFactory(new PropertyValueFactory< >("sayfaSayisi"));
 	columnAra_baslama.setCellValueFactory(new PropertyValueFactory< >("baslamaTarih"));
 	columnAra_bitis.setCellValueFactory(new PropertyValueFactory< >("bitisTarih"));
 	columnAra_yorum.setCellValueFactory(new PropertyValueFactory<  >("yorumAlinti"));
 	tableview_arama.setItems(kayitlarliste);
 	
 	} catch (Exception e) {
 		System.out.println(e.getMessage().toString());
 	}
     	
     }
    
    public void DegerleriGetir2(TableView tablo, PreparedStatement sorgu) {
        sql="select *  from kitapkayýt "	;
        ObservableList<Kitap_kayit> kayitlarliste = FXCollections.observableArrayList();
        try {
 		sorguIfadesi=baglanti.prepareStatement(sql);
 		ResultSet getirilen=sorgu.executeQuery();
 		while(getirilen.next()) {
 			kayitlarliste.add(new Kitap_kayit(getirilen.getString("kitapAdi"),getirilen.getString("yazarAdi"),getirilen.getInt("sayfaSayisi"),getirilen.getDate("baslamaTarih"),getirilen.getDate("bitisTarih"),getirilen.getString("yorumAlinti")));
 		}
 		columnAra_kitapAd.setCellValueFactory(new PropertyValueFactory< >("kitapAdi"));
 	 	columnAra_yazarAd.setCellValueFactory(new PropertyValueFactory< >("yazarAdi"));
 	 	columnAra_sayfas.setCellValueFactory(new PropertyValueFactory< >("sayfaSayisi"));
 	 	columnAra_baslama.setCellValueFactory(new PropertyValueFactory< >("baslamaTarih"));
 	 	columnAra_bitis.setCellValueFactory(new PropertyValueFactory< >("bitisTarih"));
 	 	columnAra_yorum.setCellValueFactory(new PropertyValueFactory<  >("yorumAlinti"));
 	 	tableview_arama.setItems(kayitlarliste);
 	
 	} catch (Exception e) {
 		System.out.println(e.getMessage().toString());
 	}
     	
     }
    
    @FXML
    void initialize() {
        sql="select * from kitapkayýt";
        DegerleriGetir(tableview_arama, sql);
        
    }
}
