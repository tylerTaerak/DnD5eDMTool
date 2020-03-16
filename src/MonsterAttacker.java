import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MonsterAttacker extends Frame implements WindowListener {

    MonsterAttacker(Monster monster) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        ArrayList<Button> buttons = new ArrayList<>();
        setLayout(new FlowLayout());
        setVisible(true);
        setTitle("Monster Attacker:: " + monster.getName());
        setLayout(new FlowLayout());
        pack();
        setVisible(true);
        setSize(600, 300);
        TextArea txtOutput = new TextArea();
        txtOutput.setEditable(false);
        if(monster.isMultiattackImplemented()) {
            buttons.add(new Button("Multiattack"));
            add(buttons.get(0));
        }
        for(int n=0;n<monster.attacks.size();n++) {
            buttons.add(new Button(monster.attacks.get(n).getName()));
            if(monster.isMultiattackImplemented()) {
                add(buttons.get(n+1));
            }
            else{
                add(buttons.get(n));
            }
        }
        for(int n=0;n<buttons.size();n++) {
            int finalN = n;
            if(monster.isMultiattackImplemented()&&n==0){
                buttons.get(n).addActionListener(event -> txtOutput.setText(monster.multiattack()));
                continue;
            }
            buttons.get(n).addActionListener(event -> {
                if (monster.isMultiattackImplemented()) {
                    txtOutput.setText(monster.attack(monster.attacks.get(finalN-1)));
                }
                else{
                    txtOutput.setText(monster.attack(monster.attacks.get(finalN)));
                }
            });
            add(txtOutput);
        }
    }

    @Override
    public void windowOpened(WindowEvent arg0) { }

    @Override
    public void windowClosing(WindowEvent event) {
        dispose();
    }

    @Override
    public void windowClosed(WindowEvent event) {}

    @Override
    public void windowIconified(WindowEvent event) {}

    @Override
    public void windowDeiconified(WindowEvent event) {}

    @Override
    public void windowActivated(WindowEvent event) {}

    @Override
    public void windowDeactivated(WindowEvent event) {}
}
