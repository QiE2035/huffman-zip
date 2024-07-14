package moe.qie2035.hz.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import moe.qie2035.hz.service.processor.Compressor;
import moe.qie2035.hz.service.processor.Decompressor;
import moe.qie2035.hz.ui.callback.CompressCallback;
import moe.qie2035.hz.ui.callback.DecompressCallback;

import static javafx.scene.input.TransferMode.ANY;
import static moe.qie2035.hz.core.Constants.SUFFIX;


public class MainController {
    @FXML
    public TextArea log;
    @FXML
    private Button compressBtn, decompressBtn;
    @FXML
    private TextField in, out;
    private CompressCallback compressCallback;
    private DecompressCallback decompressCallback;
    private boolean disable = false;

    @FXML
    private void initialize() {
        compressCallback = new CompressCallback(this);
        decompressCallback = new DecompressCallback(this);
//        log.setTextFormatter(new TextFormatter<String>(change -> null));
    }

    @FXML
    private void compress() {
        final String in = this.in.getText().trim(), out = this.out.getText().trim();
        changeAllDisable(true);
        new Compressor(in, out, compressCallback).start();
    }

    @FXML
    private void decompress() {
        final String in = this.in.getText().trim(), out = this.out.getText().trim();
        changeAllDisable(true);
        new Decompressor(in, out, decompressCallback).start();
    }

    @FXML
    public void onInChange() {
        final String in = this.in.getText().trim();
        compressBtn.setDisable(in.isEmpty() || in.endsWith(SUFFIX));
        decompressBtn.setDisable(!in.endsWith(SUFFIX));
    }

    public void changeAllDisable(final boolean disable) {
        this.disable = disable;
        compressBtn.setDisable(disable);
        decompressBtn.setDisable(disable);
        in.setDisable(disable);
        out.setDisable(disable);
    }

    @FXML
    private void onDragDropped(DragEvent event) {
        final Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            final String path = db.getFiles().get(0).getPath();
            in.setText(path);
            if (path.endsWith(SUFFIX)) {
                out.setText(path.substring(0, path.length() - SUFFIX.length()));
            } else {
                out.setText(path + SUFFIX);
            }
            onInChange();
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void onDragOver(DragEvent event) {
        if (!disable && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(ANY);
        }
        event.consume();
    }

}
