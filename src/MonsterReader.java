import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MonsterReader implements Dice {

    public static void main(String[] args)
            throws NoSuchMethodException,
            IOException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            ClassNotFoundException
    {
        MonsterReader mr = new MonsterReader();

        LegendaryMonster aboleth = (LegendaryMonster) mr.readMonsterFromFile(new File("src/txt/Aboleth"));

        System.out.println(aboleth.toString());
    }

    public Monster readMonsterFromFile(File file)
            throws IOException,
            ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException
    {
        BufferedReader input = new BufferedReader(new FileReader(file));
        String[] strings1 = input.readLine().split("\\s+");
        Monster monster = constructMonster(strings1);
        String[] hpString = input.readLine().split("\\s+");
        monster.hpVaried = d(false, makeInt(hpString[0]), makeInt(hpString[1]), makeInt(hpString[2]));
        String line;
        if(input.readLine().contains("Passives")) {
            while(!(line = input.readLine()).contains("===")){
                monster.passives.add(line);
            }
            String[] attacks = line.split("\\s+");
            ArrayList<Integer> indices = new ArrayList<>();
            int i = 2;
            while(!attacks[i].contains("===")){
                if(attacks[i].equals("null")) {
                    monster.setMultiattack(null);
                    break;
                }
                indices.add(makeInt(attacks[i]));
                i++;
            }
            int[] multiattack = new int[indices.size()];
            for(int n = 0; n<indices.size(); n++) {
                multiattack[n] = indices.get(n);
            }
            monster.setMultiattack(multiattack);
        }
        else {
            line = input.readLine();
            String[] attacks = line.split("\\s+");
            ArrayList<Integer> indices = new ArrayList<>();
            int i = 2;
            while(!attacks[i].contains("===")){
                if(attacks[i].contains("null")) {
                    monster.setMultiattack(null);
                    break;
                }
                indices.add(makeInt(attacks[i]));
                i++;
            }
            int[] multiattack = new int[indices.size()];
            for(int n = 0; n<indices.size(); n++) {
                multiattack[n] = indices.get(n);
            }
            monster.setMultiattack(multiattack);
        }
        while((line = input.readLine())!=null && !line.contains("===")){
            String[] strings = line.split("\\s+");
            ArrayList<String> list = new ArrayList<>(Arrays.asList(strings));
            if(list.get(0).equals("Attack")) {
                list.remove(0);
                String[] sList = new String[list.size()];
                for(int i = 0; i<list.size(); i++) {
                    sList[i] = list.get(i);
                }
                monster.attacks.add(constructAttack(sList));
            }
            else if(list.get(0).equals("DCAttack")) {
                list.remove(0);
                String[] sList = new String[list.size()];
                for(int i = 0; i<list.size(); i++) {
                    sList[i] = list.get(i);
                }
                monster.attacks.add(constructDCAttack(sList));
            }
        }
        if(monster instanceof LegendaryMonster) {
            if(line!=null && line.contains("Legendary Actions")) {
                while(!((line = input.readLine()) ==null) && !line.contains("===")) {
                    String[] parameters = line.split("\\s+");
                    ((LegendaryMonster) monster).legendaryActions.add(constructLegendaryAction(parameters));
                }
            }
            if(line != null && line.contains("Lair")) {
                ArrayList<DCAttack> lairActions = null;
                ArrayList<String> regionalEffects = null;
                if((line = input.readLine()).contains("lairActions")) {
                    lairActions = new ArrayList<>();
                    while(!(line = input.readLine()).contains("---")) {
                        String[] parameters = line.split("\\s+");
                        lairActions.add(constructDCAttack(parameters));
                    }
                }
                if(line.contains("regionalEffects")){
                    regionalEffects = new ArrayList<>();
                    while(!(line = input.readLine()).contains("---")) {
                        regionalEffects.add(line);
                    }
                }

                ((LegendaryMonster) monster).lair = new Lair(input.readLine(), lairActions, regionalEffects);
            }
        }
        return monster;
    }

    public int[] readProbabilitiesFromFile(File file)
            throws IOException
    {
        BufferedReader input = new BufferedReader(new FileReader(file));
        String line;
        while((line=input.readLine())!=null && (!line.contains("===") || !line.contains("Areas"))){
            //do nothing, we want the last line
        }
        line = input.readLine();
        String[] probs = line.split("\\s+");
        int[] intProbs = new int[probs.length];
        for(int i = 0; i<probs.length; i++) {
            intProbs[i] = makeInt(probs[i]);
        }
        return intProbs;
    }

    private Object constructObject(String cl, String[] params)
            throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException
    {
        switch (cl) {
            case ("DCAttack"):
                return constructDCAttack(params);
            case ("Attack"):
                return constructAttack(params);
            case ("String"):
                return constructString(params);
            case ("int[]"):
                return constructIntArray(params);
            default:
                return null;
        }
    }

    private Object[] getParameters(String[] parameters)
            throws NoSuchMethodException,
            ClassNotFoundException,
            IllegalAccessException,
            InstantiationException,
            InvocationTargetException
    {
        ArrayList<Object> objects = new ArrayList<>();
        ArrayList<String> pArray = new ArrayList<>(Arrays.asList(parameters));
        for (int n=0;n<pArray.size();n++) {
            pArray.set(n, pArray.get(n).replaceAll("\\s", ""));
            if(pArray.get(n).equals("null")) {
                objects.add(null);
            }
            else if (pArray.get(n).equals("true") || pArray.get(n).equals("false")) {
                objects.add(makeBool(pArray.get(n)));
            }
            else if (pArray.get(n).matches("T(-?(?!0)\\d+)")){
                objects.add(makeInt(pArray.get(n)));
            }
            else if(pArray.get(n).contains("{")) {
                String cl = pArray.get(n).replace("{", "");

                List<String> subList = pArray.subList(n, pArray.size() - 1);
                List<String> bracketed = subList.subList(0, getBracketedIndices(subList));

                bracketed.remove(0);
                bracketed.remove(bracketed.size()-1);

                String[] st = new String[bracketed.size()];
                for (int i = 0; i < bracketed.size(); i++) {
                    st[i] = bracketed.get(i);
                }

                Object o = constructObject(cl, st);
                objects.add(o);

                pArray.subList(n, n+bracketed.size()).clear();
                n--;
            }
            else {
                objects.add(pArray.get(n));
            }
        }
        return objects.toArray();
    }

    private Monster constructMonster(String[] string)
            throws ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException
    {
        Class<?> monster = Class.forName(string[0]);
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(string));
        arrayList.remove(0);
        String[] newStrings = new String[arrayList.size()];

        for(int i = 0; i<arrayList.size(); i++) {
            newStrings[i] = arrayList.get(i);
        }

        Constructor<?> monsterConstructor =
                monster.getConstructor(
                        String.class,
                        String.class,
                        String.class,
                        int.class,
                        int.class,
                        int.class,
                        String.class,
                        int.class,
                        boolean.class,
                        int.class,
                        boolean.class,
                        int.class,
                        boolean.class,
                        int.class,
                        boolean.class,
                        int.class,
                        boolean.class,
                        int.class,
                        boolean.class,
                        int[].class,
                        String.class,
                        String.class,
                        double.class
                );

        Object[] parameters = getParameters(newStrings);

        return (Monster) monsterConstructor.newInstance(
                parameters[0].toString(),
                parameters[1].toString(),
                parameters[2].toString(),
                makeInt(parameters[3].toString()),
                makeInt(parameters[4].toString()),
                makeInt(parameters[5].toString()),
                parameters[6].toString(),
                makeInt(parameters[7].toString()),
                makeBool(parameters[8].toString()),
                makeInt(parameters[9].toString()),
                makeBool(parameters[10].toString()),
                makeInt(parameters[11].toString()),
                makeBool(parameters[12].toString()),
                makeInt(parameters[13].toString()),
                makeBool(parameters[14].toString()),
                makeInt(parameters[15].toString()),
                makeBool(parameters[16].toString()),
                makeInt(parameters[17].toString()),
                makeBool(parameters[18].toString()),
                (int[]) parameters[19],
                parameters[20].toString(),
                parameters[21].toString(),
                makeDouble(parameters[22].toString())
        );
    }

    private int[] constructIntArray(String[] string) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for(String s: string) {
            if (s.matches("^\\d+$")){
                arrayList.add(makeInt(s));
            }
        }
        int[] array = new int[arrayList.size()];
        for(int n=0;n<arrayList.size(); n++) {
            array[n] = arrayList.get(n);
        }
        return array;
    }

    private String constructString(String[] string) {
        StringBuilder sb = new StringBuilder();
        for(String s: string) {
            sb.append(s).append(" ");
        }
        return sb.toString();
    }

    private Attack constructAttack(String[] string)
            throws ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException,
            InstantiationException,
            InvocationTargetException
    {
        Class<?> attack = Class.forName("Attack");
        Constructor<?> attackConstructor =
                attack.getConstructor(
                        String.class,
                        int.class,
                        int.class,
                        int.class,
                        int.class,
                        int.class,
                        String.class,
                        Object.class
                );

        Object[] parameters = getParameters(string);

        return (Attack) attackConstructor.newInstance(
                (String) parameters[0],
                makeInt(parameters[1].toString()),
                makeInt(parameters[2].toString()),
                makeInt(parameters[3].toString()),
                makeInt(parameters[4].toString()),
                makeInt(parameters[5].toString()),
                (String) parameters[6],
                parameters[7]
        );
    }

    private DCAttack constructDCAttack(String[] string)
            throws ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException
    {
        Class<?> dcAttack = Class.forName("DCAttack");
        Constructor<?> dcConstructor =
                dcAttack.getConstructor(
                        String.class,
                        int.class,
                        int.class,
                        int.class,
                        String.class,
                        int.class,
                        int.class,
                        int.class,
                        String.class,
                        String.class,
                        String.class,
                        boolean.class
                );

        Object[] parameters = getParameters(string);

        return (DCAttack) dcConstructor.newInstance(
                (String) parameters[0],
                makeInt(parameters[1].toString()),
                makeInt(parameters[2].toString()),
                makeInt(parameters[3].toString()),
                (String) parameters[4],
                makeInt(parameters[5].toString()),
                makeInt(parameters[6].toString()),
                makeInt(parameters[7].toString()),
                (String) parameters[8],
                (String) parameters[9],
                (String) parameters[10],
                makeBool(parameters[11].toString())
        );
    }

    private LegendaryAction constructLegendaryAction(String[] string)
            throws ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException
    { Class<?> legAct = Class.forName("LegendaryAction");
        Constructor<?> legConstructor =
                legAct.getConstructor(
                        String.class,
                        int.class,
                        String.class
                );
        Object[] parameters = getParameters(string);
        return (LegendaryAction) legConstructor.newInstance(
                (String) parameters[0],
                makeInt(parameters[1].toString()),
                (String) parameters[2]
        );
    }

    private int makeInt(String string) {
        return Integer.parseInt(string);
    }

    private boolean makeBool(String string) {
        return Boolean.parseBoolean(string);
    }

    private double makeDouble(String string) {
        return Double.parseDouble(string);
    }

    private int getBracketedIndices(List<String> list) {
        int count = 0;
        int index = 0;
        for(String s : list) {
            if(s.contains("{")) {
                count++;
            }
            if(s.contains("}")) {
                count--;
            }
            index++;
            if(count <= 0) {
                break;
            }
        }
        return index;
    }
}
