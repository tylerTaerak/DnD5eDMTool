import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TreeSet;


public class MonsterDirectory {
    private TreeSet<Monster> tree; // a tree containing all of the monsters in the /txt folder
    private ArrayList<Hashtable<Integer, ArrayList<Monster>>> probabilities; // a data structure containing relative probabilities of finding a monster in a certain area.  Probabilities are listed on the last two lines of the monster file

    private final String PATH = "src/txt"; //the file path used in the class. This may need to be manually changed depending on the compiling tool


    public MonsterDirectory()
            throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException,
            IOException
    {
        tree = new TreeSet<>();

        probabilities = new ArrayList<>();
        for(int i =0; i<11; i++) {
            probabilities.add(new Hashtable<>(5));
            for(int j=0; j<5; j++) {
                probabilities.get(i).put(j, new ArrayList<>());
            }
        }


        MonsterReader mr = new MonsterReader();
        File[] monsters = new File(PATH).listFiles();

        assert monsters != null;

        for(File f : monsters) { ;
            Monster m = mr.readMonsterFromFile(f);
            //System.out.println(m.getName() + " added");
            tree.add(m);
            addProbabilities(m, mr.readProbabilitiesFromFile(f));
        }

    }

    public void display() {
        for(Monster m : tree) {
            System.out.println(m.getName());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Monster m : tree){
            sb.append(m.getName()).append("\n");
        }
        return sb.toString();
    }

    public TreeSet<Monster> getTree() {
        return tree;
    }

    public ArrayList<Hashtable<Integer, ArrayList<Monster>>> getProbabilities() {
        return probabilities;
    }

    Monster[] searchMonsters(String name, String type, Integer prof, Double CR){
        ArrayList<Monster> monsters = new ArrayList<>();
        for (Monster m : tree){
            boolean n = false;
            boolean t = false;
            boolean p = false;
            boolean c = false;
            if (name == null){
                n = true;
            }
            else if (m.getName().contains(name)){
                n = true;
            }

            if (type == null){
                t = true;
            }
            else if (m.getType().contains(type)){
                t = true;
            }

            if(prof == null){
                p = true;
            }
            else if (m.getProficiencyBonus() == prof){
                p = true;
            }

            if (CR == null){
                c = true;
            }
            else if (m.getCR() == CR){
                c = true;
            }

            if (n && t && p && c){
                monsters.add(m);
            }

        }

        Monster[] monsterArray = new Monster[monsters.size()];
        for (int i = 0; i<monsterArray.length; i++){
            monsterArray[i] = monsters.get(i);
        }

        return monsterArray;
    }

    private void addProbabilities(Monster monster, int[] probs) {
        for(int i = 0; i<probs.length; i++) {
            probabilities.get(i).get(probs[i]).add(monster);
        }
    }
}