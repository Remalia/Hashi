import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LevelEditor extends JPanel{
    private Grille grille;

    private List<ButtonPos> lb;
    private boolean creerIle = true;

    private int qtePont = 1;

    private List<ButtonPos> save;

    LevelEditor(){
        this.lb = new ArrayList<>();
        this.save = new ArrayList<>();
        createPlageButton(10,10);
    }

    private GridLayout createPlageButton(int x, int y) {
        GridLayout grille = new GridLayout(x+1, y+1, 5, 5);
        this.setLayout(grille);
        for(int i =0;i<x;i++) {
            for (int j = 0; j < y; j++) {
                ButtonPos b = new ButtonPos(x,y);
                lb.add(b);
                this.add(b.getButton());
            }
        }
        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(buttonIleOrPont());
        this.add(buttonQTEPont());
        this.add(new JLabel(""));
        this.add(buttonExport());
        return grille;
    }
    private class ButtonPos{
        private JButton b;
        private int x;
        private int y;
        ButtonPos(int x,int y){
            this.b = buttonCreator();
            this.x = x;
            this.y = y;
        }

        public JButton getButton() {
            return b;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
        private JButton buttonCreator(){
            JButton button = new JButton("");
            button.addActionListener(e -> {
                if(creerIle) {
                    String nextText = getNextValueFromText(b.getText());
                    b.setText(nextText);
                }else{
                    String text = b.getText();
                    if(text.matches("[1-8]")){
                        if(save.size() < 2)
                            save.add(this);
                        if(save.size() >=2){
                            ButtonPos b1 = save.get(0);
                            ButtonPos b2 = save.get(1);

                            save.clear();
                        }
                    }
                }
            });
            return button;
        }

    }

    private JButton buttonQTEPont() {
        JButton button = new JButton("QTE : " + qtePont);
        button.addActionListener(e -> {
            if(qtePont == 1)
                qtePont++;
            else
                qtePont--;
           button.setText("QTE : " + qtePont);
        });
        return button;
    }


    private JButton buttonIleOrPont(){
        JButton button = new JButton("Ile");
        button.addActionListener(e -> {
            if(creerIle){
                creerIle = false;
                button.setText("Pont");
            }else{
                creerIle = true;
                button.setText("Ile");
            }
        });
        return button;
    }

    private JButton buttonExport(){
        JButton button = new JButton("Exporter");
        button.addActionListener(e -> exporterGrille());
        return button;
    }

    private String getNextValueFromText(String text){
        String result = "";
        switch (text){
            case "":
                if(creerIle)
                    result = "1";
                else
                    result = "-";
                break;
            case "1":
                result = "2";
                break;
            case "2":
                result = "3";
                break;
            case "3":
                result = "4";
                break;
            case "4":
                result = "5";
                break;
            case "5":
                result = "6";
                break;
            case "6":
                result = "7";
                break;
            case "7":
                result = "8";
                break;
            case "8":
            case "=":
                result = "";
                break;
            case "-":
                result = "=";
                break;
        }
        return result;
    }

    public void exporterGrille(){
        System.out.println("EXPORT");
    }
    public static void main(String args[]){
        JFrame jframe =  new JFrame("LevelEditor");
        LevelEditor level = new LevelEditor();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.getContentPane().add(level);
        jframe.setSize(1200,1000);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }
}
