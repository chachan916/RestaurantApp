package com.restaurant.restaurantapp;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RestaurantController {

    public TabPane menuTabPane;
    @FXML private CheckBox bCb1, bCb2, bCb3, bCb4, bCb5;
    @FXML private CheckBox bCb6, bCb7, bCb8, bCb9, bCb10;
    @FXML private RadioButton bRb1, bRb2, bRb3;
    @FXML private ComboBox<String> basothoDrinkCombo;
    @FXML private CheckBox iCb1, iCb2, iCb3, iCb4, iCb5;
    @FXML private CheckBox iCb6, iCb7, iCb8, iCb9, iCb10;
    @FXML private CheckBox iCb11, iCb12, iCb13, iCb14, iCb15;
    @FXML private RadioButton iRb1, iRb2, iRb3;
    @FXML private ComboBox<String> italianDrinkCombo;
    @FXML private TextField cashPaidField;
    @FXML private Label totalLabel;
    @FXML private Label changeLabel;
    @FXML private Label transactionTimeLabel;
    @FXML private Label dateLabel;
    @FXML private TextArea selectedItemsArea;
    @FXML private Button totalBtn, changeBtn, resetBtn, exitBtn;

    private RestaurantModel model;
    private double totalAmount = 0;

    private final String[] BDIN_NAMES = {
            "Nama le Joala (Meat and Sorghum Beer)",
            "Papa le Moroho (Pap and Greens)",
            "Khoho le Papa (Chicken and Pap)",
            "Lihobe (Dried Beans Stew)",
            "Lipabi (Samp and Beans)",
            "Motoho (Fermented Porridge)",
            "Nama ea Nku (Mutton Stew)",
            "Sechu (Tripe with Pap)",
            "Moroho oa Setso (Wild Spinach)",
            "Lisaoana (Grilled Offal Skewers)"
    };
    private final double[] BDIN_PRICES = {55, 45, 60, 40, 42, 35, 70, 50, 30, 48};

    private final String[] BDES_NAMES = {
            "Leqebekoane (Sesotho Candy)",
            "Mabele Pudding (Sorghum Pudding)",
            "Morula Fruit Custard"
    };
    private final double[] BDES_PRICES = {20, 25, 28};

    private final String[] IDIN_NAMES = {
            "Spaghetti Carbonara", "Lasagne al Forno", "Margherita Pizza",
            "Pepperoni Pizza", "Penne Arrabbiata", "Osso Buco (Braised Veal)",
            "Branzino al Forno (Roasted Sea Bass)", "Ribollita (Tuscan Bean Soup)",
            "Risotto ai Funghi (Mushroom Risotto)", "Caprese Salad",
            "Pollo alla Parmigiana", "Bistecca alla Fiorentina (T-Bone)",
            "Scampi al Aglio (Garlic Prawns)", "Gnocchi al Gorgonzola",
            "Tagliatelle al Ragu (Bolognese)"
    };
    private final double[] IDIN_PRICES = {85,90,75,85,78,120,110,65,88,55,95,150,105,82,88};

    private final String[] IDES_NAMES = {"Tiramisu", "Panna Cotta", "Gelato (2 Scoops)"};
    private final double[] IDES_PRICES = {45, 38, 35};

    @FXML
    public void initialize() {
        model = new RestaurantModel();

        dateLabel.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        basothoDrinkCombo.setItems(FXCollections.observableArrayList(
                "Joala (Traditional Sorghum Beer) - M18.00",
                "Maheu (Fermented Maize Drink) - M15.00",
                "Morula Juice - M20.00",
                "Baobab Water - M16.00",
                "Metsi a Chesang (Hot Ginger Tea) - M12.00",
                "Lerotse Juice (Melon Juice) - M18.00",
                "Molapo Water (Still Water) - M10.00",
                "Moseme (Sour Milk Drink) - M14.00",
                "Letsoai Smoothie (Salt Plum) - M22.00",
                "Fermented Milk (Amasi) - M16.00"
        ));

        italianDrinkCombo.setItems(FXCollections.observableArrayList(
                "Chianti Classico (Red Wine) - M65.00",
                "Pinot Grigio (White Wine) - M60.00",
                "Prosecco (Sparkling Wine) - M70.00",
                "Espresso - M22.00",
                "Cappuccino - M28.00",
                "Limoncello - M40.00",
                "Aperol Spritz - M55.00",
                "San Pellegrino (Sparkling Water) - M25.00",
                "Negroni Cocktail - M58.00",
                "Amaretto on the Rocks - M48.00"
        ));

        ToggleGroup bDessertGroup = new ToggleGroup();
        bRb1.setToggleGroup(bDessertGroup);
        bRb2.setToggleGroup(bDessertGroup);
        bRb3.setToggleGroup(bDessertGroup);

        ToggleGroup iDessertGroup = new ToggleGroup();
        iRb1.setToggleGroup(iDessertGroup);
        iRb2.setToggleGroup(iDessertGroup);
        iRb3.setToggleGroup(iDessertGroup);

        CheckBox[] allCheckBoxes = {
                bCb1,bCb2,bCb3,bCb4,bCb5,bCb6,bCb7,bCb8,bCb9,bCb10,
                iCb1,iCb2,iCb3,iCb4,iCb5,iCb6,iCb7,iCb8,iCb9,iCb10,
                iCb11,iCb12,iCb13,iCb14,iCb15
        };
        for (CheckBox cb : allCheckBoxes) cb.setOnAction(e -> updateTotal());

        RadioButton[] allRadios = {bRb1,bRb2,bRb3,iRb1,iRb2,iRb3};
        for (RadioButton rb : allRadios) rb.setOnAction(e -> updateTotal());

        basothoDrinkCombo.setOnAction(e -> updateTotal());
        italianDrinkCombo.setOnAction(e -> updateTotal());

        setButtonIcon(totalBtn,  "🧾", "Show Total");
        setButtonIcon(changeBtn, "💵", "Calculate Change");
        setButtonIcon(resetBtn,  "🔄", "Reset");
        setButtonIcon(exitBtn,   "❌", "Exit");

        totalBtn.setOnAction(e -> {
            updateTotal();
            showInfo("Total Amount", "Your current total is: M" + String.format("%.2f", totalAmount));
        });
        changeBtn.setOnAction(e -> handlePayment());
        resetBtn.setOnAction(e -> handleReset());
        exitBtn.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Exit Application");
            confirm.setHeaderText(null);
            confirm.setContentText("Are you sure you want to exit?");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) System.exit(0);
            });
        });
    }

    private void setButtonIcon(Button btn, String symbol, String labelText) {
        Text icon = new Text(symbol);
        icon.setStyle("-fx-font-size: 20;");
        Label lbl = new Label(labelText);
        lbl.setStyle("-fx-text-fill: white; -fx-font-size: 11; -fx-font-weight: bold;");
        VBox graphic = new VBox(2, icon, lbl);
        graphic.setAlignment(Pos.CENTER);
        btn.setText("");
        btn.setGraphic(graphic);
        btn.setStyle(btn.getStyle() + " -fx-padding: 8 20;");
    }

    private void updateTotal() {
        totalAmount = 0;
        List<String> lines = new ArrayList<>();

        CheckBox[] bDin = {bCb1,bCb2,bCb3,bCb4,bCb5,bCb6,bCb7,bCb8,bCb9,bCb10};
        for (int i = 0; i < bDin.length; i++) {
            if (bDin[i].isSelected()) {
                totalAmount += BDIN_PRICES[i];
                lines.add(BDIN_NAMES[i] + "  ->  M" + String.format("%.2f", BDIN_PRICES[i]));
            }
        }

        RadioButton[] bDes = {bRb1,bRb2,bRb3};
        for (int i = 0; i < bDes.length; i++) {
            if (bDes[i].isSelected()) {
                totalAmount += BDES_PRICES[i];
                lines.add(BDES_NAMES[i] + "  ->  M" + String.format("%.2f", BDES_PRICES[i]));
            }
        }

        if (basothoDrinkCombo.getValue() != null) {
            double price = parseDrinkPrice(basothoDrinkCombo.getValue());
            totalAmount += price;
            lines.add(basothoDrinkCombo.getValue().split(" - ")[0].trim()
                    + "  ->  M" + String.format("%.2f", price));
        }

        CheckBox[] iDin = {iCb1,iCb2,iCb3,iCb4,iCb5,iCb6,iCb7,
                iCb8,iCb9,iCb10,iCb11,iCb12,iCb13,iCb14,iCb15};
        for (int i = 0; i < iDin.length; i++) {
            if (iDin[i].isSelected()) {
                totalAmount += IDIN_PRICES[i];
                lines.add(IDIN_NAMES[i] + "  ->  M" + String.format("%.2f", IDIN_PRICES[i]));
            }
        }

        RadioButton[] iDes = {iRb1,iRb2,iRb3};
        for (int i = 0; i < iDes.length; i++) {
            if (iDes[i].isSelected()) {
                totalAmount += IDES_PRICES[i];
                lines.add(IDES_NAMES[i] + "  ->  M" + String.format("%.2f", IDES_PRICES[i]));
            }
        }

        if (italianDrinkCombo.getValue() != null) {
            double price = parseDrinkPrice(italianDrinkCombo.getValue());
            totalAmount += price;
            lines.add(italianDrinkCombo.getValue().split(" - ")[0].trim()
                    + "  ->  M" + String.format("%.2f", price));
        }

        totalLabel.setText("M" + String.format("%.2f", totalAmount));

        if (lines.isEmpty()) {
            selectedItemsArea.setText("No items selected yet.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) sb.append(line).append("\n");
            selectedItemsArea.setText(sb.toString());
        }
    }

    private double parseDrinkPrice(String value) {
        try {
            String[] parts = value.split(" - M");
            if (parts.length == 2) return Double.parseDouble(parts[1].trim());
            parts = value.split(" - ");
            return Double.parseDouble(parts[parts.length - 1].trim().replace("M", ""));
        } catch (Exception e) { return 0; }
    }

    private void handlePayment() {
        if (totalAmount == 0) {
            showError("No Items Selected", "Please select at least one item before calculating change.");
            return;
        }
        String cashText = cashPaidField.getText().trim();
        if (cashText.isEmpty()) {
            showError("Missing Amount", "Please enter the cash tendered amount.");
            cashPaidField.requestFocus();
            return;
        }
        double cash;
        try {
            cash = Double.parseDouble(cashText);
        } catch (NumberFormatException ex) {
            showError("Invalid Amount", "Cash tendered must be a valid number (e.g. 150.00).");
            cashPaidField.clear();
            cashPaidField.requestFocus();
            return;
        }
        if (cash <= 0) {
            showError("Invalid Amount", "Cash tendered must be greater than zero.");
            return;
        }
        double change = cash - totalAmount;
        if (change < 0) {
            showError("Insufficient Payment",
                    "Amount tendered (M" + String.format("%.2f", cash) + ") is less than "
                            + "the total (M" + String.format("%.2f", totalAmount) + ").\n"
                            + "You still need M" + String.format("%.2f", Math.abs(change)) + " more.");
            return;
        }

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        transactionTimeLabel.setText(time);
        changeLabel.setText("M" + String.format("%.2f", change));
        model.saveTransaction(totalAmount, cash, change, time, buildItemList());

        showInfo("Transaction Complete",
                "Total:    M" + String.format("%.2f", totalAmount) + "\n"
                        + "Paid:     M" + String.format("%.2f", cash) + "\n"
                        + "Change:   M" + String.format("%.2f", change) + "\n\n"
                        + "Receipt saved to transaction_log.txt\nEnjoy your meal! Kea leboha!");
    }

    private List<String> buildItemList() {
        List<String> list = new ArrayList<>();
        CheckBox[] bDin = {bCb1,bCb2,bCb3,bCb4,bCb5,bCb6,bCb7,bCb8,bCb9,bCb10};
        for (int i = 0; i < bDin.length; i++)
            if (bDin[i].isSelected())
                list.add(BDIN_NAMES[i] + " @ M" + String.format("%.2f", BDIN_PRICES[i]));
        RadioButton[] bDes = {bRb1,bRb2,bRb3};
        for (int i = 0; i < bDes.length; i++)
            if (bDes[i].isSelected())
                list.add(BDES_NAMES[i] + " @ M" + String.format("%.2f", BDES_PRICES[i]));
        if (basothoDrinkCombo.getValue() != null) {
            double p = parseDrinkPrice(basothoDrinkCombo.getValue());
            list.add(basothoDrinkCombo.getValue().split(" - ")[0].trim() + " @ M" + String.format("%.2f", p));
        }
        CheckBox[] iDin = {iCb1,iCb2,iCb3,iCb4,iCb5,iCb6,iCb7,
                iCb8,iCb9,iCb10,iCb11,iCb12,iCb13,iCb14,iCb15};
        for (int i = 0; i < iDin.length; i++)
            if (iDin[i].isSelected())
                list.add(IDIN_NAMES[i] + " @ M" + String.format("%.2f", IDIN_PRICES[i]));
        RadioButton[] iDes = {iRb1,iRb2,iRb3};
        for (int i = 0; i < iDes.length; i++)
            if (iDes[i].isSelected())
                list.add(IDES_NAMES[i] + " @ M" + String.format("%.2f", IDES_PRICES[i]));
        if (italianDrinkCombo.getValue() != null) {
            double p = parseDrinkPrice(italianDrinkCombo.getValue());
            list.add(italianDrinkCombo.getValue().split(" - ")[0].trim() + " @ M" + String.format("%.2f", p));
        }
        return list;
    }

    private void handleReset() {
        CheckBox[] all = {
                bCb1,bCb2,bCb3,bCb4,bCb5,bCb6,bCb7,bCb8,bCb9,bCb10,
                iCb1,iCb2,iCb3,iCb4,iCb5,iCb6,iCb7,iCb8,iCb9,iCb10,
                iCb11,iCb12,iCb13,iCb14,iCb15
        };
        for (CheckBox cb : all) cb.setSelected(false);
        RadioButton[] radios = {bRb1,bRb2,bRb3,iRb1,iRb2,iRb3};
        for (RadioButton rb : radios) rb.setSelected(false);
        basothoDrinkCombo.setValue(null);
        italianDrinkCombo.setValue(null);
        cashPaidField.clear();
        totalLabel.setText("M0.00");
        changeLabel.setText("M0.00");
        transactionTimeLabel.setText("--:--:--");
        selectedItemsArea.setText("No items selected yet.");
        totalAmount = 0;
        showInfo("Reset", "Order cleared. Ready for next customer.");
    }

    private void showInfo(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}