import java.util.ArrayList;

public class Encounter {
    private ArrayList<Monster> monsters = new ArrayList<>();

    public Encounter() { }

    public void add(Monster monster) {
        monsters.add(monster);
    }

    public void displayMonsters() {
        System.out.println(monsters.size() + " monsters total: \n");
        for(int n=0;n<monsters.size(); n++) {
            int monsterCount = 1;
            while(n<monsters.size()-1 && monsters.get(n).getName().equals(monsters.get(n+1).getName())) {
                if (monsters.get(n).getName().equals(monsters.get(n + 1).getName())) {
                    monsters.remove(n + 1);
                    monsterCount++;
                }
            }
            System.out.println("---<" + monsterCount + ">---");
            monsters.get(n).fullDisplay();
            System.out.println("------------------------------------------------------------------------------------");
            System.out.println();
            new MonsterAttacker(monsters.get(n));
        }
    }

    public ArrayList<Monster> getMonsters() {
        return getMonsters(monsters);
    }


    /*
        areas are
        dungeon,
        grassland,
        forest,
        mountain,
        roadside,
        desert,
        Underdark,
        Shadowfell,
        Feywild,
        Lower Planes,
        Upper Planes
        on a scale from 0 to 4, 4 being common
    public void randomEncounter(int hexes_hours, String area, int playersProficiencyBonus) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        Scanner scanner = new Scanner(System.in);
        for(int n=0;n<hexes_hours;n++) {
            System.out.print("Interval: " + (n+1) + ": ");

            double roll = Math.random();
            if(roll<=.008889) {
                switch(area) {
                    case "dungeon":
                        //celestials
                        randomEncounterHelper(3, playersProficiencyBonus+2);
                        break;
                    case "grassland":
                    case "forest":
                    case "mountain":
                    case "roadside":
                    case "desert":
                    case "Underdark":
                        //celestials, fiends
                        int randomRoll = (int)(Math.random()*2);
                        if(randomRoll==0) {
                            randomEncounterHelper(2, playersProficiencyBonus+2);
                        }
                        else {
                            randomEncounterHelper(7, playersProficiencyBonus+2);
                        }
                        break;
                    case "Shadowfell":
                    case "Lower Planes":
                        //celestials
                        randomEncounterHelper(2, playersProficiencyBonus+2);
                        break;
                    case "Feywild":
                    case "Upper Planes":
                        //fiends
                        randomEncounterHelper(7, playersProficiencyBonus+2);
                        break;
                }
                System.out.println();
                displayMonsters();
            }
            else if (roll<=.028416) {
                //very rare encounter
                switch(area) {
                    case "dungeon":
                        //fey, fiends
                        int rollDungeon = (int)(Math.random()*2);
                        if(rollDungeon==0) {
                            randomEncounterHelper(6, playersProficiencyBonus+1);
                        }
                        else {
                            randomEncounterHelper(7, playersProficiencyBonus+1);
                        }
                        break;
                    case "grassland":
                        //dragons, fey, undead
                        int rollGrasslands = (int)(Math.random()*3);
                        if(rollGrasslands==0) {
                            randomEncounterHelper(4, playersProficiencyBonus);
                        }
                        else if(rollGrasslands==1){
                            randomEncounterHelper(6, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(13, playersProficiencyBonus);
                        }
                        break;
                    case "forest":
                    case "roadside":
                        //elementals, oozes
                        int rollForest = (int)(Math.random()*2);
                        if(rollForest==0) {
                            randomEncounterHelper(5, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(11, playersProficiencyBonus);
                        }
                        break;
                    case "mountain":
                        //aberrations, constructs, oozes
                        int rollMountain = (int)(Math.random()*3);
                        if(rollMountain==0) {
                            randomEncounterHelper(0, playersProficiencyBonus);
                        }
                        else if(rollMountain==1) {
                            randomEncounterHelper(3, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(11, playersProficiencyBonus);
                        }
                        break;
                    case "desert":
                        //constructs
                        randomEncounterHelper(3, playersProficiencyBonus);
                        break;
                    case "Underdark":
                        //aberrations, constructs, fey, undead
                        int rollUnderdark = (int)(Math.random()*4);
                        if(rollUnderdark==0){
                            randomEncounterHelper(0, playersProficiencyBonus);
                        }
                        else if(rollUnderdark==1){
                            randomEncounterHelper(3, playersProficiencyBonus);
                        }
                        else if (rollUnderdark==2) {
                            randomEncounterHelper(6, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(13, playersProficiencyBonus);
                        }
                        break;
                    case "Shadowfell":
                    case "Lower Planes":
                        //fey
                        randomEncounterHelper(6, playersProficiencyBonus);
                        break;
                    case "Upper Planes":
                    case "Feywild":
                        //undead
                        randomEncounterHelper(13, playersProficiencyBonus);
                        break;
                }
                System.out.println();
                displayMonsters();
            }
            else if (roll <= .072) {
                //rare encounter
                switch(area) {
                    case "dungeon":
                        //dragons, elementals
                        int rollDungeon = (int) (Math.random()*2);
                        if(rollDungeon==0) {
                            randomEncounterHelper(4, playersProficiencyBonus+1);
                        }
                        else {
                            randomEncounterHelper(5, playersProficiencyBonus+1);
                        }
                        break;
                    case "grassland":
                    case "roadside":
                        //giants, undead
                        int rollGrassland = (int)(Math.random()*2);
                        if(rollGrassland==0) {
                            randomEncounterHelper(8, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(13, playersProficiencyBonus);
                        }
                        break;
                    case "forest":
                        //aberrations, giants, undead
                        int rollForest = (int) (Math.random()*3);
                        if(rollForest==0) {
                            randomEncounterHelper(0, playersProficiencyBonus);
                        }
                        else if(rollForest==1) {
                            randomEncounterHelper(8, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(13, playersProficiencyBonus);
                        }
                        break;
                    case "mountain":
                        //elementals, giants, undead
                        int rollMountain = (int)(Math.random()*3);
                        if(rollMountain==0) {
                            randomEncounterHelper(5, playersProficiencyBonus);
                        }
                        else if(rollMountain==1) {
                            randomEncounterHelper(8, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(13, playersProficiencyBonus);
                        }
                        break;
                    case "desert":
                        //dragons, humanoids
                        int rollDesert = (int)(Math.random()*2);
                        if(rollDesert==0) {
                            randomEncounterHelper(4, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(9, playersProficiencyBonus);
                        }
                        break;
                    case "Underdark":
                        //elementals, giants
                        int rollUnderdark = (int)(Math.random()*2);
                        if(rollUnderdark==0) {
                            randomEncounterHelper(5, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(8, playersProficiencyBonus);
                        }
                        break;
                    case "Shadowfell":
                        //fiends, giants
                        int rollShadowfell = (int)(Math.random()*2);
                        if(rollShadowfell==0) {
                            randomEncounterHelper(7, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(8, playersProficiencyBonus);
                        }
                        break;
                    case "Feywild":
                        //celestials, plants
                        int rollFeywild = (int)(Math.random()*2);
                        if(rollFeywild==0) {
                            randomEncounterHelper(2, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(12, playersProficiencyBonus);
                        }
                        break;
                    case "Upper Planes":
                    case "Lower Planes":
                        //constructs, elementals
                        int rollPlanes = (int)(Math.random()*2);
                        if(rollPlanes==0) {
                            randomEncounterHelper(3, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(5, playersProficiencyBonus);
                        }
                        break;
                }
                System.out.println();
                displayMonsters();
            }
            else if (roll <= .090666) {
                //uncommon encounter
                switch(area) {
                    case "dungeon":
                        //aberrations, beasts, constructs
                        int rollDungeon = (int) (Math.random()*3);
                        if (rollDungeon==0) {
                            randomEncounterHelper(0, playersProficiencyBonus+1);
                        }
                        else if(rollDungeon==1) {
                            randomEncounterHelper(1, playersProficiencyBonus+1);
                        }
                        else {
                            randomEncounterHelper(3, playersProficiencyBonus+1);
                        }
                        break;
                    case "grassland":
                        //monstrosities, plants
                        int rollGrassland = (int) (Math.random()*2);
                        if(rollGrassland==0) {
                            randomEncounterHelper(10, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(12, playersProficiencyBonus);
                        }
                        break;
                    case "forest":
                        //fey, monstrosities, plants
                        int rollForest = (int) (Math.random()*3);
                        if(rollForest==0) {
                            randomEncounterHelper(6, playersProficiencyBonus);
                        }
                        else if (rollForest==1) {
                            randomEncounterHelper(10, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(12, playersProficiencyBonus);
                        }
                        break;
                    case "mountain":
                        //giants, monstrosities
                        int rollMountain = (int)(Math.random()*2);
                        if(rollMountain==0) {
                            randomEncounterHelper(8, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(10, playersProficiencyBonus);
                        }
                        break;
                    case "roadside":
                        //monstrosities
                        randomEncounterHelper(10, playersProficiencyBonus);
                        break;
                    case "desert":
                        //monstrosities, undead
                        int rollDesert = (int)(Math.random()*2);
                        if(rollDesert==0) {
                            randomEncounterHelper(10, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(13, playersProficiencyBonus);
                        }
                        break;
                    case "Underdark":
                        //giants, humanoids -- drow, svirfneblin, duergar, oozes
                        MonsterDirectory monsterDirectory = new MonsterDirectory();
                        int monsterNumber = (int)(Math.random()*5);
                        int rollUnderdark = (int)(Math.random()*3);
                        if(rollUnderdark==0) {
                            for(int i=0;i<=monsterNumber;i++) {
                                int directory = 9;
                                int type = (int) (Math.random()*3);
                                Monster monster;
                                int roll2 = (int) (Math.random()*monsterDirectory.getDirectory(directory).size());
                                if (type==0) {
                                    int roll3 = (int) (Math.random()*monsterDirectory.getSubdirectory(directory, 0).size());
                                    monster = monsterDirectory.getMonster(directory, 0, roll3);
                                    while (!(monster.getProficiencyBonus()>=playersProficiencyBonus-1 && monster.getProficiencyBonus()<=playersProficiencyBonus+1)) {
                                        roll2 = (int) (Math.random()*monsterDirectory.getDirectory(directory).size());
                                        roll3 = (int) (Math.random()*monsterDirectory.getSubdirectory(directory, roll2).size());
                                        monster = monsterDirectory.getMonster(directory, roll2, roll3);
                                    }
                                }
                                else if(type==1) {
                                    int roll3 = (int) (Math.random()*monsterDirectory.getSubdirectory(directory,1).size());
                                    monster = monsterDirectory.getMonster(directory, 1, roll3);
                                    while (!(monster.getProficiencyBonus()>=playersProficiencyBonus-1 && monster.getProficiencyBonus()<=playersProficiencyBonus+1)) {
                                        roll2 = (int) (Math.random()*monsterDirectory.getDirectory(directory).size());
                                        roll3 = (int) (Math.random()*monsterDirectory.getSubdirectory(directory, roll2).size());
                                        monster = monsterDirectory.getMonster(directory, roll2, roll3);
                                    }
                                }
                                else{
                                    monster = monsterDirectory.getMonster(directory, 0, 3);
                                }
                                add(monster);
                            }
                        }
                        else if(rollUnderdark==1) {
                            randomEncounterHelper(8, playersProficiencyBonus);
                        }
                        else{
                            randomEncounterHelper(11, playersProficiencyBonus);
                        }
                        break;

                    case "Shadowfell":
                        //beasts, monstrosities, oozes
                        int rollShadowfell = (int) (Math.random()*3);
                        if (rollShadowfell==0) {
                            randomEncounterHelper(1, playersProficiencyBonus);
                        }
                        else if (rollShadowfell==1) {
                            randomEncounterHelper(10, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(11, playersProficiencyBonus);
                        }
                        break;
                    case "Feywild":
                        //humanoids, monstrosities
                        int rollFeywild = (int) (Math.random()*2);
                        if(rollFeywild==0) {
                            randomEncounterHelper(9, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(10, playersProficiencyBonus);
                        }
                        break;
                    case "Upper Planes":
                        //humanoids
                        randomEncounterHelper(9, playersProficiencyBonus);
                        break;
                    case "Lower Planes":
                        //humanoids, aberrations
                        int rollLower = (int) (Math.random()*2);
                        if(rollLower==0) {
                            randomEncounterHelper(0, playersProficiencyBonus);
                        }
                        else {
                            randomEncounterHelper(9, playersProficiencyBonus);
                        }
                        break;
                }
                System.out.println();
                displayMonsters();
            }
            else if(roll <=.15666) {
                //common encounter
                switch(area) {
                    case "dungeon":
                        int rollDungeon = (int) (Math.random()*4);
                        if (rollDungeon==0) {
                            randomEncounterHelper(9, playersProficiencyBonus+1);
                        }
                        else if (rollDungeon==1) {
                            randomEncounterHelper(10, playersProficiencyBonus+1);
                        }
                        else if (rollDungeon==2) {
                            randomEncounterHelper(11, playersProficiencyBonus+1);
                        }
                        else {
                            randomEncounterHelper(13, playersProficiencyBonus+1);
                        }
                        break;
                    case "grassland":
                        int roll1 = (int) (Math.random()*2);
                        if(roll1 == 0) {
                            randomEncounterHelper(1, playersProficiencyBonus);
                        }
                        else if (roll1 ==1) {
                            randomEncounterHelper(9, playersProficiencyBonus);
                        }
                        break;
                    case "forest":
                    case "mountain":
                    case "desert":
                        randomEncounterHelper(1, playersProficiencyBonus);
                        break;
                    case "roadside":
                        randomEncounterHelper(9, playersProficiencyBonus);
                        break;
                    case "Underdark":
                        randomEncounterHelper(10, playersProficiencyBonus);
                        break;
                    case "Shadowfell":
                        randomEncounterHelper(13, playersProficiencyBonus);
                        break;
                    case "Feywild":
                        randomEncounterHelper(6, playersProficiencyBonus);
                        break;
                    case "Upper Planes":
                        randomEncounterHelper(2, playersProficiencyBonus);
                        break;
                    case "Lower Planes":
                        randomEncounterHelper(7, playersProficiencyBonus);
                        break;
                }
                System.out.println();
                displayMonsters();
            }
            else {
                System.out.println("No encounter");
            }
            System.out.println();
            if(n<hexes_hours-1) {
                System.out.print("Type 'next' to continue: ");
                while (!scanner.next().equals("next")){
                }
                System.out.println();
            }
            removeAllMonsters();
        }
    }


    private void randomEncounterHelper (int directory, int playersProficiencyBonus) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        MonsterDirectory monsterDirectory = new MonsterDirectory();
        int monsterNumber = (int) ((Math.random()*5)+1);
        int roll2 = (int) (Math.random()*monsterDirectory.getDirectory(directory).size());
        int roll3 = (int) (Math.random()*monsterDirectory.getSubdirectory(directory, roll2).size());
        for(int i=0;i<=monsterNumber;i++) {
            double differentRoll2 = Math.random();
            double differentRoll3 = Math.random();
            if(i>0 && differentRoll2>.9) {
                roll2 = (int) (Math.random()*monsterDirectory.getDirectory(directory).size());
            }
            if(i>0 && differentRoll3>.65 && differentRoll2<=.9) {
                roll3 = (int) (Math.random()*monsterDirectory.getSubdirectory(directory, roll2).size());
            }
            Monster monster = monsterDirectory.getMonster(directory, roll2, roll3);
            boolean workableProficiencyBonus = false;
            for(Monster m : monsterDirectory.getSubdirectory(directory, roll2)) {
                if (m.getProficiencyBonus()<=playersProficiencyBonus+1 && m.getProficiencyBonus()>=playersProficiencyBonus-1) {
                    workableProficiencyBonus=true;
                    break;
                }
            }
            if (workableProficiencyBonus) {
                while (!(monster.getProficiencyBonus()>=playersProficiencyBonus-1 && monster.getProficiencyBonus()<=playersProficiencyBonus+1)) {
                    roll2 = (int) (Math.random()*monsterDirectory.getDirectory(directory).size());
                    roll3 = (int) (Math.random()*(monsterDirectory.getSubdirectory(directory, roll2).size()));
                    monster = monsterDirectory.getMonster(directory, roll2, roll3);
                }
            }
            add(monster);
            if (monster.getProficiencyBonus()>playersProficiencyBonus) {
                i++;
            }
        }
    }
     */

    private ArrayList<Monster> getMonsters(ArrayList<Monster> monsters) {
        return monsters;
    }

    private void removeAllMonsters() {
        if (monsters.size() >= 1) {
            monsters.subList(0, monsters.size()).clear();
        }
    }
}