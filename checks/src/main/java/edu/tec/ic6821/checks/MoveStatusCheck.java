package edu.tec.ic6821.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MoveStatusCheck extends AbstractCheck {

    private static final String LOG_NO_INVALID_POSITION_REQUIRED = "[IC-6821] La abstracción de Row y Column vuelve innecesario el status de INVALID_POSITION.";

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.ENUM_DEF};
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
        if (!"MoveStatus".equals(ident.getText())) {
            return;
        }

        DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        DetailAST child = objBlock.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
                ident = child.findFirstToken(TokenTypes.IDENT);
                if ("INVALID_POSITION".equals(ident.getText())) {
                    log(ident.getLineNo(), LOG_NO_INVALID_POSITION_REQUIRED);
                }
            }
            child = child.getNextSibling();
        }
    }


}
