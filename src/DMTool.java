/**
 * @author Tyler Conley
 * @date 20.2.11
 * This class is the main driver class for this Dungeon Master Tool.
 * It will have a GUI to display all information, different tools will be contained in different tabs
 */

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


public class DMTool extends Application implements Autowrap {
    public static int LINE_LENGTH; //the character count for each line, can be circumvented with setWrapText for text areas, implemented in the Autowrap interface
    private static Monster monster; //the current monster whose stats are being displayed; needs changing with implementation of SearchPane

    public static final String PATH = "src/enc/"; //the file path used throughout the file. This is the variable that may need changing
                                                    //depending on the compiling tool used


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


        setConstraints(homePane);


        TextArea rolls = new TextArea();
        rolls.setWrapText(true);

        ScrollPane monsterSP = new ScrollPane();
        SearchPane sp = new SearchPane(monsterSP, rolls);
        setGridIndices(sp, 0);
        

        homePane.getChildren().add(sp);


        // The monster pane contains information for the current monster whose stats are being looked at
        // Clicking on a monster in the home pane will bring up its stats in the monster pane
        Pane monsterPane = new Pane();
	if (md.getTree() != null && md.getTree().size() > 0){
 	       int randInt = (int) (Math.random() * md.getTree().size());
		monster =  (Monster) md.getTree().toArray()[randInt];
	}
	else {
		monster = null;
	}
	if (monster != null){
		System.out.println(monster.getName());
		monsterSP.setContent(monster.toPane(rolls));
	}

        monsterSP.prefWidthProperty().bind(monsterPane.widthProperty());
        monsterSP.prefHeightProperty().bind(monsterPane.heightProperty());


        monsterSP.getStyleClass().add("monsterPane");
        monsterPane.getChildren().add(monsterSP);

        setGridIndices(monsterPane, 1);

        homePane.getChildren().add(monsterPane);


        // The dice pane is a tool that will roll dice for the user. In addition, it will display attack and saving throw
        // results of monsters. It will only take in 2, 3, 4, 6, 8, 10, 12, 20, and 100 for values
        VBox dicePane = new VBox();


        rolls.setEditable(false);
        TextField number = new TextField();
        number.setPromptText("No. of Dice");
        number.setFocusTraversable(false);
        TextField sides = new TextField();
        sides.setPromptText("d-");
        sides.setFocusTraversable(false);
        TextField bonus = new TextField();
        bonus.setPromptText("Bonus to Roll");
        bonus.setFocusTraversable(false);
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
        setGridIndices(dicePane, 2);

        homePane.getChildren().add(dicePane);





        GridPane encounterPane = new GridPane();
        setConstraints(encounterPane);


        File[] files = (new File(PATH)).listFiles();
        VBox vBox = new VBox();


        ScrollPane encPane = new ScrollPane();
        encPane.getStyleClass().add("monsterPane");

        Button newEnc = new Button("+");

        ArrayList<Node> nodes = new ArrayList<>();

        Button toEncounterHome = new Button("Back to Encounter Home");
        toEncounterHome.setOnAction(back -> {
            vBox.getChildren().clear();
            vBox.getChildren().add(newEnc);
            vBox.getChildren().addAll(nodes);
            encounterPane.getChildren().setAll(vBox);
        });

        Label noEncounters = new Label("You have no saved encounters. Press the '+' button to create one");
        if (files != null && files.length > 0){
            for (File f : files){
                Encounter e = Encounter.readFromFile(f);
                Button b = new Button(f.getName().replace(".enc", ""));
                b.setOnAction(ev -> openEncounter(e, encounterPane, toEncounterHome));
                nodes.add(b);
            }
        }

        else {
            nodes.add(noEncounters);
        }

        vBox.getChildren().add(newEnc);
        vBox.getChildren().addAll(nodes);

        newEnc.setOnAction(e -> {
            vBox.getChildren().clear();
            Encounter encounter = new Encounter();
            vBox.getChildren().add(toEncounterHome);
            Button addMonsters = new Button("Add Monsters");
            addMonsters.setOnAction(ev -> {
                try {
                    encPane.setContent(monster.toPane(new TextArea()));
                    SearchPane encSearch = new SearchPane(encPane, new TextArea());
                    setGridIndices(encSearch, 0);

                    VBox encMonster = new VBox(encPane);
                    setGridIndices(encMonster, 1);

                    encPane.prefWidthProperty().bind(encMonster.widthProperty());
                    encPane.prefHeightProperty().bind(encMonster.heightProperty());

                    VBox encounterView = new VBox();
                    setGridIndices(encounterView, 2);

                    Button add = new Button("Add Monster");
                    add.setOnAction(action -> {
                        Monster temp = monster;
                        encounter.add(monster);
                        Label monsterName = new Label(monster.getName());
                        monsterName.setOnMouseClicked(click -> {
                            encounter.remove(temp);
                            encounterView.getChildren().remove(monsterName);
                        });
                        encounterView.getChildren().add(monsterName);
                    });
                    encMonster.getChildren().add(add);

                    TextField encName = new TextField();
                    encName.setPromptText("Encounter Name");
                    Button saveEnc = new Button("Save");
                    saveEnc.setOnAction(event -> {
                        Button button = new Button();

                        if(encName.getText().equals("")){
                            Encounter.writeToFile(encounter, "untitled");
                            button.setText("untitled");
                        }
                        else {
                            Encounter.writeToFile(encounter, encName.getText());
                            button.setText(encName.getText());
                        }

                        button.setOnAction(evt -> openEncounter(encounter, encounterPane, toEncounterHome));
                        nodes.add(button);
                        nodes.remove(noEncounters);
                    });
                    saveEnc.setDefaultButton(true);

                    encounterView.getChildren().add(new Label("Current Encounter\n\n"));

                    encounterView.getChildren().addAll(encName, saveEnc, toEncounterHome);

                    encounterPane.getChildren().setAll(encSearch, encMonster, encounterView);
                }
                catch (NoSuchMethodException | InstantiationException | ClassNotFoundException |
                        IllegalAccessException | InvocationTargetException | IOException err) {
                    err.printStackTrace();
                }
            });

            Button random = new Button("Random Encounter");
            random.setOnAction(ev -> {
                vBox.getChildren().clear();

                ToggleGroup group = new ToggleGroup();

                RadioButton dungeon = new RadioButton("Dungeon");
                dungeon.setToggleGroup(group);
                RadioButton grassland = new RadioButton("Grassland");
                grassland.setToggleGroup(group);
                RadioButton forest = new RadioButton("Forest");
                forest.setToggleGroup(group);
                RadioButton desert = new RadioButton("Desert");
                desert.setToggleGroup(group);
                RadioButton mountain = new RadioButton("Mountain");
                mountain.setToggleGroup(group);
                RadioButton roadside = new RadioButton("Roadside");
                roadside.setToggleGroup(group);
                RadioButton underdark = new RadioButton("Underdark");
                underdark.setToggleGroup(group);
                RadioButton shadowfell = new RadioButton("Shadowfell");
                shadowfell.setToggleGroup(group);
                RadioButton feywild = new RadioButton("Feywild");
                feywild.setToggleGroup(group);
                RadioButton upper = new RadioButton("Upper Planes");
                upper.setToggleGroup(group);
                RadioButton lower = new RadioButton("Lower Planes");
                lower.setToggleGroup(group);

                TextField noOfParty = new TextField();
                TextField partyLvl = new TextField();


                noOfParty.setPromptText("Number of Party Members");
                partyLvl.setPromptText("Party Level");
                Button submit = new Button("Generate Random Encounter");

                Node[] randoNodes = new Node[] {toEncounterHome, dungeon, grassland, forest, desert, mountain, roadside, underdark,
                        shadowfell, feywild, upper, lower, noOfParty, partyLvl, submit};

                submit.setOnAction(event -> {
                    try{
                        Encounter.AreaIndexes indexes = new Encounter.AreaIndexes();
                        RadioButton btn = (RadioButton) group.getSelectedToggle();
                        Integer index = indexes.table.get(btn.getText().toLowerCase());
                        Encounter rando = Encounter.randomEncounter(index,
                                Integer.parseInt(noOfParty.getText()), Integer.parseInt(partyLvl.getText()));
                        encounter.setMonsters(rando.getMonsters());
                        //see if I can rework this to always be above the save button
                        TextField name = new TextField();
                        name.setPromptText("untitled");

                        Button save = new Button("Save");
                        save.setOnAction(action -> Encounter.writeToFile(encounter, name.getText()));

                        vBox.getChildren().setAll(randoNodes);
                        vBox.getChildren().addAll(encounter.toPane(null, null), name, save);

                    }
                    catch (NumberFormatException numErr){
                        Label error = new Label("Error: Party Number and Level must be integers");
                        vBox.getChildren().add(error);
                    }
                    catch (IndexOutOfBoundsException exc){
                        Label error = new Label("Error: Monster Directory not initialized. Path must be changed");
                        vBox.getChildren().add(error);
                    }
                    catch (NoSuchMethodException | InstantiationException | IOException |
                            IllegalAccessException | InvocationTargetException | ClassNotFoundException err){
                        err.printStackTrace();
                    }

                });


                vBox.getChildren().setAll(randoNodes);
            });
            //TextField name = new TextField("untitled");
            //Button save = new Button("Save");
            //save.setOnAction(ev -> Encounter.writeToFile(encounter, name.getText()));
            vBox.getChildren().removeAll(newEnc);
            vBox.getChildren().add(addMonsters);
            vBox.getChildren().add(random);


        });

        encounterPane.getChildren().add(vBox);



        Pane homebrewPane = new Pane();
        homebrewPane.getChildren().add(new Label("Content Not Yet Added"));




        Pane settingsPane = new Pane();
        settingsPane.getChildren().add(new Label("Content Not Yet Added"));





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
        scene.getStylesheets().add("styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setLineLength(int lineLength){
        LINE_LENGTH = lineLength;
    }

    public static void setMonsterPane(ScrollPane monsterPane, TextArea dice, Monster m) {
        monster = m;
        monsterPane.setContent(m.toPane(dice));
    }

    private static String rollDice(int number, int sides, int bonus){
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
    
    private static void openEncounter(Encounter e, GridPane gridPane, Button backButton){
        if(gridPane.getColumnConstraints().size() == 0 && gridPane.getRowConstraints().size() == 0){
            setConstraints(gridPane);
        }
        VBox monsterLst = new VBox();
        setGridIndices(monsterLst, 0);
        monsterLst.getChildren().add(backButton);


        VBox dicePane = new VBox();
        setGridIndices(dicePane, 2);

        TextArea dice = new TextArea();
        dice.setWrapText(true);

        VBox monsterPane = new VBox();
        monsterPane.getStyleClass().add("monsterPane");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(e.getMonsters().get(0).toPane(dice));
        setGridIndices(monsterPane, 1);
        monsterPane.getChildren().add(scrollPane);


        for (Monster m : e.getMonsters()){
            Button b = new Button(m.getName());
            b.setOnAction(ev -> scrollPane.setContent(m.toPane(dice)));
            monsterLst.getChildren().add(b);
        }

        dice.setEditable(false);
        TextField number = new TextField();
        number.setPromptText("No. of Dice");
        number.setFocusTraversable(false);
        TextField sides = new TextField();
        sides.setPromptText("d-");
        sides.setFocusTraversable(false);
        TextField bonus = new TextField();
        bonus.setPromptText("Bonus to Roll");
        bonus.setFocusTraversable(false);
        Button roll = new Button("Roll");
        roll.setOnAction(event -> {
            try{
                dice.setText(rollDice(Integer.parseInt(number.getText()), Integer.parseInt(sides.getText()), Integer.parseInt(bonus.getText())));
            }
            catch (NumberFormatException ex){
                dice.setText("Invalid Entry. Please enter only integers");
            }
        });

        HBox prompts = new HBox();
        prompts.getChildren().addAll(number, sides, bonus, roll);

        dicePane.getChildren().addAll(dice, prompts);


        gridPane.getChildren().setAll(monsterLst, monsterPane, dicePane);
    }

    /**
     * This function sets the constraints for the GridPanes throughout the app.  The app is designed to have a 60/40
     * ratio for both rows and columns, with the large central areas taking up two rows.
     *
     *
     * @param p the GridPane which is to have its constraints defined
     */
    private static void setConstraints(GridPane p){
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        c1.setPercentWidth(60);
        c2.setPercentWidth(40);

        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        r1.setPercentHeight(70);
        r2.setPercentHeight(30);

        p.getColumnConstraints().addAll(c1, c2);
        p.getRowConstraints().addAll(r1, r2);
    }


    /**
     * This function is built to set a Pane's place within the grid.  Grid constraints are made to be consistent
     * throughout the app (see above).  This function places a pane in one of three locations:
     *
     *
     * @param index 0 indicates that the pane goes in the large, open area in the middle of the screen. 1 indicates that
     *              it goes in the semi-large area to the top right of the screen, and 2 means that it goes in the
     *              smaller area in the bottom right.
     */
    private static void setGridIndices(Pane p, int index){
        if (index == 0){
            GridPane.setColumnIndex(p, 0);
            GridPane.setColumnSpan(p, 1);
            GridPane.setRowIndex(p, 0);
            GridPane.setRowSpan(p, 2);
        }
        else if (index == 1){
            GridPane.setRowIndex(p, 0);
            GridPane.setColumnIndex(p, 1);
            GridPane.setRowSpan(p, 1);
            GridPane.setColumnSpan(p, 1);
        }
        else if (index == 2){
            GridPane.setRowIndex(p, 1);
            GridPane.setColumnIndex(p, 1);
            GridPane.setRowSpan(p, 1);
            GridPane.setColumnSpan(p, 1);
        }
    }
}
