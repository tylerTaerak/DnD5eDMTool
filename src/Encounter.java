import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * @author Tyler Conley
 * encounters are a group of monsters that will be put to combat against the players. Encounters can be saved and
 * accessed in the 'enc' folder of this program.  They will be displayed in the 'encounters' tab in the main application
 *
 * These are the indices, in order, for areas for the random encounter calculator:
 * dungeon, grassland, forest, desert, mountain, roadside, Underdark, Shadowfell, Feywild, Upper Planes, and Lower Planes
 *
 * A 'translator' of sorts needs to be implemented to translate the string form to the index (e.g. dungeon => 0)
 * This could be accomplished with an external enum, though that may need to be played around with if I want to allow the
 * user to be able to add different environments manually
 */

public class Encounter implements Serializable {
    private ArrayList<Monster> monsters;

    public Encounter(){
        monsters = new ArrayList<>();
    }

    public Encounter(Monster[] monsters) {
        this.monsters = (ArrayList<Monster>) Arrays.asList(monsters);
    }

    public void add(Monster monster){
        monsters.add(monster);
    }

    public void remove(Monster monster){
        monsters.remove(monster);
    }

    public static Encounter readFromFile(File file){
        Encounter encounter = null;
        try {
            FileInputStream fIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fIn);
            encounter = (Encounter) in.readObject();
            in.close();
            fIn.close();
        }
        catch (IOException | ClassNotFoundException err){
            err.printStackTrace();
        }
        return encounter;
    }

    public static void writeToFile(Encounter encounter, String name) {
        try {
            FileOutputStream fileOut = new FileOutputStream(DMTool.PATH + name + ".enc");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(encounter);
            out.close();
        }
        catch (IOException err){
            err.printStackTrace();
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Encounter: \n\n");
        for (Monster m : monsters){
            sb.append(m.getName()).append('\n');
        }
        return sb.toString();
    }

    public VBox toPane(ScrollPane pane, TextArea rolls){
        VBox box = new VBox();
        for (Monster m : monsters){
            Button b = new Button(m.getName());
            b.setOnAction(e -> {
                if(pane != null && rolls != null){
                    DMTool.setMonsterPane(pane, rolls, m);
                }
            });
            box.getChildren().add(b);
        }
        return box;
    }

    public ArrayList<Monster> getMonsters(){
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters){
        this.monsters = monsters;
    }

    public static Encounter[] randEncounterInterval(int noOfIntervals, int index, int partySize, int partyLevel) throws NoSuchMethodException, InstantiationException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, IOException {
        ArrayList<Encounter> encounters = new ArrayList<>();
        for (int i=0; i<noOfIntervals; i++) {
            double roll = Math.random();
            if (roll <= 0.125){
                encounters.add(Encounter.randomEncounter(index, partySize, partyLevel));
            }
        }

        return (Encounter[]) encounters.toArray();
    }

    /**
     * @author Tyler Conley
     * This will generate a random encounter
     *
     * TODO 5.9.2020 additional random monster algorithms need to be implemented to create more realized encounters
     */
    public static Encounter randomEncounter(int index, int partySize, int partyLevel) throws NoSuchMethodException, InstantiationException, IOException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        MonsterDirectory md = new MonsterDirectory();
        Encounter encounter = new Encounter();

        Hashtable<Integer, ArrayList<Monster>> area = md.getProbabilities().get(index);
        int xp = 200 * partyLevel * partySize;

        double roll = Math.random();
        ArrayList<Monster> monsters;

        // super duper rare encounters (rarity index of 0)
        if (roll <= 0.005){
            monsters = area.get(0);
            rollMonsters(monsters, encounter, xp, xp);
        }
        // very rare encounters (rarity index of 1)
        else if (roll <= 0.1){
            monsters = area.get(1);
            rollMonsters(monsters, encounter, xp, xp);
        }
        // rare encounters (rarity index of 2)
        else if (roll <= 0.25){
            monsters = area.get(2);
            rollMonsters(monsters, encounter, xp, xp);
        }
        // uncommon encounters (rarity index of 3)
        else if(roll <= 0.5){
            monsters = area.get(3);
            rollMonsters(monsters, encounter, xp, xp);
        }
        // common encounters (rarity index of 4)
        else{
            monsters = area.get(4);
            rollMonsters(monsters, encounter, xp, xp);
        }

        return encounter;
    }

    private static void rollMonsters(ArrayList<Monster> monsters, Encounter encounter, int xp, int xpTotal){
        XPTable table = new XPTable();
        while (true){
            int monsterRoll = (int) ((Math.random()) * monsters.size());
            Monster monster = monsters.get(monsterRoll);
            int monsterXP = table.table.get(monster.getCR());
            if(monsterXP>xpTotal){
                continue;
            }
            if(monsterXP > xp){
                break;
            }
            encounter.add(monsters.get(monsterRoll));
            xp -= monsterXP;
        }
    }


    /**
     * @author Tyler Conley
     * this class holds information for xp earned for specific challenge ratings, and is used to calculate random
     * encounters for players of a specific level
     */
    static class XPTable{
        Hashtable<Double, Integer> table;

        XPTable(){
            table = new Hashtable<>();
            table.put(0.0, 10);
            table.put(0.125, 25);
            table.put(0.25, 50);
            table.put(0.5, 100);
            table.put(1.0, 200);
            table.put(2.0, 450);
            table.put(3.0, 700);
            table.put(4.0, 1100);
            table.put(5.0, 1800);
            table.put(6.0, 2300);
            table.put(7.0, 2900);
            table.put(8.0, 3900);
            table.put(9.0, 5000);
            table.put(10.0, 5900);
            table.put(11.0, 7200);
            table.put(12.0, 8400);
            table.put(13.0, 10000);
            table.put(14.0, 11500);
            table.put(15.0, 13000);
            table.put(16.0, 15000);
            table.put(17.0, 18000);
            table.put(18.0, 20000);
            table.put(19.0, 22000);
            table.put(20.0, 25000);
            table.put(21.0, 33000);
            table.put(22.0, 41000);
            table.put(23.0, 50000);
            table.put(24.0, 62000);
            table.put(25.0, 75000);
            table.put(26.0, 90000);
            table.put(27.0, 105000);
            table.put(28.0, 120000);
            table.put(29.0, 135000);
            table.put(30.0, 155000);
        }
    }

    //dungeon, grassland, forest, desert, mountain, roadside, Underdark, Shadowfell, Feywild, Upper Planes, and Lower Planes
    static class AreaIndexes{
        Hashtable<String, Integer> table;

        AreaIndexes(){
            table = new Hashtable<>();
            table.put("dungeon", 0);
            table.put("grassland", 1);
            table.put("forest", 2);
            table.put("desert", 3);
            table.put("mountain", 4);
            table.put("roadside", 5);
            table.put("underdark", 6);
            table.put("shadowfell", 7);
            table.put("feywild", 8);
            table.put("upper planes", 9);
            table.put("lower planes", 10);
        }
    }
}