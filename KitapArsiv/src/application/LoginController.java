package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.isteMySQL.Util.VeritabaniUtil;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import javafx.scene.Node;


public class LoginController {
	
	public LoginController() {
		baglanti= VeritabaniUtil.Baglan();
	}
   
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_giris;

    @FXML
    private Button btn_kayit;
    

    @FXML
    private PasswordField pass_girissifre;

    @FXML
    private PasswordField pass_kayitsifre;

    @FXML
    private TextField txt_giriskulad;

    @FXML
    private TextField txt_kayitkulad;
    
    Connection baglanti=null;
    PreparedStatement sorguIfadesi=null;
    ResultSet getirilen=null; 
    String sql;
    
 

    @FXML
    void btn_giris_Click(ActionEvent event) {
        String kullaniciAdi = txt_giriskulad.getText().trim();
        String sifre = pass_girissifre.getText().trim();
        
        String sql = "SELECT * FROM login WHERE Kul_Ad = ? AND Sifre = ?";
        try {
            PreparedStatement sorguIfadesi = baglanti.prepareStatement(sql);
            sorguIfadesi.setString(1, kullaniciAdi);
            sorguIfadesi.setString(2, sifre);
            
            ResultSet sonuc = sorguIfadesi.executeQuery();
            
            if (sonuc.next()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Kitap Arþiv Otomasyonu");
                alert.setHeaderText("Bilgi Mesajý");
                alert.setContentText("Giriþ Baþarýlý!");
                alert.showAndWait();
                
                AnchorPane root;
				try {
					root = FXMLLoader.load(getClass().getResource("KitapArsiv.fxml"));
					Stage stage = new Stage();
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
                
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Kitap Arþiv Otomasyonu");
                alert.setHeaderText("Hata Mesajý");
                alert.setContentText("Kullanýcý adý veya þifre yanlýþ!");
                alert.showAndWait();
                
            }
			
        } catch (SQLException e) {
            System.out.println("SQL Hatasý: " + e.getMessage());
        }
        }
    

    @FXML
    void btn_kayit_Click(ActionEvent event) {
    
    	    
    	sql = "INSERT INTO login(Kul_ad, Sifre) VALUES (?, ?)";
    	    try {
    	        sorguIfadesi = baglanti.prepareStatement(sql);
    	        sorguIfadesi.setString(1, txt_kayitkulad.getText().trim());
    	        sorguIfadesi.setString(2, pass_kayitsifre.getText().trim());
    	        
    	        int etkilenenSatirSayisi = sorguIfadesi.executeUpdate();
    	        if (etkilenenSatirSayisi > 0) {
    	            Alert alert = new Alert(AlertType.INFORMATION);
    	            alert.setTitle("Kitap Arþiv Otomasyonu");
    	            alert.setHeaderText("Bilgi Mesajý");
    	            alert.setContentText("Kaydýnýz Gerçekleþmiþtir!");
    	            alert.showAndWait();
    	        } else {
    	            Alert alert = new Alert(AlertType.ERROR);
    	            alert.setTitle("Kitap Arþiv Otomasyonu");
    	            alert.setHeaderText("Hata Mesajý");
    	            alert.setContentText("Kayýt iþlemi gerçekleþtirilemedi!");
    	            alert.showAndWait();
    	        }
    	    } catch (Exception e) {
    	        System.out.println(e.getMessage().toString());
    	    }
    			
    	    
    }

    @FXML
    void initialize() {
        

    }

}
