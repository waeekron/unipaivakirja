package application;
	
import javafx.scene.layout.Pane;


import javafx.application.Application;
import javafx.stage.Stage;
import unipaivakirja.SailoException;
import unipaivakirja.Sovellus;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("mainView.fxml"));
			final Pane root = (Pane)ldr.load();
			final MainGUIController mainCtrl = (MainGUIController)ldr.getController();
			
			final Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Unipäiväkirja");
			
			Sovellus sovellus = new Sovellus();
			
			try {
				sovellus.lueTiedostosta();
			} catch (SailoException e) {
				System.err.println(e.getMessage());
			}
			
			mainCtrl.setSovellus(sovellus);
			primaryStage.show();
			
		} catch(Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
