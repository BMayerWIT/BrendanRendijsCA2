module com.example.brendanrendijsca2 {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.brendanrendijsca2 to javafx.fxml;
    exports com.example.brendanrendijsca2;
}