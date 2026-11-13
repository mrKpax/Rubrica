package com.rubrica;

import ui.LoginDialog;
import ui.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Frame invisibile di supporto per il dialogo modale
            JFrame dummyFrame = new JFrame();
            LoginDialog loginDialog = new LoginDialog(dummyFrame);
            loginDialog.setVisible(true);

            // Se login va a buon fine, chiude frame fittizio e apre MainFrame
            if (loginDialog.isLoginRiuscito()) {
                dummyFrame.dispose();
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}