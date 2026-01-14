module it.unisa.diem.oop.testservice {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens it.unisa.diem.oop.testservice to javafx.fxml;
    exports it.unisa.diem.oop.testservice;
}