import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TreeSet;


public class MonsterDirectory {
    private TreeSet<Monster> tree; // a tree containing all of the monsters in the /txt folder
    private ArrayList<Hashtable<Integer, ArrayList<Monster>>> probabilities; // a data structure containing relative probabilities of finding a monster in a certain area.  Probabilities are listed on the last two lines of the monster file


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
        File[] monsters = new File("src/txt").listFiles();

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

    Monster[] searchMonsters(String name, String type, int prof, double CR){
        return new Monster[1];
    }

    Monster[] searchMonstersByName(String name) {
        ArrayList<Monster> monsters = new ArrayList<>();
        for(Monster m : tree) {
            if(m.getName().contains(name)) {
                monsters.add(m);
            }
        }
        Monster[] lst = new Monster[monsters.size()];
        for(int i = 0; i<monsters.size(); i++) {
            lst[i] = monsters.get(i);
        }
        return lst;
    }

    Monster[] searchMonstersByType(String type) {
        ArrayList<Monster> monsters = new ArrayList<>();
        for(Monster m : tree) {
            String typ = m.getType();
            if(m.getType().contains(" ")) {
                typ = m.getType().split("\\s+")[0];
            }
            if(typ.equals(type)) {
                monsters.add(m);
            }
        }
        Monster[] lst = new Monster[monsters.size()];
        for(int i = 0; i<monsters.size(); i++) {
            lst[i]= monsters.get(i);
        }
        return lst;
    }

    Monster[] searchMonstersByProficiency(int prof) {
        ArrayList<Monster> monsters = new ArrayList<>();
        for(Monster m : tree) {
            if(m.getProficiencyBonus() == prof) {
                monsters.add(m);
            }
        }
        Monster[] lst = new Monster[monsters.size()];
        for(int i = 0; i<monsters.size(); i++) {
            lst[i]= monsters.get(i);
        }
        return lst;
    }

    Monster[] searchMonstersByCR(double CR) {
        ArrayList<Monster> monsters = new ArrayList<>();
        for(Monster m : tree) {
            if(m.getCR() == CR) {
                monsters.add(m);
            }
        }
        Monster[] lst = new Monster[monsters.size()];
        for(int i = 0; i<monsters.size(); i++) {
            lst[i]= monsters.get(i);
        }
        return lst;
    }

    private void addProbabilities(Monster monster, int[] probs) {
        for(int i = 0; i<probs.length; i++) {
            probabilities.get(i).get(probs[i]).add(monster);
        }
    }
}