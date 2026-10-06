package com.agentus.inspections;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiIfStatement;
import com.intellij.psi.PsiLoopStatement;
import org.jetbrains.annotations.NotNull;

public class DeepNestingInspection extends LocalInspectionTool {

    private static final int MAX_NESTING_LEVEL = 3;

    @Override
    public @NotNull PsiElementVisitor buildVisitor(
            @NotNull ProblemsHolder holder,
            boolean isOnTheFly
    ) {
        return new PsiElementVisitor() {
            @Override
            public void visitElement(@NotNull PsiElement element) {
                if (!(element instanceof PsiIfStatement)
                        && !(element instanceof PsiLoopStatement)) {
                    return;
                }

                int nestingLevel = calculateNestingLevel(element);

                if (nestingLevel > MAX_NESTING_LEVEL) {
                    holder.registerProblem(
                            element,
                            "Слишком глубокая вложенность: "
                                    + nestingLevel
                    );
                }
            }
        };
    }

    private int calculateNestingLevel(PsiElement element) {
        int level = 1;
        PsiElement parent = element.getParent();

        while (parent != null) {
            if (parent instanceof PsiIfStatement
                    || parent instanceof PsiLoopStatement) {
                level++;
            }

            parent = parent.getParent();
        }

        return level;
    }
}
