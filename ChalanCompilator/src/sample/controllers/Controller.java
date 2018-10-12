package sample.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.reactfx.Subscription;
import sample.Constants.Configs;

import java.awt.*;
import java.io.File;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sample.Constants.Configs.PATTERN;
import static sample.Constants.Configs.computeHighlighting;
import static sample.Constants.Configs.sampleCode;

public class Controller extends Application {
    private Stage stage;
    @FXML private HBox panesote;
    @FXML
    TextArea txtConsola;


    CodeArea codeArea = new CodeArea();

    @FXML protected void initialize(){
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.replaceText(0, 0, sampleCode);
        codeArea.setPrefSize(1200,330);
        Subscription cleanupWhenNoLongerNeedIt = codeArea
                .multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe(ignore -> codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText())));
        panesote.getChildren().add(codeArea);
    }//llave load


    public void evtSalir (ActionEvent event){ System.exit(0); }
    public void evtAbrir (ActionEvent event){
        FileChooser of= new FileChooser();
        of.setTitle("Abrir archivo ccf");
        FileChooser.ExtensionFilter filtro= new FileChooser.ExtensionFilter("Archivos .ccf","*.ccf");
        of.getExtensionFilters().add(filtro);
        File file = of.showOpenDialog(stage);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
    }
    public void ejecutar(ActionEvent event){
        compilar();

    }//llave ejecutarrrr
    public void compilar (){
        txtConsola.setText(" ");
        long tInicial=System.currentTimeMillis();

        String texto=codeArea.getText();
        String[] renglones=texto.split("\\n");
        for(int x=0;x<renglones.length;x++){
            for(int y=0; y< Configs.EXPRESIONES.length;y++){
                Pattern patron=Pattern.compile(Configs.EXPRESIONES[y]);
                Matcher matcher=patron.matcher(renglones[x]);
                if(!matcher.matches()){
                    txtConsola.setText( txtConsola.getText() +"\n" +"Error de sintaxys en la linea "+(x+1));
                }
            }//llave for y
        }//llave for
        long tFinal=System.currentTimeMillis()-tInicial;
        txtConsola.setText(txtConsola.getText()+ "\n" +
                "Compilado en :"+tFinal+" milisegundos");
    }//llave compilar
}
