package edu.tec.ic6821.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class CountingBoardCheck extends AbstractCheck {

    private static final String LOG_NO_MATRIX_REQUIRED = "[IC-6821] La implementación de esta clase no requiere de matrices.";

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CLASS_DEF};
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

        DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        if (!"CountingBoard".equals(ident.getText())) {
            return;
        }

        DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        DetailAST child = objBlock.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.VARIABLE_DEF) {
                checkNoMatrix(child);

            }
            child = child.getNextSibling();
        }


    }

    private void checkNoMatrix(DetailAST variableDef) {
        DetailAST type = variableDef.findFirstToken(TokenTypes.TYPE);
        DetailAST arrayDeclarator = type.findFirstToken(TokenTypes.ARRAY_DECLARATOR);

        if (arrayDeclarator != null) {
            DetailAST nextSibling = arrayDeclarator.getNextSibling();
            if (nextSibling != null && nextSibling.getType() == TokenTypes.ARRAY_DECLARATOR) {
                log(variableDef.getLineNo(), LOG_NO_MATRIX_REQUIRED);
            }
        }
    }
}
