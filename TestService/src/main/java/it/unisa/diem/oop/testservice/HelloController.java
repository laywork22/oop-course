package it.unisa.diem.oop.testservice;

import javafx.beans.binding.Bindings;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class HelloController {
    @FXML
    private Label label;
    @FXML
    private ProgressIndicator progressInd;
    @FXML
    private Label status;

    @FXML
    public void initialize() {

        status.setText("Status bar");

    }

    @FXML
    protected void onHelloButtonClick() {

        TestService service = new TestService();

        label.textProperty().bind(service.valueProperty());

        progressInd.progressProperty().bind(service.progressProperty());

        progressInd.visibleProperty().bind(service.runningProperty());

        status.textProperty().bind(service.messageProperty());

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                label.textProperty().unbind();

                label.setText("Task completato: " + service.getValue());
            }
        });

        service.start();
    }


}
