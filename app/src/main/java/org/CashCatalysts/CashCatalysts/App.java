package org.CashCatalysts.CashCatalysts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.CashCatalysts.CashCatalysts.controllers.TransactionsController;

import java.util.Objects;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("forms/Transactions.fxml")));
        TransactionsController controller = new TransactionsController();
        loader.setController(controller);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Cash Catalysts");
        stage.setScene(scene);
        stage.show();
    }
}
