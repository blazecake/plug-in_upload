package com.mz.efficient.view;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import org.codehaus.jettison.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by MZ on 2017/8/15.
 */
public class PanelHolder {
    private Project project;
    private JPanel panel;
    private JEditorPane uploadEditPane;
    private JEditorPane uploadNewFolderEditPan;
    private JLabel localFilePathLabel;
    private JCheckBox isAddDateCheckBox;

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


    public boolean getjCheckBoxValue() {
        return isAddDateCheckBox.isSelected();
    }

    public PanelHolder invoke() {
        panel = new JPanel();
        panel.setSize(800, 1000);
        panel.setLayout(new GridLayout(4, 2, 20, 20));

        //输入要上传的地址
        JLabel label1 = new JLabel();
        label1.setText("上传路径：");
        label1.setMaximumSize(new Dimension(100, 100));
        panel.add(label1);

        uploadEditPane = new JEditorPane();
        uploadEditPane.setBackground(JBColor.GRAY);
        uploadEditPane.setMinimumSize(new Dimension(200, 100));
        panel.add(uploadEditPane);

        JLabel label2 = new JLabel();
        label2.setText("上传路径新文件夹：");
        panel.add(label2);

        uploadNewFolderEditPan = new JEditorPane();
        uploadNewFolderEditPan.setText("${date}");
        uploadNewFolderEditPan.setBackground(JBColor.GRAY);
        panel.add(uploadNewFolderEditPan);

        isAddDateCheckBox = new JCheckBox();
        isAddDateCheckBox.setText("文件名是否追加日期");
        isAddDateCheckBox.setSelected(true);
        panel.add(isAddDateCheckBox);
        panel.add(new JLabel());

        //显示选择的文件路径
        localFilePathLabel = new JLabel();
        localFilePathLabel.setBackground(JBColor.GRAY);
        localFilePathLabel.setMinimumSize(new Dimension(200, 100));

        JButton selectFileButton = new JButton("选择文件");
        selectFileButton.setPreferredSize(new Dimension(80, 60));
        panel.add(selectFileButton); // 将JButton实例添加到JPanel中
        //点击去选择待上传的文件
        selectFileButton.addActionListener(e -> pathList = FileUtil.selectMultipleFiles(project, localFilePathLabel));

        panel.add(localFilePathLabel);

        restore();
        return this;
    }

    public void save() {
        Properties properties = new Properties();
        properties.put("isWrite", "Y");
        properties.put("uploadEditPane", uploadEditPane.getText());
        properties.put("uploadNewFolderEditPan", uploadNewFolderEditPan.getText());
        properties.put("isAddDateCheckBox", isAddDateCheckBox.isSelected() ? "Y" : "N");
        properties.put("localFilePathLabel", localFilePathLabel.getText());
        properties.put("pathList", new Gson().toJson(pathList));

        File file = new File(System.getProperty("user.home"), ".sendTestPackage");
        try {
            properties.store(new FileOutputStream(file), "send-test-package插件缓存文件");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restore() {
        Properties properties = new Properties();
        try {
            File file = new File(System.getProperty("user.home"), ".sendTestPackage");
            if (file.exists()) {
                properties.load(new FileInputStream(file));
                if (properties.containsKey("isWrite")) {
                    uploadEditPane.setText(properties.getProperty("uploadEditPane"));
                    uploadNewFolderEditPan.setText(properties.getProperty("uploadNewFolderEditPan"));
                    isAddDateCheckBox.setSelected("Y".equals(properties.getProperty("isAddDateCheckBox")));
                    localFilePathLabel.setText(properties.getProperty("localFilePathLabel"));
                    pathList = new Gson().fromJson(properties.getProperty("pathList"), new TypeToken<List<String>>() {
                    }.getType());
                    localFilePathLabel.setText(properties.getProperty("localFilePathLabel"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
