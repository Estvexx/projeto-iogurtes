package com.empresa.iogurtes.gestaoiogurtes.desktop.paginas;

import com.empresa.iogurtes.gestaoiogurtes.desktop.GestaoIogurtes;
import com.empresa.iogurtes.gestaoiogurtes.desktop.utils.AppAware;
import com.empresa.iogurtes.gestaoiogurtes.desktop.utils.NavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller for PaginaLogin.fxml.
 *
 * <p>All layout is declared in the FXML.  This class handles only
 * the login action (currently navigates straight to Dashboard).
 *
 * <p>Moved from the root {@code com.empresa.iogurtes.gestaoiogurtes.desktop} package into
 * {@code com.empresa.iogurtes.gestaoiogurtes.desktop.paginas} to match the pages folder convention.
 */
public class PaginaLogin implements AppAware {

    @FXML private TextField campoUser;
    @FXML private PasswordField campoPass;
    @FXML private Button btnEntrar;

    private GestaoIogurtes app;

    // ── AppAware ──────────────────────────────────────────────────
    @Override
    public void setApp(GestaoIogurtes app) {
        this.app = app;
    }

    // ── FXML handler ──────────────────────────────────────────────
    @FXML
    private void handleEntrar() {
        NavigationHelper.navigateTo(app, "/fxml/paginas/Dashboard.fxml");
    }
}
