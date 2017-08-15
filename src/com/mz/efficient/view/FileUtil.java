package com.mz.efficient.view;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MZ on 2017/8/14.
 */
public class FileUtil {

    public static boolean copy(File srcFile, File desFile) {
        if (srcFile.isDirectory()) {
            String filepath = srcFile.getAbsolutePath();
            filepath = filepath.replaceAll("\\\\", "/");
            String toFilepath = desFile.getAbsolutePath();
            toFilepath = toFilepath.replaceAll("\\\\", "/");
            int lastIndexOf = filepath.lastIndexOf("/");
            toFilepath = toFilepath + filepath.substring(lastIndexOf, filepath.length());
            File copy = new File(toFilepath);
            //复制文件夹
            if (!copy.exists()) {
                copy.mkdir();
            }
            //遍历文件夹
            for (File f : srcFile.listFiles()) {
                copy(f, copy);
            }
        } else {
            byte[] b = new byte[1024];
            int byteCount;
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                fis = new FileInputStream(srcFile);
                if (desFile.isDirectory()) {
                    String filepath = srcFile.getAbsolutePath();
                    filepath = filepath.replaceAll("\\\\", "/");
                    String toFilepath = desFile.getAbsolutePath();
                    toFilepath = toFilepath.replaceAll("\\\\", "/");
                    int lastIndexOf = filepath.lastIndexOf("/");
                    toFilepath = toFilepath + filepath.substring(lastIndexOf, filepath.length());

                    File newFile = new File(toFilepath);
                    fos = new FileOutputStream(newFile);
                } else {
                    fos = new FileOutputStream(desFile);
                }
                //写文件
                while ((byteCount = fis.read(b)) != -1) {
                    fos.write(b, 0, byteCount);
                }
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static List<String> selectMultipleFiles(Project project, JLabel txtFromPath) {
        VirtualFile[] files = FileChooser.chooseFiles(FileChooserDescriptorFactory.createMultipleFilesNoJarsDescriptor(), txtFromPath, project,
                txtFromPath.getText().isEmpty() ? null : LocalFileSystem.getInstance().findFileByPath(txtFromPath.getText()));
        List<String> pathList = new ArrayList<>();
        if (files.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (VirtualFile file : files) {
                stringBuilder.append(com.intellij.openapi.util.io.FileUtil.toSystemDependentName(file.getPath())).append("\n");
                pathList.add(com.intellij.openapi.util.io.FileUtil.toSystemDependentName(file.getPath()));
            }
            txtFromPath.setText(stringBuilder.substring(0, stringBuilder.length() - 1));
        }
        return pathList;
    }

}
