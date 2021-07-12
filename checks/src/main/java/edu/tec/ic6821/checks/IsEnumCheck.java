package edu.tec.ic6821.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IsEnumCheck extends AbstractCheck {

    private static final String LOG_TYPE_SHOULD_BE_ENUM = "Type should be an enum: ";

    private Set<String> typeNames;

    public void setTypeNames(String... typeNames) {
        this.typeNames = new HashSet<>(Arrays.asList(typeNames));
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.IDENT};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        super.visitToken(ast);

        if (typeNames.contains(ast.getText())) {
            DetailAST parent = ast.getParent();
            if (parent.getText().endsWith("_DEF")
                && parent.getType() != TokenTypes.ENUM_DEF) {
                log(parent.getLineNo(), LOG_TYPE_SHOULD_BE_ENUM + ast.getText());
            }
        }
    }
}
