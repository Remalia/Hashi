package Application.BackEnd;

import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Grille.Ile;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelEditor extends JPanel{
    private Grille grille;

    private List<ButtonPos> lb;
    private boolean creerIle = true;

    private int qtePont = 1;

    private List<ButtonPos> save;

    LevelEditor() throws IOException {
        this.lb = new ArrayList<>();
        this.save = new ArrayList<>();
        this.grille = new Grille("levelEditor");
        createPlageButton(10,10);
    }

    private void createPlageButton(int x, int y) {
        GridLayout grille = new GridLayout(x+1, y+1, 5, 5);
        this.setLayout(grille);
        for(int i =0;i<x;i++) {
            for (int j = 0; j < y; j++) {
                ButtonPos b = new ButtonPos(i,j);
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
                    String nextText = getNextValueFromText(b.getText(),x,y);
                    b.setText(nextText);
                }else{
                    String text = b.getText();
                    if(text.matches("[1-8]")){
                        if(save.size() < 2)
                            save.add(this);
                        if(save.size() >=2){
                            ButtonPos b1 = save.get(0);
                            ButtonPos b2 = save.get(1);
                            if(b1 != b2){
                                grille.ajouterPont(grille.getIleFromPos(b1.getX(),b1.getY()),grille.getIleFromPos(b2.getX(),b2.getY()),qtePont);
                                int xMax,xMin,yMax,yMin;
                                if(b1.getX() > b2.getX()){
                                    xMax = b1.getX();
                                    xMin = b2.getX();
                                }else{
                                    xMin = b1.getX();
                                    xMax = b2.getX();
                                }
                                if(b1.getY() > b2.getY()){
                                    yMax = b1.getY();
                                    yMin = b2.getY();
                                }else {
                                    yMin = b1.getY();
                                    yMax = b2.getY();
                                }
                                for (ButtonPos bp: lb) {
                                        if(bp.getX() == xMax && xMax == xMin && bp.getY() > yMin && bp.getY() < yMax) {
                                            bp.getButton().setText(qtePont == 1 ? "-" : "=");
                                        }
                                        if(bp.getY() == yMax && yMax == yMin && bp.getX() > xMin && bp.getX() < xMax) {
                                            bp.getButton().setText(qtePont == 1 ? "|" : "||");
                                        }
                                }
                                save.clear();
                            }
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
        JButton button = new JButton("Application.BackEnd.Grille.Ile");
        button.addActionListener(e -> {
            if(creerIle){
                creerIle = false;
                button.setText("Application.BackEnd.Grille.Pont");
            }else{
                creerIle = true;
                button.setText("Application.BackEnd.Grille.Ile");
            }
        });
        return button;
    }

    private JButton buttonExport(){
        JButton button = new JButton("Exporter");
        button.addActionListener(e -> {
            try {
                exporterGrille();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return button;
    }

    private String getNextValueFromText(String text,int x, int y){
        String result = "";
        switch (text){
            case "":
                if(creerIle){
                    result = "1";
                    int i= 1;
                    for (Ile ile :grille.getListIle()) {
                        ile.setId(i);
                        i++;
                    }
                    grille.ajouterIle(new Ile(i,1,x,y));
                }else
                    result = "-";
                break;
            case "1":
                result = "2";
                grille.getIleFromPos(x,y).setNum(2);
                break;
            case "2":
                result = "3";
                grille.getIleFromPos(x,y).setNum(3);
                break;
            case "3":
                result = "4";
                grille.getIleFromPos(x,y).setNum(4);
                break;
            case "4":
                result = "5";
                grille.getIleFromPos(x,y).setNum(5);
                break;
            case "5":
                result = "6";
                grille.getIleFromPos(x,y).setNum(6);
                break;
            case "6":
                result = "7";
                grille.getIleFromPos(x,y).setNum(7);
                break;
            case "7":
                result = "8";
                grille.getIleFromPos(x,y).setNum(8);
                break;
            case "8":
                grille.removeIle(grille.getIleFromPos(x,y));
                break;
            case "=":
                result = "";
                break;
            case "-":
                result = "=";
                break;
        }
        return result;
    }

    public void exporterGrille() throws IOException {
        System.out.println("EXPORT");
        this.grille.saveGrilleToYAML();
    }
    public static void main(String args[]) throws IOException {
        JFrame jframe =  new JFrame("Application.BackEnd.LevelEditor");
        LevelEditor level = new LevelEditor();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.getContentPane().add(level);
        jframe.setSize(1200,1000);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }
}
