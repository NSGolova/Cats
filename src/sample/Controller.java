package sample;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller
{
    String next = "712635";
    Boolean safemode = false;

    @FXML
    Label nsfw;

    public void togle()
    {
        nsfw.setVisible(!nsfw.isVisible());
        safemode = !safemode;
    }

    @FXML
    public void cat()
    {
        String show = "";

        if (safemode || Math.random() > 0.3)
        {
            show = "http://thecatapi.com/api/images/get?format=src&type=gif&size=full";

        }
        else
        {
            URL url;
            InputStream is = null;
            BufferedReader br;
            String line = "", all = "";

            try {
                url = new URL("http://porn.gifland.us/" + next);
                is = url.openStream();  // throws an IOException
                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    all += line + "\n";
                }
            } catch (MalformedURLException mue) {
                mue.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                } catch (IOException ioe) {
                    // nothing to see here
                }
            }

            String regexp = "(?<=<div class=\"gf__body gf__body--image gf__body--big gf__body--unloaded\">" + "\n" +
                    "    <a href=\"(......)\">\n" +
                    "        <img src=\")(.*)(?=class=\"img-fluid center-block gf__body__img gf__body__img--big\" alt=\"Super gif\")";
            Pattern pattern = Pattern.compile(regexp);
            Matcher matcher = pattern.matcher(all);

            if (matcher.find()) {
                next = "" + matcher.group(1);
                show = matcher.group().substring(0, matcher.group().length() - 2);
            }
        }

        show(show);

    }

    public void show(String url)
    {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Pane pane = new Pane();
        final Delta dragDelta = new Delta();
        pane.setOnMousePressed(mouseEvent -> {
            dragDelta.x = stage.getX() - mouseEvent.getScreenX();
            dragDelta.y = stage.getY() - mouseEvent.getScreenY();
        });
        pane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() + dragDelta.x);
            stage.setY(mouseEvent.getScreenY() + dragDelta.y);
        });
        ImageView catView = new ImageView(url);
        pane.getChildren().add(catView);

        ImageView close = new ImageView("closeDark.png");
        close.setPreserveRatio(true);
        close.setSmooth(true);
        close.setFitWidth(30);
        close.setFitHeight(30);
        close.setLayoutX(3);
        close.setLayoutY(3);
        close.setOnMousePressed(event -> stage.close());
        close.setOnMouseEntered(event -> close.setImage(new Image("close.png")));
        close.setOnMouseExited(event -> close.setImage(new Image("closeDark.png")));
        pane.getChildren().add(close);

        stage.setScene(new Scene(pane));
        stage.show();
    }
}

class Delta { double x, y; }