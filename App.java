/**
 * JavaFX Food Ordering 
 * Made by: Shri Varshini
 * Semester: Fall 2024
 */

package com.example;

import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("My Food Ordering App");

        // Main layout setup
        BorderPane myPanel = new BorderPane();
        myPanel.setPadding(new Insets(10));

        // Text area setup
        TextArea myTextArea = new TextArea();
        myTextArea.setPrefHeight(200);
        myTextArea.setEditable(false);
        myPanel.setBottom(myTextArea);

        // Order info
        FoodOrder myOrder = new FoodOrder("0001");

        // Order ID display
        TextField orderNumberField = new TextField("Order #" + myOrder.getOrderNumber());
        orderNumberField.setEditable(false);
        myPanel.setTop(orderNumberField);

        // Grid layout for order options
        GridPane myGrid = new GridPane();
        myGrid.setPadding(new Insets(10, 10, 10, 10));
        myGrid.setVgap(10);
        myGrid.setHgap(10);
        myPanel.setCenter(myGrid);

        // Buttons to place order, add food, and remove items
        Button submit = new Button("Place Order");
        Button addPizza = new Button("Add Pizza");
        Button addSalad = new Button("Add Salad");
        Button removeItem = new Button("Remove Last Item");
        
        // Organize buttons in a horizontal layout
        HBox buttonBox = new HBox(10, addPizza, addSalad, removeItem, submit);
        buttonBox.setAlignment(Pos.CENTER);
        myPanel.setTop(buttonBox);

        // Size and flavor options
        final ToggleGroup groupSize = addSizeOptions(myGrid);
        final ToggleGroup groupFlavor = addFlavorOptions(myGrid);

        // Place order button functionality
        submit.setOnAction(e -> {
            myOrder.placeOrder();
            myTextArea.clear();
            myTextArea.appendText("Order Status: " + myOrder.getStatus() + "\n" + myOrder.toString());
        });

        // Add pizza button functionality
        addPizza.setOnAction(e -> {
            Pizza.Flavor flavor = (Pizza.Flavor) groupFlavor.getSelectedToggle().getUserData();
            Pizza.Size size = (Pizza.Size) groupSize.getSelectedToggle().getUserData();
            Food pizza = new Pizza(flavor, size);
            myOrder.addFood(pizza);
            myTextArea.appendText("Added: " + pizza.toString());
        });

        // Add salad button functionality
        addSalad.setOnAction(e -> {
            Salad salad = new Salad();
            myOrder.addFood(salad);
            myTextArea.appendText("Added: " + salad.toString());
        });

        // Remove item button functionality
        removeItem.setOnAction(e -> {
            if (!myOrder.items.isEmpty()) {
                Food removed = myOrder.items.remove(myOrder.items.size() - 1);
                myOrder.removeFood(removed);
                myTextArea.appendText("Removed: " + removed.toString());
            } else {
                myTextArea.appendText("No items to remove.\n");
            }
        });

        // Set up and display scene
        scene = new Scene(myPanel, 700, 500);
        stage.setScene(scene);
        stage.show();
    }

    private ToggleGroup addSizeOptions(GridPane myGrid) {
        final ToggleGroup groupSize = new ToggleGroup();

        RadioButton small = new RadioButton("Small");
        small.setToggleGroup(groupSize);
        small.setUserData(Pizza.Size.PERSONAL);
        GridPane.setConstraints(small, 2, 0);
        myGrid.getChildren().add(small);

        RadioButton medium = new RadioButton("Medium");
        medium.setToggleGroup(groupSize);
        medium.setUserData(Pizza.Size.MEDIUM);
        GridPane.setConstraints(medium, 2, 1);
        myGrid.getChildren().add(medium);

        RadioButton large = new RadioButton("Large");
        large.setToggleGroup(groupSize);
        large.setUserData(Pizza.Size.LARGE);
        GridPane.setConstraints(large, 2, 2);
        myGrid.getChildren().add(large);

        small.setSelected(true); // Default selection

        return groupSize;
    }

    private ToggleGroup addFlavorOptions(GridPane myGrid) {
        final ToggleGroup groupFlavor = new ToggleGroup();

        RadioButton cheese = new RadioButton("Cheese");
        cheese.setToggleGroup(groupFlavor);
        cheese.setUserData(Pizza.Flavor.CHEESE);
        GridPane.setConstraints(cheese, 3, 0);
        myGrid.getChildren().add(cheese);

        RadioButton pepperoni = new RadioButton("Pepperoni");
        pepperoni.setToggleGroup(groupFlavor);
        pepperoni.setUserData(Pizza.Flavor.PEPPERONI);
        GridPane.setConstraints(pepperoni, 3, 1);
        myGrid.getChildren().add(pepperoni);

        cheese.setSelected(true); // Default selection

        return groupFlavor;
    }

    public static void main(String[] args) {
        launch();
    }
}
