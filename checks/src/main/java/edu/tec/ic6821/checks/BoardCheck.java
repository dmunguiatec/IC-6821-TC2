package edu.tec.ic6821.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class BoardCheck extends AbstractCheck {
    private static final String LOG_ONLY_APPLY_MOVE_ALLOWED = "[IC-6821] La abstracción correcta del tablero solo requiere del método applyMove. El método toString se hereda de java.lang.Object, no es necesario incluirlo en la interfaz.";

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.INTERFACE_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[0];
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }

    @Override
    public void visitToken(DetailAST ast) {
        super.visitToken(ast);

        DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        if (!"Board".equals(ident.getText())) {
            return;
        }

        DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        DetailAST child = objBlock.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.METHOD_DEF) {
                ident = child.findFirstToken(TokenTypes.IDENT);
                if (!"applyMove".equals(ident.getText())) {
                    log(ident.getLineNo(), LOG_ONLY_APPLY_MOVE_ALLOWED);
                }
            }
            child = child.getNextSibling();
        }
    }
}
