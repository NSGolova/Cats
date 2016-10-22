package sample;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller
{
    @FXML
    ImageView forCat;

    @FXML
    public void cat()
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
        ImageView catView = new ImageView("http://thecatapi.com/api/images/get?format=src&type=gif&size=full");
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