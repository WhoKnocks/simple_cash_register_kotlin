package ui;

import core.Basket;
import core.CashRegister;
import core.FoodType;
import core.MenuItem;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.application.Application.launch;

/**
 * Created by GJ on 5/04/2017.
 */
public class UI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private BorderPane root;
    private double totalPrice = 0.0;
    private Label totalPriceLabelFirst = new Label("0.0");
    private Label totalPriceLabelSecond = new Label("0.0");
    private VBox vBox = new VBox();

    private CashRegister cashRegister;
    ObservableList<Basket.SelectedMenuItem> observableList;

    @Override
    public void start(Stage primaryStage) {
        try {
            cashRegister = new CashRegister(new Basket(new ArrayList<>()), new ArrayList<>());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Waarschijnlijk staan de '.json' bestanden niet op de correcte locatie.\n" +
                    "De correcte locatie is \"C:\\joc-kassa-menu\" met hier in de bestanden");
            alert.showAndWait();
            System.exit(0);
        }

        primaryStage.setTitle("Joc Frontaal Jenge");


        root = new BorderPane();

        HBox top = new HBox(10);
        top.setPadding(new Insets(10, 10, 35, 10));

        final Button left = new Button("Left");
        left.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);
        final Button right = new Button("Right");
        right.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        root.setTop(top);
        root.setCenter(getGridPane(FoodType.PRE));

        Button clearAll = new Button("Nieuwe Klant");
        clearAll.setPrefHeight(75.0);
        clearAll.setPrefWidth(200.0);
        clearAll.setStyle("-fx-font: 24 arial;");


        for (FoodType foodType : cashRegister.getStock().stream().map(MenuItem::getType).distinct().sorted(Comparator.comparingInt(Enum::ordinal)).collect(Collectors.toList())) {
            top.getChildren().add(getCategoryButton(foodType));
        }

        top.getChildren().addAll(spacer, clearAll);


        HBox priceBox = new HBox(10.0);
        Label priceLabel = new Label("Totale prijs: € ");
        priceLabel.setStyle("-fx-font: 30 arial;");
        totalPriceLabelFirst.setStyle("-fx-font: 30 arial;");

        priceBox.getChildren().addAll(priceLabel, totalPriceLabelFirst);

        root.setBottom(priceBox);

        FXCollections.observableHashMap();
        observableList = FXCollections.observableList(cashRegister.getActiveBasket().getSelectedItems());
        clearAll.setOnAction((event) -> {
            observableList.clear();
        });

        ListView<Basket.SelectedMenuItem> lv = new ListView<>(observableList);
        lv.getSelectionModel().selectedIndexProperty().addListener((observable, oldvalue, newValue) -> {
                    Platform.runLater(() -> lv.getSelectionModel().clearSelection());
                });
        lv.setMinWidth(340);
        lv.setCellFactory(param -> new HistoryCell());
        root.setRight(lv);

        buildSecondScreen();

        primaryStage.setScene(new Scene(root, 1750, 650));
        primaryStage.show();
    }

    private void buildSecondScreen() {
        BorderPane customerScreen = new BorderPane();

        ListView<Basket.SelectedMenuItem> customerList = new ListView<>(observableList);
        customerList.getSelectionModel().selectedIndexProperty().addListener((observable, oldvalue, newValue) -> {
            Platform.runLater(() -> customerList.getSelectionModel().clearSelection());
        });
        customerList.setMinWidth(500);
        customerList.setMinHeight(400);
        customerList.setMaxWidth(500);
        customerList.setMaxHeight(700);
        customerList.setCellFactory(param -> new CustomerCell());
        customerScreen.setCenter(customerList);

        HBox priceBox = new HBox(10.0);
        priceBox.setAlignment(Pos.CENTER);
        priceBox.setPadding(new Insets(50, 0, 50, 0));
        Label priceLabel = new Label("Totale prijs: € ");
        priceLabel.setStyle("-fx-font: 50 arial;");
        totalPriceLabelSecond.setStyle("-fx-font: 50 arial;");

        priceBox.getChildren().addAll(priceLabel, totalPriceLabelSecond);
        customerScreen.setBottom(priceBox);


        URL resource1 = getClass().getResource("/img/joc-header.jpg");

        ImageView iv3 = new ImageView();
        try {
            iv3.setImage(new Image(resource1.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HBox imageBox = new HBox();
        imageBox.setPadding(new Insets(20, 0, 20, 0));
        imageBox.setAlignment(Pos.CENTER);
        imageBox.getChildren().addAll(iv3);
        customerScreen.setTop(imageBox);

        Stage stage = new Stage();
        stage.setScene(new Scene(customerScreen, 1000, 1000));

        //Fill stage with content
        stage.show();
    }


    private GridPane getGridPane(FoodType foodType) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        List<Button> buttons = cashRegister.getStock().stream().filter(x -> x.getType() == foodType).map(this::getItemButton).collect(Collectors.toList());

        int counter = 0;
        for (int i = 0; i < Math.ceil((double) buttons.size()) / 6; i++) {
            gridPane.addRow(counter, listToButtons(buttons, (counter * 6), (counter * 6) + 6));
            counter++;
        }


        return gridPane;
    }

    private Button[] listToButtons(List<Button> buttons, int start, int end) {
        ArrayList<Button> part = new ArrayList<>();
        for (; start < end; start++) {
            boolean inBounds = (start >= 0) && (start < buttons.size());
            if (inBounds) part.add(buttons.get(start));
        }
        return part.toArray(new Button[part.size()]);
    }

    private Button getCategoryButton(FoodType foodType) {
        Button button = new Button(foodType.getDescription());
        button.setPrefHeight(75.0);
        button.setPrefWidth(200.0);
        button.setStyle("-fx-font: 24 arial; -fx-base: #b6e7c9;");
        button.setOnAction((event) -> {
            root.setCenter(getGridPane(foodType));
        });
        return button;
    }

    private Button getItemButton(MenuItem menuItem) {
        Button button = new Button();
        button.setPrefHeight(100.0);
        button.setPrefWidth(200.0);

        VBox vBox = new VBox(10.0);
        Label itemNameLabel = new Label(menuItem.getDescription());
        itemNameLabel.setPrefWidth(200.0);
        itemNameLabel.setAlignment(Pos.CENTER);
        itemNameLabel.setStyle("-fx-font: 24 arial;");

        Label itemPriceLabel = new Label(menuItem.getPrice() + "");
        itemPriceLabel.setPrefWidth(200.0);
        itemPriceLabel.setAlignment(Pos.CENTER);
        itemPriceLabel.setStyle("-fx-font: 20 arial;");

        vBox.getChildren().addAll(itemNameLabel, itemPriceLabel);

        button.setGraphic(vBox);

        button.setOnAction((event) -> {
            addToBasket(menuItem);
        });

        return button;
    }


    class CustomerCell extends ListCell<Basket.SelectedMenuItem> {
        HBox hbox = new HBox();
        Label description = new Label("");
        Label counter = new Label("");
        Label subPrice = new Label("");

        Pane pane = new Pane();

        public CustomerCell() {
            super();
            hbox.getChildren().addAll(description, counter, pane, subPrice);
            HBox.setHgrow(pane, Priority.ALWAYS);
            description.setPrefSize(250, 50);
            description.setStyle("-fx-font: 24 arial;");
            counter.setStyle("-fx-font: 24 arial;");
            counter.setPrefSize(50, 50);
            subPrice.setStyle("-fx-font: 24 arial;");
            subPrice.setPrefSize(100, 50);
        }

        @Override
        protected void updateItem(Basket.SelectedMenuItem item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                description.setText(String.format("%-15.25s", item.getItem().getDescription()));
                counter.setText(String.format("%3s", item.getAmount()));
                subPrice.setText(String.format("€ %4s", (item.getItem().getPrice()) * item.getAmount()));
                setGraphic(hbox);
            }
            calculatePrice();
        }
    }


    class HistoryCell extends ListCell<Basket.SelectedMenuItem> {
        HBox hbox = new HBox();
        Label description = new Label("");

        Label counter = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Verwijder");

        public HistoryCell() {
            super();
            hbox.getChildren().addAll(description, counter, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            // description.setStyle("-fx-background-color: #6b3b50;");
            description.setPrefSize(150, 30);
            description.setStyle("-fx-font: 20 arial;");
            counter.setPrefSize(60, 30);
            counter.setStyle("-fx-font: 20 arial;");
            button.setOnAction(event -> {
                if (getItem().getAmount() == 1) {
                    getListView().getItems().remove(getItem());
                } else {
                    getItem().removeMenuItem();
                    observableList.set(observableList.indexOf(getItem()), getItem());
                }
            });
        }

        @Override
        protected void updateItem(Basket.SelectedMenuItem item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                description.setText(String.format("%-15.25s", item.getItem().getDescription()));
                counter.setText(String.format("%5s", item.getAmount()));
                setGraphic(hbox);
            }
            calculatePrice();
        }
    }


    private void addHistoryPanel(final MenuItem menuItem) {
        Label label = new Label();
        label.setStyle("-fx-font: 20 arial;");
        label.setText(menuItem.getDescription() + "   ");

        vBox.getChildren().add(label);
    }

    public void addToBasket(final MenuItem menuItem) {
        if (observableList.contains((new Basket.SelectedMenuItem(menuItem, 0)))) {
            Basket.SelectedMenuItem presentItem = observableList.stream().filter(x -> x.getItem() == menuItem).findFirst().get();
            presentItem.addMenuItem();
            observableList.set(observableList.indexOf(presentItem), presentItem);
        } else {
            observableList.add(new Basket.SelectedMenuItem(menuItem, 1));
        }


        addHistoryPanel(menuItem);
        calculatePrice();

    }

    public void calculatePrice() {
        totalPriceLabelFirst.setText(cashRegister.getActiveBasket().calcTotalPrice() + "");
        totalPriceLabelSecond.setText(cashRegister.getActiveBasket().calcTotalPrice() + "");
    }


}
