package sample;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.Splash;

public class Main extends Application {
public static int duracion=110000;
public static int steps= 1;

    @Override
    public void init() throws Exception {
        for (int i=0; i<duracion; i++){
            double progreso= (100*i)/duracion;
            LauncherImpl.notifyPreloader(this,
                    new Splash.ProgressNotification(progreso));
        }//Llave For
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/Principal.fxml"));
        primaryStage.setTitle("Chalan Compilator");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //launch(args);
        LauncherImpl.launchApplication(Main.class,Splash.class,args);
    }
}
