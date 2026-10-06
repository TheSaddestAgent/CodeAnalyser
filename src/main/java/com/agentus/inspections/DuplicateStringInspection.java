package com.agentus.inspections;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DuplicateStringInspection extends LocalInspectionTool {

    private static final int MIN_REPETITIONS = 3;

    @Override
    @NotNull
    public PsiElementVisitor buildVisitor(
            @NotNull ProblemsHolder holder,
            boolean isOnTheFly
    ) {
        Map<String, Integer> occurrences = new HashMap<>();

        return new PsiElementVisitor() {
            @Override
            public void visitElement(@NotNull PsiElement element) {
                if (!(element instanceof PsiLiteralExpression literal)) {
                    return;
                }

                Object value = literal.getValue();

                if (!(value instanceof String stringValue)
                        || stringValue.isBlank()) {
                    return;
                }

                int count = occurrences.getOrDefault(stringValue, 0) + 1;
                occurrences.put(stringValue, count);

                if (count == MIN_REPETITIONS) {
                    holder.registerProblem(
                            literal,
                            "Строка повторяется не менее "
                                    + MIN_REPETITIONS
                                    + " раз. Рекомендуется вынести её в константу."
                    );
                }
            }
        };
    }
}
