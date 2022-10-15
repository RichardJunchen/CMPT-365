package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.compress.ImageUtils;
import sample.compress.LosslessCompression;
import sample.compress.LossyCompress;
import sample.compress.PixImage;

import java.io.File;


public class Main extends Application {


    BorderPane borderPane;
    Image image;
    Pane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final double WIDTH = 500.0, HEIGHT = 300.0;
        //create menu bar
        MenuBar menuBar = new MenuBar();
        //create the file menu
        Menu fileMenu = new Menu("File");
        // add items to file menu
        MenuItem openItem = new MenuItem("Open and lossless compress");
        MenuItem openLossyItem = new MenuItem("Open and lossy compress");
        MenuItem closeItem = new MenuItem("Exit");
        SeparatorMenuItem sep = new SeparatorMenuItem();
        SeparatorMenuItem sep1 = new SeparatorMenuItem();
        fileMenu.getItems().addAll(openItem, sep1, openLossyItem, sep, closeItem);
        //close the stage
        closeItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.close();
            }
        });
        //open the file
        openItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fc = new FileChooser();
                FileChooser.ExtensionFilter tifFilter = new FileChooser.ExtensionFilter("TIF Files", "*.tif");
                fc.getExtensionFilters().addAll(tifFilter);
                File selectFile = fc.showOpenDialog(primaryStage);
                if (selectFile != null) {
                    final double X1 = 30.0, Y1 = 400, X2 = 30.0, Y2 = 420.0;
                    javafx.scene.text.Text text = new Text(X1, Y1, "Creater : Junchen Li");
                    File imageFile = selectFile;
                    PixImage pixImage = ImageUtils.readTIFFPix(selectFile.getName());
                    Canvas canvas = new Canvas(pixImage.getWidth() * 3, pixImage.getHeight() * 3);

                    GraphicsContext gc = canvas.getGraphicsContext2D();

                    for (int index = 0; index < pixImage.getWidth(); index++) {
                        for (int j = 0; j < pixImage.getHeight(); j++) {
                            java.awt.Color c = new java.awt.Color(pixImage.getRed(index, j), pixImage.getGreen(index, j), pixImage.getBlue(index, j));
                            gc.setFill(new Color(c.getRed() / 255.0, c.getGreen() / 255.0, c.getBlue() / 255.0, c.getAlpha() / 255.0));
                            gc.fillRect(50 + index, 50 + j, 1, 1);
                        }
                    }
                    gc.strokeText("origin", 150, 20);
                    LosslessCompression.writeTo(pixImage, "lossless.tiff");
                    File losslessFile = new File("lossless.tiff");

                    pixImage = LosslessCompression.readFrom("lossless.tiff");
                    for (int index = 0; index < pixImage.getWidth(); index++) {
                        for (int j = 0; j < pixImage.getHeight(); j++) {
                            java.awt.Color c = new java.awt.Color(pixImage.getRed(index, j), pixImage.getGreen(index, j), pixImage.getBlue(index, j));
                            gc.setFill(new Color(c.getRed() / 255.0, c.getGreen() / 255.0, c.getBlue() / 255.0, c.getAlpha() / 255.0));
                            gc.fillRect(50 * 2 + pixImage.getWidth() + index, 50 + j, 1, 1);
                        }
                    }
                    gc.strokeText(String.format("Lossless Compression: %.2f", (losslessFile.length() * 100.0 / selectFile.length())) , 150 + pixImage.getWidth(), 20);
                    borderPane.setCenter(canvas);

                }
            }
        });
        openLossyItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fc = new FileChooser();
                FileChooser.ExtensionFilter tifFilter = new FileChooser.ExtensionFilter("TIF Files", "*.tif");
                fc.getExtensionFilters().addAll(tifFilter);
                File selectFile = fc.showOpenDialog(primaryStage);
                if (selectFile != null) {
                    final double X1 = 30.0, Y1 = 400, X2 = 30.0, Y2 = 420.0;
                    javafx.scene.text.Text text = new Text(X1, Y1, "Creater : Junchen Li");
                    File imageFile = selectFile;
                    PixImage pixImage = ImageUtils.readTIFFPix(imageFile.getName());
                    Canvas canvas = new Canvas(pixImage.getWidth() * 3, pixImage.getHeight() * 3);

                    GraphicsContext gc = canvas.getGraphicsContext2D();

                    for (int index = 0; index < pixImage.getWidth(); index++) {
                        for (int j = 0; j < pixImage.getHeight(); j++) {
                            java.awt.Color c = new java.awt.Color(pixImage.getRed(index, j), pixImage.getGreen(index, j), pixImage.getBlue(index, j));
                            gc.setFill(new Color(c.getRed() / 255.0, c.getGreen() / 255.0, c.getBlue() / 255.0, c.getAlpha() / 255.0));
                            gc.fillRect(50 + index, 50 + j, 1, 1);
                        }
                    }
                    gc.strokeText("origin", 150, 20);
                    LossyCompress.writeTo(pixImage, "lossy.tiff");
                    File lossyFile = new File("lossy.tiff");
                    pixImage = LossyCompress.readFrom("lossy.tiff");
                    for (int index = 0; index < pixImage.getWidth(); index++) {
                        for (int j = 0; j < pixImage.getHeight(); j++) {
                            java.awt.Color c = new java.awt.Color(pixImage.getRed(index, j), pixImage.getGreen(index, j), pixImage.getBlue(index, j));
                            gc.setFill(new Color(c.getRed() / 255.0, c.getGreen() / 255.0, c.getBlue() / 255.0, c.getAlpha() / 255.0));
                            gc.fillRect(50 * 2 + pixImage.getWidth() + index, 50 + j, 1, 1);
                        }
                    }
                    gc.strokeText(String.format("Lossy Compress %.2f",(lossyFile.length() * 100.0 / selectFile.length())), 150 + pixImage.getWidth(), 20);
                    borderPane.setCenter(canvas);

                }
            }
        });
        menuBar.getMenus().addAll(fileMenu);
        borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        // the primaryStage information
        final double X1 = 30.0, Y1 = 150.0, X2 = 30.0, Y2 = 170.0;
        final double FONT_SIZE = 40;
        final double SCALE_QTR = 0.5;
        javafx.scene.text.Text text = new Text(X1, Y1, "Creater : Junchen Li");
        javafx.scene.text.Text text2 = new Text(X2, Y2, "Student ID : 301385486");
        text.setFont(new Font(FONT_SIZE));
        text.setScaleX(SCALE_QTR);
        text.setScaleY(SCALE_QTR);
        text2.setFont(new Font(FONT_SIZE));
        text2.setScaleX(SCALE_QTR);
        text2.setScaleY(SCALE_QTR);
        VBox hb = new VBox(text, text2);
        borderPane.setCenter(hb);
        Scene scene = new Scene(borderPane, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}