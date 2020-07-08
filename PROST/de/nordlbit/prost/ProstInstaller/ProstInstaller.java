/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

/**
 * 
 */
package de.nordlbit.prost.ProstInstaller;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author n1117412
 *
 */
public class ProstInstaller extends Application
{
	/**
	 * 
	 * @param argv
	 */
	public static void main(String argv[])
	{
		launch(argv);
	}
	    
	/**
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) 
	{
        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 300, 250));
        //primaryStage.getScene().getStylesheets().add("resources/css/ProstInstallerStyles.css");
	        primaryStage.setTitle("ProstInstaller");
	        
	        Label lvLabelDurchfuehren = new Label("Durchfuehren");
	        Label lvLabelBeschreibung = new Label("Beschreibung");
	        Label lvLabelDatenbank = new Label("Datenbank");
	        
	        CheckBox lvCheckboxSicherungErstellen = new CheckBox();
	        CheckBox lvCheckboxSicherungEinspielen = new CheckBox();
	        CheckBox lvCheckboxDatenbankLoeschen = new CheckBox();
	        VBox lvBoxCheckBoxen = new VBox();
	        lvBoxCheckBoxen.getChildren().add(lvCheckboxSicherungErstellen);
	        lvBoxCheckBoxen.getChildren().add(lvCheckboxSicherungEinspielen);
	        lvBoxCheckBoxen.getChildren().add(lvCheckboxDatenbankLoeschen);
	        
	        Label lvLabelSicherungErstellen= new Label("Sicherung erstellen");
	        Label lvLabelSicherungEinspielen = new Label("Sicherung einspielen");
	        Label lvLabelDatenbankLoeschen = new Label("Datenbank loeschen");	        

	        VBox lvBoxLabel = new VBox();
	        lvBoxLabel.getChildren().add(lvLabelSicherungErstellen);
	        lvBoxLabel.getChildren().add(lvLabelSicherungEinspielen);
	        lvBoxLabel.getChildren().add(lvLabelDatenbankLoeschen);
	        	        
	        ComboBox lvComboboxSicherungErstellen = new ComboBox();
	        ComboBox lvComboboxSicherungEinspielen = new ComboBox();
	        ComboBox lvComboboxDatenbankLoeschen = new ComboBox();
	        
	        VBox lvBoxCombobox= new VBox();
	        lvBoxCombobox.getChildren().add(lvComboboxSicherungErstellen);
	        lvBoxCombobox.getChildren().add(lvComboboxSicherungEinspielen);
	        lvBoxCombobox.getChildren().add(lvComboboxDatenbankLoeschen);
	      
	        HBox lvBoxCenter = new HBox();
	        lvBoxCenter.getChildren().add(lvBoxCheckBoxen);
	        lvBoxCenter.getChildren().add(lvBoxLabel);
	        lvBoxCenter.getChildren().add(lvBoxCombobox);
	      
	        lvBoxCenter.getStyleClass().add("hbox");

	        GridPane lvCenterGrid = new GridPane();
	        lvCenterGrid.setGridLinesVisible(true);
	        lvCenterGrid.setConstraints(lvLabelDurchfuehren, 0, 0);
	        lvCenterGrid.getColumnConstraints().add(new ColumnConstraints(200));
	        lvCenterGrid.setConstraints(lvCheckboxSicherungErstellen, 0, 1);
	        lvCenterGrid.getChildren().add(lvCheckboxSicherungErstellen);

	        lvCenterGrid.setConstraints(lvCheckboxSicherungEinspielen, 0, 2);
	        lvCenterGrid.getChildren().add(lvCheckboxSicherungEinspielen);

	        lvCenterGrid.setConstraints(lvCheckboxDatenbankLoeschen, 0, 3);
	        lvCenterGrid.getChildren().add(lvCheckboxDatenbankLoeschen);

	        lvCenterGrid.getChildren().add(lvLabelDurchfuehren);
	        lvCenterGrid.setConstraints(lvLabelBeschreibung, 1, 0);
	        lvCenterGrid.getColumnConstraints().add(new ColumnConstraints(200)); 

	        lvCenterGrid.getChildren().add(lvLabelBeschreibung);
	        lvCenterGrid.setConstraints(lvLabelDatenbank, 2, 0);
	        lvCenterGrid.getColumnConstraints().add(new ColumnConstraints(200)); 

	        lvCenterGrid.getChildren().add(lvLabelDatenbank);
	        lvCenterGrid.getStyleClass().add("grid");
	        //lvCenterGrid.setPadding(new Insets(100));
	        
	        /*
	        HBox lvBoxSicherungEinspielen = new HBox();
	        lvBoxSicherungEinspielen.getChildren().add(lvCheckboxSicherungEinspielen);
	        lvBoxSicherungEinspielen.getChildren().add(lvLabelSicherungEinspielen);
	        lvBoxSicherungEinspielen.getChildren().add(lvComboboxSicherungEinspielen);
	        
	        HBox lvBoxDatenbankLoeschen = new HBox();
	        lvBoxDatenbankLoeschen.getChildren().add(lvCheckboxDatenbankLoeschen);
	        lvBoxDatenbankLoeschen.getChildren().add(lvLabelDatenbankLoeschen);
	        lvBoxDatenbankLoeschen.getChildren().add(lvComboboxDatenbankLoeschen);
	       
	        
	        VBox lvBoxCenter = new VBox();
	        lvBoxCenter.getChildren().add(lvBoxSicherungErstellen);
	        lvBoxCenter.getChildren().add(lvBoxSicherungEinspielen);
	        lvBoxCenter.getChildren().add(lvBoxDatenbankLoeschen);
	         */ 
	        
	        BorderPane lvBorderPane = new BorderPane();
	        lvBorderPane.setTop(new Label("Bitte Aktionen auswaehlen..."));
	        lvBorderPane.setCenter(lvCenterGrid);
	        
	        Button lvButtonStarten = new Button("Starten");
	        Button lvButtonExit = new Button("Exit");
	        HBox lvButtonBox = new HBox(4.0);
	        lvButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
	        lvButtonBox.getChildren().add(lvButtonStarten);
	        lvButtonBox.getChildren().add(lvButtonExit);
	        
	        
	        lvBorderPane.setBottom(lvButtonBox);

	        root.getChildren().add(lvBorderPane);

	        primaryStage.show();
	}

}
