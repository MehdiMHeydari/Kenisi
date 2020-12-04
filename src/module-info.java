module Kenisi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens org.headroyce.kenisi to javafx.fxml;
    exports org.headroyce.kenisi;
}