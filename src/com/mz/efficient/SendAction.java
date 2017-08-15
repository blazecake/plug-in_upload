package com.mz.efficient;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.MessageDialogBuilder;
import com.intellij.openapi.ui.Messages;
import com.mz.efficient.view.CollocateDialog;

import javax.swing.*;

/**
 * Created by Administrator on 2017/8/9.
 */
public class SendAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        CollocateDialog.showDialog(e.getProject());
    }
}


