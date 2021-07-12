package edu.tec.ic6821.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BoardImplCheck extends AbstractCheck {

    private static final String LOG_NO_GETTERS_SETTERS = "[IC-6821] el uso de getters y setters en esta clase rompe el principio de encapsulamiento.";
    private static final String LOG_NO_PUBLIC_METHODS = "[IC-6821] los únicos métodos públicos requeridos para esta clase son: ";
    private static final String LOG_NO_PARAMS_IN_CTOR = "[IC-6821] el constructor de esta clase no debería recibir valores pues sus estructuras de datos pueden ser autocontenidas y no hay necesidad de inyectar datos externos.";
    private static final String LOG_INTERFACE_REQUIRED = "[IC-6821] esta clase debe implementar una interfaz para cumplir con el principio de polimorfismo.";
    private static final String LOG_CLASS_IMPLEMENTS_BOARD = "[IC-6821] esta clase deben implementar a la interfaz Board para lograr polimorfismo con dicha clase.";

    private Set<String> allowedMethods;
    private Set<String> classNames;

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

    public void setAllowedMethods(String... methods) {
        this.allowedMethods = new HashSet<>(Arrays.asList(methods));
    }

    public void setClassNames(String... classNames) {
        this.classNames = new HashSet<>(Arrays.asList(classNames));
    }

    @Override
    public void visitToken(DetailAST ast) {
        super.visitToken(ast);

        // we only want to check the classes in classNames set
        DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        if (!classNames.contains(ident.getText())) {
            return;
        }

        DetailAST implementsClause = ast.findFirstToken(TokenTypes.IMPLEMENTS_CLAUSE);
        if (implementsClause == null) {
            log(ast.getLineNo(), LOG_INTERFACE_REQUIRED);
        } else {
            ident = implementsClause.findFirstToken(TokenTypes.IDENT);
            if (!"Board".equals(ident.getText())) {
                log(implementsClause.getLineNo(), LOG_CLASS_IMPLEMENTS_BOARD);
            }
        }

        DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        DetailAST child = objBlock.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.CTOR_DEF) {
                checkConstructor(child);
            }
            if (child.getType() == TokenTypes.METHOD_DEF) {
                checkMethod(child);
            }
            child = child.getNextSibling();
        }
    }

    private void checkConstructor(DetailAST ctorDef) {
        DetailAST modifiers = ctorDef.findFirstToken(TokenTypes.MODIFIERS);
        DetailAST publicModifier = modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC);
        // ignore non public constructors
        if (publicModifier == null) {
            return;
        }

        DetailAST parameters = ctorDef.findFirstToken(TokenTypes.PARAMETERS);
        if (parameters.hasChildren()) {
            log(ctorDef.getLineNo(), LOG_NO_PARAMS_IN_CTOR);
        }
    }

    private void checkMethod(DetailAST methodDef) {
        DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
        DetailAST publicModifier = modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC);
        // ignore non public methods
        if (publicModifier == null) {
            return;
        }

        DetailAST ident = methodDef.findFirstToken(TokenTypes.IDENT);
        if (ident.getText().startsWith("get")
            || ident.getText().startsWith("set")) {
            log(methodDef.getLineNo(), LOG_NO_GETTERS_SETTERS);
        }

        if (!allowedMethods.contains(ident.getText())) {
            log(methodDef.getLineNo(), LOG_NO_PUBLIC_METHODS + allowedMethods.toString());
        }
    }
}
