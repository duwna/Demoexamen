import ui.ClientsController;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT,
                    Main.class.getResourceAsStream("res/myfont.ttf")).deriveFont(Font.PLAIN, 12);
            UIManager.put("Button.font", font);
            UIManager.put("Label.font", font);
            UIManager.put("Table.font", font);
            UIManager.put("TableHeader.font", font);
            UIManager.put("TextField.font", font);
            UIManager.put("PasswordField.font", font);

            UIManager.put("Button.background", "#000000");
            UIManager.put("Label.background", "#000000");
            UIManager.put("Table.background", "#000000");
            UIManager.put("TableHeader.background", "#000000");
            UIManager.put("TextField.background", "#000000");
            UIManager.put("PasswordField.background", "#000000");
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        ClientsController clientsController = new ClientsController();
        clientsController.pack();
        clientsController.setVisible(true);
    }
}
