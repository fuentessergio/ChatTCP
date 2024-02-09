module com.infantaelena.chattcp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.infantaelena.chattcp to javafx.fxml;
    exports com.infantaelena.chattcp;
    exports com.infantaelena.chattcp.cliente;
    opens com.infantaelena.chattcp.cliente to javafx.fxml;

}