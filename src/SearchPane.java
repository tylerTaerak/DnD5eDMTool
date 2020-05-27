import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class SearchPane extends VBox {
    private final ScrollPane monsterPane;
    private TextArea rolls;

    SearchPane(ScrollPane monsterPane, TextArea dice)
            throws NoSuchMethodException,
            InstantiationException,
            ClassNotFoundException,
            IllegalAccessException,
            InvocationTargetException,
            IOException {

        super();
        this.monsterPane = monsterPane;
        this.rolls = dice;

        TextField byName = new TextField();
        byName.setPromptText("Search by Name");
        byName.setFocusTraversable(true);
        TextField byType = new TextField();
        byType.setPromptText("Search by Type");
        byType.setFocusTraversable(true);
        TextField byProf = new TextField();
        byProf.setPromptText("Search by Proficiency Bonus");
        byProf.setFocusTraversable(true);
        TextField byCR = new TextField();
        byCR.setPromptText("Search by Challenge Rating");
        byCR.setFocusTraversable(true);
        Button search = new Button("Search");
        search.setDefaultButton(true);


        ScrollPane buttonsPane = new ScrollPane();

        Monster[] monsters = searchMonsterList(
                byName.getText(), byType.getText(), byProf.getText(), byCR.getText()
        );

        VBox box = new VBox();
        box.getChildren().addAll(createButtonList(monsters));
        buttonsPane.setContent(box);
        buttonsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        buttonsPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        search.setOnAction(e -> {
            Monster[] monsters1 = new Monster[] {};
            try {
                monsters1 = searchMonsterList(
                        byName.getText(), byType.getText(), byProf.getText(), byCR.getText()
                );
            }
            catch (NoSuchMethodException | InstantiationException | IOException | IllegalAccessException |
                    InvocationTargetException | ClassNotFoundException err) {
                err.printStackTrace();
            }
            byName.setText("");
            byType.setText("");
            byProf.setText("");
            byCR.setText("");


            VBox box1 = new VBox();
            box1.getChildren().addAll(createButtonList(monsters1));
            buttonsPane.setContent(box1);
        });

        buttonsPane.setFocusTraversable(false);

        this.getChildren().addAll(byName, byType, byProf, byCR, search);
        this.getChildren().add(buttonsPane);
    }


    private ArrayList<Button> createButtonList(Monster[] monsters){
        ArrayList<Button> buttons = new ArrayList<>();
        for (Monster m : monsters) {
            Button b = new Button(m.getName());
            b.setOnAction(ev -> DMTool.setMonsterPane(this.monsterPane, rolls, m));
            buttons.add(b);
        }

        return buttons;
    }



    private Monster[] searchMonsterList(String name, String type, String prof, String CR)
            throws NoSuchMethodException,
            InstantiationException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            ClassNotFoundException {

        MonsterDirectory md = new MonsterDirectory();
        Integer profInt;
        Double crDouble;
        if (name.equals("")){
            name = null;
        }
        if (type.equals("")){
            type = null;
        }
        if (prof.equals("")){
            profInt = null;
        }
        else {
            profInt = Integer.parseInt(prof);
        }
        if (CR.equals("")){
            crDouble = null;
        }
        else{
            crDouble = Double.parseDouble(CR);
        }

        return md.searchMonsters(name, type, profInt, crDouble);
    }

}
