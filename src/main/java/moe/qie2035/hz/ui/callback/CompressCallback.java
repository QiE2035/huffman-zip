package moe.qie2035.hz.ui.callback;

import lombok.AllArgsConstructor;
import moe.qie2035.hz.service.callback.CompressProgress;
import moe.qie2035.hz.service.callback.ICallback;
import moe.qie2035.hz.ui.MainController;

@AllArgsConstructor
public class CompressCallback implements ICallback<CompressProgress> {
    private final MainController mainController;

    @Override
    public void onProgress(CompressProgress progressInfo) {
        mainController.log.appendText("正在" + progressInfo.desc + "..." + System.lineSeparator());
    }

    @Override
    public void onFinish() {
        mainController.log.appendText("完成!" + System.lineSeparator() + System.lineSeparator());
        mainController.changeAllDisable(false);
        mainController.onInChange();
    }
}
