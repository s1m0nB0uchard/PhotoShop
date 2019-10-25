import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        ImageView tabImage[] = {new ImageView("default.jpg"), new ImageView("image1.jpg"), new ImageView("image2.jpg"), new ImageView("image4.jpg")};
        BorderPane bp = new BorderPane();


        //affichage Slider
        Slider saturation = slider();
        Slider luminosite = slider();
        Slider contraste = slider();
        Slider teinte = slider();
        Slider tabSlider[] = {saturation, luminosite, contraste, teinte};


        //affichage des labels des sliders
        Label saturationTxt = new Label("Saturation");
        Label luminositeTxt = new Label("Luminosité");
        Label contrasteTxt = new Label("Contraste");
        Label teinteTxt = new Label("Teinte");


        //Allignement au centre du BorderPane
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(saturationTxt, saturation, luminositeTxt, luminosite, contrasteTxt, contraste, teinteTxt, teinte);
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(getImage(tabImage[0], bp), vb);
        bp.setCenter(hb);

        //menus
        Menu fichiers = new Menu("Fichiers");
        Menu action = new Menu("Actions");
        Menu charger = new Menu("Charger une image");
        fichiers.getItems().add(charger);
        MenuBar menubar = new MenuBar(fichiers, action);

        MenuItem restart = new MenuItem("Réinitialiser");
        restart.setOnAction((n) -> {
            backToNormal(tabSlider);
            hb.getChildren().remove(0);
            hb.getChildren().add(0, getImage(tabImage[0], bp));
            notification("Données réinitialisées", bp);
        });
        MenuItem image1 = new MenuItem("Image 1");
        MenuItem image2 = new MenuItem("Image 2");
        MenuItem image3 = new MenuItem("Image 3");
        charger.getItems().addAll(image1, image2, image3);
        action.getItems().add(restart);
        menuAction(image1, image2, image3, bp, tabImage, hb, tabSlider);
        bp.setTop(menubar);


        //Clic droit
        hb.setOnContextMenuRequested((event ->new ContextMenu(action,fichiers).show(hb, event.getScreenX(), event.getScreenY())));


        //action des controles
        ColorAdjust color = new ColorAdjust();
        saturation.valueProperty().addListener((observable, oldValue, newValue) -> {
            notification("\tSaturation changée à " + Math.round((double) newValue), bp);
            color.setSaturation((double) newValue / 50);
            hb.getChildren().get(0).setEffect(color);
        });
        luminosite.valueProperty().addListener((observable, oldValue, newValue) -> {
            notification("\tLuminosité changée à " + Math.round((double) newValue), bp);
            color.setBrightness((double) newValue / 50);
            hb.getChildren().get(0).setEffect(color);
        });
        contraste.valueProperty().addListener((observable, oldValue, newValue) -> {
            notification("\tContraste changé à " + Math.round((double) newValue), bp);
            color.setContrast((double) newValue / 50);
            hb.getChildren().get(0).setEffect(color);
        });
        teinte.valueProperty().addListener((observable, oldValue, newValue) -> {
            notification("\tTeinte changée à " + Math.round((double) newValue), bp);
            color.setHue((double) newValue / 50);
            hb.getChildren().get(0).setEffect(color);
        });

        //tooltips
        Tooltip s = new Tooltip("Saturation : Diminue ou augmente l'intensité des couleurs");
        saturationTxt.setTooltip(s);

        Tooltip l = new Tooltip("Luminosité : Rend l'image plus claire ou plus sombre");
        luminositeTxt.setTooltip(l);

        Tooltip c = new Tooltip("Contraste : Diminue ou augmente la différence entre les couleurs");
        contrasteTxt.setTooltip(c);

        Tooltip t = new Tooltip("Teinte : Change la teinte (couleur) de l'image");
        teinteTxt.setTooltip(t);


        Scene scene = new Scene(bp);
        window.setFullScreen(true);
        window.setScene(scene);
        window.show();


    }

    private ImageView getImage(ImageView iv, BorderPane bp) {
        iv.setFitWidth(450);
        iv.setPreserveRatio(true);
        notification(iv.getImage().getUrl() + " chargé", bp);

        return iv;
    }

    private Slider slider() {
        Slider slider = new Slider(-50, 50, 0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(20);
        slider.setMinorTickCount(1);
        return slider;
    }

    private void menuAction(MenuItem m1, MenuItem m2, MenuItem m3, BorderPane bp, ImageView iv[], HBox hb, Slider slider[]) {


        m1.setOnAction((n) -> {
            hb.getChildren().remove(0);
            hb.getChildren().add(0, getImage(iv[1], bp));
            backToNormal(slider);
        });
        m2.setOnAction((n) -> {
            hb.getChildren().remove(0);
            hb.getChildren().add(0, getImage(iv[2], bp));
            backToNormal(slider);
        });
        m3.setOnAction((n) -> {
            hb.getChildren().remove(0);
            hb.getChildren().add(0, getImage(iv[3], bp));
            backToNormal(slider);
        });
    }

    private void notification(String info, BorderPane bp) {
        Label notif = new Label(info);
        bp.setBottom(notif);
    }

    private void backToNormal(Slider tabSlider[]) {
        for (Slider slider : tabSlider) slider.setValue(0);
    }


}
