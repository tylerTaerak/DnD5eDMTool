public class LegendaryAction implements Autowrap {
    private String name;
    private int cost;
    private String effect;

    public LegendaryAction(String name, int cost, String effect) {
        this.name = name;
        this.cost = cost;
        this.effect = effect;
    }


    public String toString() {
        return autoWrap(name +
                " (Costs " + cost + " Action(s)): " +
                effect);
    }


    void displayLA() {
        System.out.println(getName() + " (Costs " + getCost() + "): " + getEffect());
    }

    private String getName() {
        return getName(this.name);
    }

    private int getCost() {
        return getCost(this.cost);
    }

    public String getEffect() {
        return getEffect(this.effect);
    }

    private String getName(String name) {
        return name;
    }

    private int getCost(int cost) {
        return cost;
    }

    private String getEffect(String effect) {
        return effect;
    }

}
