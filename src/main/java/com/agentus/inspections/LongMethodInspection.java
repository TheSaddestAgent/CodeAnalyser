package com.agentus.inspections;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

public class LongMethodInspection extends LocalInspectionTool {

    private static final int MAX_METHOD_LINES = 30;

    @Override
    public @NotNull JavaElementVisitor buildVisitor(
            @NotNull ProblemsHolder holder,
            boolean isOnTheFly
    ) {
        return new JavaElementVisitor() {
            @Override
            public void visitMethod(@NotNull PsiMethod method) {
                super.visitMethod(method);

                PsiCodeBlock body = method.getBody();

                if (body == null) {
                    return;
                }

                if (method.getContainingFile() == null) {
                    return;
                }

                var document = method.getContainingFile()
                        .getFileDocument();

                int startLine = document.getLineNumber(
                        method.getTextRange().getStartOffset()
                );

                int endLine = document.getLineNumber(
                        method.getTextRange().getEndOffset()
                );

                int lineCount = endLine - startLine + 1;

                if (lineCount > MAX_METHOD_LINES
                        && method.getNameIdentifier() != null) {

                    holder.registerProblem(
                            method.getNameIdentifier(),
                            "Слишком длинный метод: "
                                    + lineCount
                                    + " строк. Максимум: "
                                    + MAX_METHOD_LINES
                    );
                }
            }
        };
    }
}