package com.mz.efficient.view;

import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by MZ on 2017/8/15.
 */
public class PanelHolder {
    private Project project;
    private JPanel panel;
    private JEditorPane uploadEditPane;
    private JEditorPane uploadNewFolderEditPan;
    private JLabel localFilePathLabel;

    private java.util.List<String> pathList;

    public PanelHolder(Project project) {
        this.project = project;
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getUploadInput() {
        return uploadEditPane.getText();
    }

    public String getUploadNewFolderInput() {
        return uploadNewFolderEditPan.getText();
    }

    public List<String> getPathList() {
        return pathList;
    }

    public PanelHolder invoke() {
        panel = new JPanel();
        panel.setSize(600, 1000);
        panel.setLayout(new GridLayout(3, 2, 20, 20));

        //输入要上传的地址
        JLabel label1 = new JLabel();
        label1.setText("上传路径：");
        panel.add(label1);

        uploadEditPane = new JEditorPane();
        uploadEditPane.setText("");
        uploadEditPane.setBackground(JBColor.GRAY);
        panel.add(uploadEditPane);

        JLabel label2 = new JLabel();
        label2.setText("上传路径新文件夹：");
        panel.add(label2);

        uploadNewFolderEditPan = new JEditorPane();
        uploadNewFolderEditPan.setText("居民端_${date}");
        uploadNewFolderEditPan.setBackground(JBColor.GRAY);
        panel.add(uploadNewFolderEditPan);

        //显示选择的文件路径
        localFilePathLabel = new JLabel();
        localFilePathLabel.setBackground(JBColor.GRAY);

        JButton selectFileButton = new JButton("选择文件");
        selectFileButton.setSize(80, 40);
        panel.add(selectFileButton); // 将JButton实例添加到JPanel中
        //点击去选择待上传的文件
        selectFileButton.addActionListener(e -> pathList = FileUtil.selectMultipleFiles(project, localFilePathLabel));

        panel.add(localFilePathLabel);
        return this;
    }
}
