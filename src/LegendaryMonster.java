import java.util.ArrayList;

public class LegendaryMonster extends Monster {
    protected ArrayList<LegendaryAction> legendaryActions = new ArrayList<>();
    protected Lair lair;

    public LegendaryMonster(){}

    public LegendaryMonster(
            String name,
            String size,
            String type,
            int proficiencyBonus,
            int ac,
            int hpAvg,
            String speed,
            int STR,
            boolean StrProficiency,
            int DEX,
            boolean DexProficiency,
            int CON,
            boolean ConProficiency,
            int INT,
            boolean IntProficiency,
            int WIS,
            boolean WisProficiency,
            int CHA,
            boolean ChaProficiency,
            int[] skills,
            String senses,
            String languages,
            double cr)
    {
        super(name, size, type, proficiencyBonus, ac, hpAvg, speed, STR, StrProficiency, DEX, DexProficiency, CON, ConProficiency, INT, IntProficiency, WIS, WisProficiency, CHA, ChaProficiency, skills, senses, languages, cr);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if(legendaryActions.size()>0){
            sb.append("----- Legendary Actions -----\n");
            sb.append(autoWrap("This creature can take 3 legendary actions, choosing from below. Only one legendary action option can be used at a time and only at the end of another creature's turn. The creature regains spent legendary actions at the start of its turn\n"));
            for(LegendaryAction l : legendaryActions) {
                sb.append(l.toString()).append("\n");
            }
            sb.append("\n");
        }
        if(lair!=null) {
            sb.append(lair.toString());
        }


        return sb.toString();
    }

    /**
     * TODO: Add a .toPane() function that adds on to the one in the Monster class, and gives legendary actions & lair info
     */


    private void displayLA() {
        System.out.println("---Legendary Actions---\n");
        for (LegendaryAction n : legendaryActions) {
            System.out.print("* ");
            n.displayLA();
        }
        System.out.println();
    }

    public void fullDisplay() {
        display1();
        displayPassives();
        display2();
        displayLA();
        if(lair!=null){
            lair.display();
        }

    }

    public Lair getLair() { return getLair(this.lair); }

    private Lair getLair(Lair lair) { return lair; }
}
