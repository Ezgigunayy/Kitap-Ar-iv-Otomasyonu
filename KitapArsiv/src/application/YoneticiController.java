package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.isteMySQL.Util.VeritabaniUtil;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.*;

public class YoneticiController {
	public YoneticiController() {
		baglanti = VeritabaniUtil.Baglan();
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Tab tab_dosya;

    @FXML
    private Tab tab_duzenle;
    
    @FXML
    private Button btn_duzenle_kul_guncelle;

    @FXML
    private Button btn_duzenle_kul_sil;
    
    @FXML
    private Button btn_yenile;

    @FXML
    private TextField tabduzenle_kul_Ad;

    @FXML
    private PasswordField tabduzenle_sifre;

    @FXML
    private TableView<Login_kayit> tableview_dosya;
    
    @FXML
    private TableColumn<Login_kayit, String> column_dosya_kulad;

    @FXML
    private TableColumn<Login_kayit, String> column_dosya_sifre;
    
    private ObservableList<Login_kayit> kullaniciListesi = FXCollections.observableArrayList();
    
    Connection baglanti=null;
    String sql;
    PreparedStatement sorgu=null;
    ResultSet getirilen=null;
    
	
    public class Main extends Application {
    	@Override
    	public void start(Stage primaryStage) {
    		try {
    			AnchorPane root = FXMLLoader.load(getClass().getResource("Login.fxml"));
				
				Stage stage = new Stage();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    	
    	public static void main(String[] args) {
    		launch(args);
    	}
    }

    @FXML
    void btn_duzenle_kul_guncelle(ActionEvent event) {
    	sql="select * from login where Kul_ad=? and Sifre=? ";
    	
      Login_kayit secilikullanici = tableview_dosya.getSelectionModel().getSelectedItem();
      if (secilikullanici != null) {
          String yeniKullaniciAdi = tabduzenle_kul_Ad.getText();
          String yeniSifre = tabduzenle_sifre.getText();
          
          secilikullanici.setKul_Ad(yeniKullaniciAdi);
          secilikullanici.setSifre(yeniSifre);
          
          tableview_dosya.refresh();
          
          Alert alert = new Alert(AlertType.INFORMATION);
  	    alert.setTitle("Kitap Arþiv Otomasyonu");
  	    alert.setHeaderText("Bilgi Mesajý");
  	    alert.setContentText("Kullanýcý bilgileri güncellenmiþtir!");
  	    alert.showAndWait();
      }

    }

    @FXML
    void btn_duzenle_kul_sil(ActionEvent event) {
    	sql = "DELETE FROM login WHERE Kul_ad = ? AND Sifre = ?";
    	
        Login_kayit seciliKullanici = tableview_dosya.getSelectionModel().getSelectedItem();
         if (seciliKullanici != null) {
         	try {
             kullaniciListesi.remove(seciliKullanici);
            
             
             Connection baglanti = VeritabaniUtil.Baglan();
             PreparedStatement preparedStatement = baglanti.prepareStatement(sql); 
            preparedStatement.setString(1, seciliKullanici.getKul_ad());
            preparedStatement.setString(2, seciliKullanici.getSifre());
            
            preparedStatement.executeUpdate();
            
            tableview_dosya.refresh();
         	
             Alert alert = new Alert(AlertType.INFORMATION);
       	    alert.setTitle("Kitap Arþiv Otomasyonu");
       	    alert.setHeaderText("Bilgi Mesajý");
       	    alert.setContentText("Kullanýcý bilgileri silinmiþtir!");
       	    alert.showAndWait();
       	    }catch (Exception e) {
       	    	Alert alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Hata");
                 alert.setHeaderText("Kullanýcý silinirken bir hata oluþtu.");
                 alert.setContentText("Hata: " + e.getMessage());
                 alert.showAndWait();
 			}        	
         }  else {
 			System.out.println("Kullanýcý bilgileri silinemedi.");
 		}
       }
    
    @FXML
    void btn_yenile_Click(ActionEvent event) {
    	sql = "select * from login";
    	Degerler(tableview_dosya, sql);
    }


    public void Degerler(TableView tablo, String sql) {
    	sql = "select * from login ";
        ObservableList<Login_kayit> kayitlarliste = FXCollections.observableArrayList();
        try {
 		sorgu=baglanti.prepareStatement(sql);
 		ResultSet getirilen=sorgu.executeQuery();
 		while(getirilen.next()) {
 			kayitlarliste.add(new Login_kayit(getirilen.getString("Kul_ad"),getirilen.getString("Sifre")));
 		}
 		
 	column_dosya_kulad.setCellValueFactory(new PropertyValueFactory< >("Kul_ad"));
 	column_dosya_sifre.setCellValueFactory(new PropertyValueFactory< >("Sifre"));
 	
 	tableview_dosya.setItems(kayitlarliste);

 	} catch (Exception e) {
 		System.out.println(e.getMessage().toString());
 	}
     	
     }
    
    @FXML
    void initialize() {
    	sql = "select * from login";
    	Degerler(tableview_dosya, sql);
   
    }

}

