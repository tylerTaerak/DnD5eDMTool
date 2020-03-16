/**
 * @author Tyler Conley
 * @date 20.2.11
 * This class is the main driver class for this Dungeon Master Tool.
 * It will have a GUI to display all information, different tools will be contained in different tabs
 */

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


public class DMTool extends Application implements Autowrap {
    public static int LINE_LENGTH;
    private TextArea monsterTF;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws NoSuchMethodException, InstantiationException, IOException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        LINE_LENGTH = 65;
        primaryStage.setTitle("Dungeon Master Tool");
        primaryStage.setMaximized(true);


        MonsterDirectory md = new MonsterDirectory();

        StackPane fullPane = new StackPane();

        fullPane.prefWidthProperty().bind(primaryStage.widthProperty());
        fullPane.prefHeightProperty().bind(primaryStage.heightProperty());

        // The home pane contains information for the entirety of the MonsterDirectory
        // It will contain functions to search for specific monsters, and pull up their stat block into the monsterPane
        // It also contains the monsterPane and the dicePane
        GridPane homePane = new GridPane();
        homePane.prefHeightProperty().bind(primaryStage.heightProperty());
        homePane.prefWidthProperty().bind(primaryStage.widthProperty());


        ArrayList<Button> buttons = new ArrayList<>();
        for(Monster m : md.getTree()){
            Button b = new Button(m.getName());

            b.setOnAction(e -> setMonsterPane(m));

            buttons.add(b);
        }

        VBox box = new VBox();
        box.getChildren().addAll(buttons);
        box.prefHeightProperty().bind(primaryStage.heightProperty().subtract(40));
        ScrollPane buttonsPane = new ScrollPane();

        buttonsPane.setContent(box);
        buttonsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        buttonsPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        c1.setPercentWidth(60);
        c2.setPercentWidth(40);

        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        r1.setPercentHeight(60);
        r2.setPercentHeight(40);

        homePane.getColumnConstraints().addAll(c1, c2);
        homePane.getRowConstraints().addAll(r1, r2);

        homePane.setGridLinesVisible(true);

        GridPane.setRowIndex(buttonsPane, 0);
        GridPane.setColumnIndex(buttonsPane, 0);
        GridPane.setRowSpan(buttonsPane, 2);
        GridPane.setColumnSpan(buttonsPane, 1);

        homePane.getChildren().add(buttonsPane);


        // The monster pane contains information for the current monster whose stats are being looked at
        // Clicking on a monster in the home pane will bring up its stats in the monster pane
        Pane monsterPane = new Pane();
        int randInt = (int) (Math.random() * md.getTree().size());
        Monster monsterOfTheDay = (Monster) md.getTree().toArray()[randInt];
        System.out.println(monsterOfTheDay.getName());
        monsterTF = new TextArea(monsterOfTheDay.toString());

        monsterPane.prefWidthProperty().bind(primaryStage.widthProperty().divide(3));
        monsterPane.prefHeightProperty().bind(primaryStage.heightProperty().divide(2));

        monsterTF.prefWidthProperty().bind(monsterPane.widthProperty());
        monsterTF.prefHeightProperty().bind(monsterPane.heightProperty());

        monsterTF.setEditable(false);
        monsterTF.setStyle("-fx-font-size: 1.15em");
        // make sure to add this to the styles.css file instead of here
        monsterPane.getChildren().add(monsterTF);

        GridPane.setRowIndex(monsterPane, 0);
        GridPane.setColumnIndex(monsterPane, 1);
        GridPane.setRowSpan(monsterPane, 1);
        GridPane.setColumnSpan(monsterPane, 1);

        homePane.getChildren().add(monsterPane);

        // The dice pane is a tool that will roll dice for the user. In addition, it will display attack and saving throw
        // results of monsters. It will only take in 2, 3, 4, 6, 8, 10, 12, 20, and 100 for values
        VBox dicePane = new VBox();

        TextArea rolls = new TextArea();
        rolls.setEditable(false);
        TextField number = new TextField("No. of Dice");
        TextField sides = new TextField("d-");
        TextField bonus = new TextField("Bonus to Roll");
        Button roll = new Button("Roll");
        roll.setOnAction(e -> {
            try{
                rolls.setText(rollDice(Integer.parseInt(number.getText()), Integer.parseInt(sides.getText()), Integer.parseInt(bonus.getText())));
            }
            catch (NumberFormatException ex){
                rolls.setText("Invalid Entry. Please enter only integers");
            }
        });

        HBox prompts = new HBox();

        prompts.getChildren().addAll(number, sides, bonus, roll);

        dicePane.getChildren().addAll(rolls, prompts);


        GridPane.setRowIndex(dicePane, 1);
        GridPane.setColumnIndex(dicePane, 1);
        GridPane.setRowSpan(dicePane, 1);
        GridPane.setColumnSpan(dicePane, 1);

        homePane.getChildren().add(dicePane);


        Pane encounterPane = new Pane();




        Pane homebrewPane = new Pane();




        Pane settingsPane = new Pane();





        /**
         * @author Tyler Conley
         * @date 20.2.11
         * The tabs pane keeps tabs for the various tools that will be included in the app
         * Currently, I think these will include an encounter tool, a homebrew monster tool, a settings tab, and a
         * quick homebrew monster guide (probably related to the monster on a namecard, which I have bookmarked in Chrome),
         * as well as the home pane
         */
        TabPane tabs = new TabPane();
        tabs.setSide(Side.LEFT);
        tabs.setTabMinWidth(50);
        tabs.setTabMaxWidth(50);
        tabs.setTabMinHeight(50);
        tabs.setTabMaxHeight(50);






        Tab homeTab = new Tab();
        homeTab.setContent(homePane);
        homeTab.setClosable(false);
        homeTab.setGraphic(new Label("Home"));






        Tab encounterTab = new Tab();
        encounterTab.setContent(encounterPane);
        encounterTab.setClosable(false);
        encounterTab.setGraphic(new Label("Encounters"));






        Tab homebrewTab = new Tab();
        homebrewTab.setContent(homebrewPane);
        homebrewTab.setClosable(false);
        homebrewTab.setGraphic(new Label("Homebrew"));






        Tab settingsTab = new Tab();
        settingsTab.setContent(settingsPane);
        settingsTab.setClosable(false);
        settingsTab.setGraphic(new Label("Settings"));






        tabs.getTabs().addAll(homeTab, encounterTab, homebrewTab, settingsTab);

        fullPane.getChildren().add(tabs);

        Scene scene = new Scene(fullPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setLineLength(int lineLength){
        LINE_LENGTH = lineLength;
    }

    private void setMonsterPane(Monster m){
        monsterTF.setText(m.toString());
    }

    private String rollDice(int number, int sides, int bonus){
        StringBuilder sb = new StringBuilder();
        int[] allowableSides = new int[]{2, 3, 4, 6, 8, 10, 12, 20, 100};
        boolean validSides = false;
        for (int i : allowableSides){
            if (sides == i){
                validSides = true;
                break;
            }
        }
        if(!validSides){
            return "Invalid Dice Size: Use a d2, d3, d4, d6, d8, d10, d12, d20, or d100";
        }
        sb.append("Rolled: ");
        sb.append(number).append('d').append(sides).append(" + ").append(bonus);
        int[] lst = new int[number];
        for(int i=0; i<number; i++) {
            lst[i] = (int) (Math.random() * sides) + 1;
        }
        sb.append("\nRolls: ");
        int sum = 0;
        for(int i : lst){
            sb.append(i).append(", ");
            sum += i;
        }
        if (sides != 20 && sides != 100){
            sb.append("Total: ").append(sum + bonus);
        }

        return sb.toString();
    }
}
