package sio.gestionsubventions;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sio.gestionsubventions.Model.Structure;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Map;

public class SubventionsController implements Initializable
{
    HashMap<String,HashMap<String, TreeMap<Integer,ArrayList<Structure>>>> lesSubventions;
    @FXML
    private AnchorPane apAffecter;
    @FXML
    private ListView lvVilles;
    @FXML
    private AnchorPane apStatistiques;
    @FXML
    private ListView lvSecteurs;
    @FXML
    private ComboBox cboAnnees;
    @FXML
    private TextField txtNomStructure;
    @FXML
    private TextField txtMontant;
    @FXML
    private Button btnAffecterSubvention;
    @FXML
    private Button btnMenuAffecter;
    @FXML
    private Button btnMenuStatistiques;
    @FXML
    private ListView lvVillesStats;
    @FXML
    private TreeView tvMontantsParSecteurs;
    @FXML
    private TreeView tvMontantsParAnnees;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        apAffecter.toFront();
        lesSubventions = new HashMap<>();
        lvVilles.getItems().addAll("Bordeaux","Nantes","Paris");
        lvSecteurs.getItems().addAll("Culture","Education","Santé","Sport");
        cboAnnees.getItems().addAll(2020,2021,2022,2023,2024,2025);
        cboAnnees.getSelectionModel().selectFirst();



    }

    @FXML
    public void btnMenuClicked(Event event)
    {
        if(event.getSource()==btnMenuAffecter)
        {
            apAffecter.toFront();
        }
        else if(event.getSource()==btnMenuStatistiques)
        {
            apStatistiques.toFront();
        }
    }

    @FXML
    public void btnAffecterSubventionClicked(Event event) {
        if (lvVilles.getSelectionModel().getSelectedItem() == null)
        {
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Choix de la ville");
                alert.setHeaderText("");
                alert.setContentText("Sélectionner une ville");
                alert.showAndWait();
            }showAlert("Choix de la ville", "Sélectionner une ville");
        }

        else if (lvSecteurs.getSelectionModel().getSelectedItem() == null)
        {
            showAlert("Choix du secteur", "Sélectionner un secteur");
        }

        else if (txtNomStructure.getText().isEmpty())
        {
            showAlert("Choix de structure", "Sélectionner une structure");
        }

        else if (txtMontant.getText().isEmpty())
        {
            showAlert("Choix du montant", "Sélectionner un montant");
        }

        else {
            String ville = lvVilles.getSelectionModel().getSelectedItem().toString();
            String secteur = lvSecteurs.getSelectionModel().getSelectedItem().toString();
            int annee = (int) cboAnnees.getSelectionModel().getSelectedItem();
            double montant = Double.parseDouble(txtMontant.getText());

            Structure laStructure = new Structure(txtNomStructure.getText(), (int) montant);

            ajouterSubvention(lesSubventions, ville, secteur, annee, laStructure);

            // Afficher la page "Affectation réussie"
            showAlert("Messagerie", "Message envoyé");

            // Effacer les champs après l'ajout
            lvVilles.getSelectionModel().clearSelection();
            lvSecteurs.getSelectionModel().clearSelection();
            txtNomStructure.clear();
            txtMontant.clear();
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void ajouterSubvention(HashMap<String, HashMap<String, TreeMap<Integer, ArrayList<Structure>>>> subventions,
                String ville, String secteur, int annee, Structure structure) {
            subventions.computeIfAbsent(ville, k -> new HashMap<>())
                    .computeIfAbsent(secteur, k -> new TreeMap<>())
                    .computeIfAbsent(annee, k -> new ArrayList<>())
                    .add(structure);
        }
           // Structure laStructure = new Structure(txtNomStructure.getText(), cboAnnees.getSelectionModel().getSelectedItem().hashCode());







        @FXML
        public void lvVillesStatsClicked(Event event) {

            // Initialiser les HashMap pour stocker les montants par secteur et par année
            HashMap<String, Double> montantsParSecteur = new HashMap<>();
            HashMap<Integer, Double> montantsParAnnee = new HashMap<>();

            // Parcourir les subventions pour la ville sélectionnée
            for (HashMap<String, TreeMap<Integer, ArrayList<Structure>>> subventionsParSecteur : lesSubventions.get(villeSelectionnee).values()) {
                for (TreeMap<Integer, ArrayList<Structure>> subventionsParAnnee : subventionsParSecteur.values()) {
                    for (ArrayList<Structure> structures : subventionsParAnnee.values()) {
                        for (Structure structure : structures) {
                            // Mettre à jour le montant par secteur
                            montantsParSecteur.merge(subventionsParSecteur.firstKey(), structure.getMontant(), Double::sum);

                            // Mettre à jour le montant par année
                            montantsParAnnee.merge(subventionsParAnnee.firstKey(), structure.getMontant(), Double::sum);
                        }
                    }
                }
            }

            // Afficher les montants par secteur
            System.out.println("Montants par secteur pour " + villeSelectionnee + ":");
            for (Map.Entry<String, Double> entry : montantsParSecteur.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            // Afficher les montants par année
            System.out.println("Montants par année pour " + villeSelectionnee + ":");
            for (Map.Entry<Integer, Double> entry : montantsParAnnee.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }





    // Jeu d'essais au cas où :)
//        Structure structure1 = new Structure("Structure 1",1000);
//        Structure structure2 = new Structure("Structure 2",2000);
//        Structure structure3 = new Structure("Structure 3",3000);
//        Structure structure4 = new Structure("Structure 4",4000);
//        Structure structure5 = new Structure("Structure 5",5000);
//        Structure structure6 = new Structure("Structure 6",6000);
//        Structure structure7 = new Structure("Structure 7",7000);
//        Structure structure8 = new Structure("Structure 8",8000);
//        Structure structure9 = new Structure("Structure 9",9000);
//
//        ArrayList<Structure> lesStructuresDeBordeaux = new ArrayList<>();
//        lesStructuresDeBordeaux.add(structure1);
//        lesStructuresDeBordeaux.add(structure2);
//        lesStructuresDeBordeaux.add(structure3);
//
//        ArrayList<Structure> lesStructuresDeNantes = new ArrayList<>();
//        lesStructuresDeNantes.add(structure4);
//        lesStructuresDeNantes.add(structure5);
//        lesStructuresDeNantes.add(structure6);
//
//        ArrayList<Structure> lesStructuresDeParis = new ArrayList<>();
//        lesStructuresDeParis.add(structure7);
//        lesStructuresDeParis.add(structure8);
//        lesStructuresDeParis.add(structure9);
//
//        TreeMap<Integer,ArrayList<Structure>> lesAnneesDeBordeaux = new TreeMap<>();
//        lesAnneesDeBordeaux.put(2020, lesStructuresDeBordeaux);
//        lesAnneesDeBordeaux.put(2021, lesStructuresDeBordeaux);
//        lesAnneesDeBordeaux.put(2022, lesStructuresDeBordeaux);
//
//        TreeMap<Integer,ArrayList<Structure>> lesAnneesDeNantes = new TreeMap<>();
//        lesAnneesDeNantes.put(2020, lesStructuresDeNantes);
//        lesAnneesDeNantes.put(2021, lesStructuresDeNantes);
//        lesAnneesDeNantes.put(2022, lesStructuresDeNantes);
//        lesAnneesDeNantes.put(2023, lesStructuresDeNantes);
//
//        TreeMap<Integer,ArrayList<Structure>> lesAnneesDeParis = new TreeMap<>();
//        lesAnneesDeParis.put(2022, lesStructuresDeParis);
//        lesAnneesDeParis.put(2023, lesStructuresDeParis);
//        lesAnneesDeParis.put(2024, lesStructuresDeParis);
//
//        HashMap<String,TreeMap<Integer,ArrayList<Structure>>> lesSecteursDeBordeaux = new HashMap<>();
//        lesSecteursDeBordeaux.put("Santé", lesAnneesDeBordeaux);
//        lesSecteursDeBordeaux.put("Sport", lesAnneesDeBordeaux);
//
//        HashMap<String,TreeMap<Integer,ArrayList<Structure>>> lesSecteursDeNantes = new HashMap<>();
//        lesSecteursDeNantes.put("Education", lesAnneesDeNantes);
//        lesSecteursDeNantes.put("Culture", lesAnneesDeNantes);
//
//        HashMap<String,TreeMap<Integer,ArrayList<Structure>>> lesSecteursDeParis = new HashMap<>();
//        lesSecteursDeParis.put("Culture", lesAnneesDeParis);
//
//        lesSubventions.put("Bordeaux",lesSecteursDeBordeaux);
//        lesSubventions.put("Nantes",lesSecteursDeNantes);
//        lesSubventions.put("Paris",lesSecteursDeParis);
}