package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.isteMySQL.Util.VeritabaniUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Date;

import java.sql.*;
import java.time.LocalDate;

public class KitapArsivController {
	public KitapArsivController() {
		baglanti = VeritabaniUtil.Baglan();
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button btn_arama;


    @FXML
    private Button btn_guncelle;

    @FXML
    private Button btn_kaydet;

    @FXML
    private Button btn_sil;
    
    @FXML
    private Button btn_yenile;

    
    @FXML
    private TableView<Kitap_kayit> tableview_kitaparsiv;

    @FXML
    private TableColumn<Kitap_kayit, Date> column_baslama;

    @FXML
    private TableColumn<Kitap_kayit, Date> column_bitis;

    @FXML
    private TableColumn<Kitap_kayit, String> column_kitapAd;

    @FXML
    private TableColumn<Kitap_kayit, Integer> column_sayfaSay;

    @FXML
    private TableColumn<Kitap_kayit, String> column_yazarAd;
    
    @FXML
    private TableColumn<Kitap_kayit, String> column_yorum;

    @FXML
    private DatePicker date_baslama;

    @FXML
    private DatePicker date_bitis;

    @FXML
    private TextArea textarea_yorum;

    @FXML
    private TextField txt_kitapAd;

    @FXML
    private TextField txt_sayfaSay;

    @FXML
    private TextField txt_yazarAd;
    
    Connection baglanti=null;
    PreparedStatement sorguIfadesi;
    ResultSet getirilen=null;
    String sql;

    @FXML
    void btn_yenile_Click(ActionEvent event) {
    	 sql="select * from kitaparsiv";
         DegerleriGetir(tableview_kitaparsiv,sql);
    }
    
    @FXML
    void btn_arama_Click(ActionEvent event) {
    	AnchorPane root;
		try {
			root = FXMLLoader.load(getClass().getResource("Arama.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void btn_guncelle_Click(ActionEvent event) {
    
    	String sql = "UPDATE kitapkayýt SET yazarAdi = ?, baslamaTarih = ?, bitisTarih = ?, sayfaSayisi = ?, yorumAlinti = ? WHERE kitapAdi = ?";
        try {
            PreparedStatement sorguIfadesi = baglanti.prepareStatement(sql);
            
            String kitapAd = txt_kitapAd.getText();
            String yazarAd = txt_yazarAd.getText();
            Date baslamaTarih = java.sql.Date.valueOf(date_baslama.getValue());
            Date bitisTarih = java.sql.Date.valueOf(date_bitis.getValue());
            int sayfaSayisi = Integer.parseInt(txt_sayfaSay.getText());
            String yorumAlinti = textarea_yorum.getText();

            sorguIfadesi.setString(1, yazarAd);
            sorguIfadesi.setDate(2, (java.sql.Date) baslamaTarih);
            sorguIfadesi.setDate(3, (java.sql.Date) bitisTarih);
            sorguIfadesi.setInt(4, sayfaSayisi);
            sorguIfadesi.setString(5, yorumAlinti);
            sorguIfadesi.setString(6, kitapAd);

            sorguIfadesi.executeUpdate();
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Kitap Arþiv Otomasyonu");
            alert.setHeaderText("Bilgi Mesajý");
            alert.setContentText("Bilgileriniz Güncellenmiþtir!");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    	
    }

    @FXML
    void btn_kaydet_Click(ActionEvent event) {
    	sql="INSERT INTO kitapkayýt (kitapAdi, yazarAdi, baslamaTarih, bitisTarih, sayfaSayisi, yorumAlinti) VALUES (?, ?, ?, ?, ?, ?)";
    	try {
            String kitapAd = txt_kitapAd.getText();
            String yazarAd = txt_yazarAd.getText();
            Date baslamaTarih = java.sql.Date.valueOf(date_baslama.getValue());
            Date bitisTarih = java.sql.Date.valueOf(date_bitis.getValue());
            int sayfaSayisi = Integer.parseInt(txt_sayfaSay.getText());
            String yorumAlinti = textarea_yorum.getText();

           
            sorguIfadesi = baglanti.prepareStatement(sql);
            sorguIfadesi.setString(1, kitapAd);
            sorguIfadesi.setString(2, yazarAd);
            sorguIfadesi.setDate(3, (java.sql.Date) baslamaTarih);
            sorguIfadesi.setDate(4, (java.sql.Date) bitisTarih);
            sorguIfadesi.setInt(5, sayfaSayisi);
            sorguIfadesi.setString(6, yorumAlinti);
            sorguIfadesi.executeUpdate();
    	 
    	Alert alert = new Alert(AlertType.INFORMATION);
    	    alert.setTitle("Kitap Arþiv Otomasyonu");
    	    alert.setHeaderText("Bilgi Mesajý");
    	    alert.setContentText("Bilgileriniz Kaydolmuþtur!");
    	    alert.showAndWait();
    	}catch (Exception e) {
			System.out.println(e.getMessage().toString());
		}
    }

    @FXML
    void btn_sil_Click(ActionEvent event) {
    	
    	sql="select * from kitapkayýt";
    	 try {
    	        String deleteQuery = "DELETE FROM kitapkayýt WHERE kitapAdi = ?";
    	        
    	        String kitapAd = txt_kitapAd.getText();
    	        sorguIfadesi = baglanti.prepareStatement(deleteQuery);
    	        sorguIfadesi.setString(1, kitapAd);
    	        sorguIfadesi.executeUpdate();

    	        String yazarAd = txt_yazarAd.getText();
    	        deleteQuery = "DELETE FROM kitapkayýt WHERE yazarAdi = ?";
    	        sorguIfadesi = baglanti.prepareStatement(deleteQuery);
    	        sorguIfadesi.setString(1, yazarAd);
    	        sorguIfadesi.executeUpdate();
    	        
    	        Date baslamaTarih= java.sql.Date.valueOf(date_baslama.getValue());
    	        deleteQuery = "DELETE FROM kitapkayýt WHERE baslamaTarih = ?";
    	        sorguIfadesi = baglanti.prepareStatement(deleteQuery);
    	        sorguIfadesi.setDate(1, (java.sql.Date) baslamaTarih);
    	        sorguIfadesi.executeUpdate();
    	        
    	        Date bitisTarih= java.sql.Date.valueOf(date_bitis.getValue());
    	        deleteQuery = "DELETE FROM kitapkayýt WHERE bitisTarih = ?";
    	        sorguIfadesi = baglanti.prepareStatement(deleteQuery);
    	        sorguIfadesi.setDate(1, (java.sql.Date) bitisTarih);
    	        sorguIfadesi.executeUpdate();
    	        
    	        int sayfaSayisi = Integer.parseInt(txt_sayfaSay.getText());
    	        deleteQuery = "DELETE FROM kitapkayýt WHERE sayfaSayisi = ?";
    	        sorguIfadesi = baglanti.prepareStatement(deleteQuery);
    	        sorguIfadesi.setInt(1, sayfaSayisi);
    	        sorguIfadesi.executeUpdate();
    	        
    	        String yorumAlinti= textarea_yorum.getText();
    	        deleteQuery = "DELETE FROM kitapkayýt WHERE yorumAlinti = ?";
    	        sorguIfadesi = baglanti.prepareStatement(deleteQuery);
    	        sorguIfadesi.setString(1, yorumAlinti);
    	        sorguIfadesi.executeUpdate();
    	        
    	        Alert alert = new Alert(AlertType.INFORMATION);
    	        alert.setTitle("Kitap Arþiv Otomasyonu");
    	        alert.setHeaderText("Bilgi Mesajý");
    	        alert.setContentText("Kayýt(lar) Silindi!");
    	        alert.showAndWait();
    	    } catch (Exception e) {
    	        System.out.println(e.getMessage().toString());
    	    }
          }
    
    public void DegerleriGetir(TableView tablo, String sql) {
        sql="select *  from kitapkayýt "	;
        ObservableList<Kitap_kayit> kayitlarliste = FXCollections.observableArrayList();
        try {
 		sorguIfadesi=baglanti.prepareStatement(sql);
 		ResultSet getirilen=sorguIfadesi.executeQuery();
 		while(getirilen.next()) {
 			kayitlarliste.add(new Kitap_kayit(getirilen.getString("kitapAdi"),getirilen.getString("yazarAdi"),getirilen.getInt("sayfaSayisi"),getirilen.getDate("baslamaTarih"),getirilen.getDate("bitisTarih"),getirilen.getString("yorumAlinti")));
 		}
 		
 	column_kitapAd.setCellValueFactory(new PropertyValueFactory< >("kitapAdi"));
 	column_yazarAd.setCellValueFactory(new PropertyValueFactory< >("yazarAdi"));
    column_baslama.setCellValueFactory(new PropertyValueFactory< >("baslamaTarih"));
 	column_bitis.setCellValueFactory(new PropertyValueFactory< >("bitisTarih"));
 	column_sayfaSay.setCellValueFactory(new PropertyValueFactory< >("sayfaSayisi"));
 	column_yorum.setCellValueFactory(new PropertyValueFactory<  >("yorumAlinti"));
    tableview_kitaparsiv.setItems(kayitlarliste);
 
 	} catch (Exception e) {
 		System.out.println(e.getMessage().toString());
 	}
     	
     }
    
    
    @FXML
    void initialize() {
        sql="select * from kitaparsiv";
        DegerleriGetir(tableview_kitaparsiv,sql);
        
        date_baslama.setValue(LocalDate.now());
        date_bitis.setValue(LocalDate.now());

    }

}
