package com.agentus.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class AnalyseProjectAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        PsiFile psiFile = event.getData(com.intellij.openapi.actionSystem.CommonDataKeys.PSI_FILE);

        if (psiFile == null) {
            Messages.showWarningDialog(
                    event.getProject(),
                    "Открытый Java-файл не найден.",
                    "Code Analyser"
            );
            return;
        }

        String fileName = psiFile.getName();

        Messages.showInfoMessage(
                event.getProject(),
                "Файл " + fileName
                        + " будет проанализирован инспекциями IntelliJ IDEA.\n"
                        + "Результаты отображаются прямо в редакторе.",
                "Code Analyser"
        );
    }
}
