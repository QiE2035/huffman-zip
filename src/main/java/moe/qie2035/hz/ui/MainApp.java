package moe.qie2035.hz.ui;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class MainApp extends Application {
    public static void launch(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        final URL url = getClass().getResource("main.fxml");
        final Parent root = FXMLLoader.load(Objects.requireNonNull(url));
        stage.setScene(new Scene(root));
        stage.setTitle("哈夫曼单文件压缩");
        stage.show();
    }
}
