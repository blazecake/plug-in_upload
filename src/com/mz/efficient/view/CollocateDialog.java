package com.mz.efficient.view;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MZ on 2017/8/14.
 */
public class CollocateDialog {

    public static void showDialog(Project project) {
        PanelHolder panelHolder = new PanelHolder(project).invoke();

        DialogBuilder dialog = new DialogBuilder();
        dialog.setTitle("发送测试包");
        dialog.setCenterPanel(panelHolder.getPanel());
        dialog.setOkOperation(() -> {
            String desPath = beforeUpload(panelHolder);
            if (desPath == null) return;
            new Thread(() -> {
                boolean result = true;
                for (String path : panelHolder.getPathList()) {
                    result &= com.mz.efficient.view.FileUtil.copy(new File(path), new File(desPath), panelHolder.getjCheckBoxValue());
                }
                if (result) {
                    panelHolder.save();
                    SwingUtilities.invokeLater(() -> Messages.showMessageDialog("上传成功！", "提示", null));
                }
            }).start();
        });
        dialog.show();
    }

    @Nullable
    private static String beforeUpload(PanelHolder panelHolder) {
        String desPath = panelHolder.getUploadInput();
        if (desPath.length() == 0) {
            Messages.showMessageDialog("请输入上传路径！", "提示", null);
            return null;
        }
        if (panelHolder.getPathList() == null || panelHolder.getPathList().size() == 0) {
            Messages.showMessageDialog("请选择待上传的文件！", "提示", null);
            return null;
        }

        if (panelHolder.getUploadNewFolderInput().length() > 0) {
            String newFolderName = panelHolder.getUploadNewFolderInput();
            if (newFolderName.contains("${date}")) {
                desPath += File.separator + newFolderName.replace("${date}", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            } else {
                desPath += File.separator + newFolderName;
            }
            new File(desPath).mkdirs();
        }
        return desPath;
    }

}
