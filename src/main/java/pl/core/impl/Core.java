package pl.core.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.authentication.ILoginManager;
import pl.core.ICore;
import pl.gui.IGUI;

@RequiredArgsConstructor
@Component
public class Core implements ICore {
    private final IGUI gui;
    private final ILoginManager loginManager;

    @Override
    public void run() {
        boolean running = false;

        while (!running) {
            String choice = this.gui.askLoginOrRegister();
            switch (choice) {
                case "1":
                    running = this.loginManager.login();
                    break;
                case "2":
                    this.loginManager.register();
                    break;
                case "3":
                    return;
                default:
                    this.gui.showResultMessage("Invalid choice. Try again.");
                    break;
            }
        }

        while (running) {
            switch (this.gui.showMenuAndReadChoice()) {
                case "1":
                    this.gui.showProducts();
                    break;
                case "2":
                    this.gui.addProductToCart(this.gui.readProductId());
                    break;
                case "3":
                    this.gui.showCart();
                    break;
                case "4":
                    this.gui.Buy();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    this.gui.showResultMessage("Incorrect choice!!");
                    break;
            }
        }
    }
}
