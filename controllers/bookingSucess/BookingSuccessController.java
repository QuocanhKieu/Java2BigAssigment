package moviebookingapp.controllers.bookingSucess;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BookingSuccessController {


    public void backToMovieList(ActionEvent actionEvent) {
        try{
//                System.out.println("in the button");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../fxml/moviecinema.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            currentStage.setTitle("Movies Cinema");
            currentStage.setScene(new Scene(root, 1600, 900));

//                System.out.println("here btn Clicked");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setDarkMode(ActionEvent actionEvent) {
    }

    public void setLightMode(ActionEvent actionEvent) {
    }
}
