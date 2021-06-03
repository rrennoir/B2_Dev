package Examen.accessBD;

import java.sql.*;
import java.util.*;

public class AccessBDGen {
     /*
      * Crée une connection à une base de données à partir du nom logique de la BD,
      * du nom de l'utilisateur et du mot de passe.
      */
     public static Connection connecter(String bd, String user, String pass) throws SQLException {
          Connection connexion = null;
          try {
               try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    
               } catch (ClassNotFoundException e) {
                    Class.forName("com.mysql.jdbc.Driver");
               }
               DriverManager.setLoginTimeout(5);
               connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + bd, user, pass);
          } catch (ClassNotFoundException ex) {
               ex.printStackTrace();
          }
          return connexion;
     }

     /*
      * Ex�cute une requ�te d'acc�s en lecture � la BD (select) et r�cup�re le mod�le
      * des donn�es r�sultant de la requ�te (ensemble de lignes et de colonnes)
      */
     public static TableModelGen creerTableModel(PreparedStatement prepStat) throws SQLException {
          ResultSet donnees = prepStat.executeQuery();
          ArrayList<String> nomColonnes = creerNomColonnes(donnees);
          ArrayList<Object> lignes = creerLignes(donnees);
          ArrayList<Object> objetTypes = creerObjetTypes(donnees);
          TableModelGen model = new TableModelGen(nomColonnes, lignes, objetTypes);
          return model;
     }

     // M�thode appel�e par la m�thode creerTableModel pour r�cup�rer les noms des
     // colonnes
     private static ArrayList<String> creerNomColonnes(ResultSet donnees) throws SQLException {
          ResultSetMetaData meta = donnees.getMetaData();
          ArrayList<String> nomColonnes = new ArrayList<String>();

          for (int i = 1; i <= meta.getColumnCount(); i++) {
               nomColonnes.add(meta.getColumnName(i));
          }

          return nomColonnes;
     }

     // M�thode appel�e par la m�thode creerTableModel pour r�cup�rer les lignes des
     // donn�es
     private static ArrayList<Object> creerLignes(ResultSet donnees) throws SQLException {
          ResultSetMetaData meta = donnees.getMetaData();
          ArrayList<Object> lignes = new ArrayList<Object>();

          while (donnees.next()) {
               lignes.add(ligneSuivante(donnees, meta));
          }

          return lignes;
     }

     // M�thode appel�e par la m�thode creerLignes pour r�cup�rer une ligne de
     // donn�es
     private static ArrayList<Object> ligneSuivante(ResultSet donnees, ResultSetMetaData meta) throws SQLException {
          ArrayList<Object> ligneCourante = new ArrayList<Object>();
          String stringLu;
          int entierLu;
          double doubleLu;
          float floatLu;
          boolean booleenLu;
          java.util.Date dateLue;

          for (int i = 1; i <= meta.getColumnCount(); i++) {
               switch (meta.getColumnType(i)) {
                    case Types.VARCHAR:
                         stringLu = donnees.getString(i);
                         ligneCourante.add(donnees.wasNull() ? null : stringLu);
                         break;
                    case Types.CHAR:
                         stringLu = donnees.getString(i);
                         ligneCourante.add(donnees.wasNull() ? null : stringLu);
                         break;
                    case Types.INTEGER:
                         entierLu = donnees.getInt(i);
                         ligneCourante.add(donnees.wasNull() ? null : entierLu);
                         break;
                    case Types.SMALLINT:
                         entierLu = donnees.getInt(i);
                         ligneCourante.add(donnees.wasNull() ? null : entierLu);
                         break;
                    case Types.TINYINT:
                         entierLu = donnees.getInt(i);
                         ligneCourante.add(donnees.wasNull() ? null : entierLu);
                         break;
                    case Types.REAL:
                         floatLu = donnees.getFloat(i);
                         ligneCourante.add(donnees.wasNull() ? null : floatLu);
                         break;
                    case Types.DOUBLE:
                         doubleLu = donnees.getDouble(i);
                         ligneCourante.add(donnees.wasNull() ? null : doubleLu);
                         break;
                    case Types.DATE:
                    case Types.TIMESTAMP:
                         dateLue = donnees.getDate(i);
                         ligneCourante.add(donnees.wasNull() ? null : dateLue);
                         break;
                    case Types.BIT:
                         booleenLu = donnees.getBoolean(i);
                         ligneCourante.add(donnees.wasNull() ? null : booleenLu);
                         break;
               }
          }
          return ligneCourante;
     }

     // M�thode appel�e par la m�thode creerTableModel pour r�cup�rer les types des
     // colonnes
     private static ArrayList<Object> creerObjetTypes(ResultSet donnees) throws SQLException {
          ResultSetMetaData meta = donnees.getMetaData();
          ArrayList<Object> objetTypes = new ArrayList<Object>();
          String stringLu = "bidon";
          int entierLu = 1;
          double doubleLu = 1.0;
          float floatLu = 1.0f;
          boolean booleenLu = true;
          java.util.Date dateLue = new java.util.Date();

          for (int i = 1; i <= meta.getColumnCount(); i++) {
               switch (meta.getColumnType(i)) {
                    case Types.VARCHAR:
                         objetTypes.add(stringLu);
                         break;
                    case Types.CHAR:
                         objetTypes.add(stringLu);
                         break;
                    case Types.INTEGER:
                         objetTypes.add(entierLu);
                         break;
                    case Types.SMALLINT:
                         objetTypes.add(entierLu);
                         break;
                    case Types.TINYINT:
                         objetTypes.add(entierLu);
                         break;
                    case Types.REAL:
                         objetTypes.add(floatLu);
                         break;
                    case Types.DOUBLE:
                         objetTypes.add(doubleLu);
                         break;
                    case Types.DATE:
                    case Types.TIMESTAMP:
                         objetTypes.add(dateLue);
                         break;
                    case Types.BIT:
                         objetTypes.add(booleenLu);
                         break;
               }
          }
          return objetTypes;
     }

     /*
      * R�cup�re sous forme d'un liste les donn�es correspondant � une colonne dans
      * une table de la BD. La requete doit �tre un select portant sur une seule
      * colonne
      */
     public static Object[] creerListe1Colonne(PreparedStatement prepStat) throws SQLException {
          int max;
          Object[] uneColonne;
          int index = 0;

          String stringLu;
          int entierLu;
          float floatLu;
          double doubleLu;
          boolean booleenLu;
          java.util.Date dateLue;

          ResultSet donnees2 = prepStat.executeQuery();
          max = 0;
          while (donnees2.next()) {
               max++;
          }

          uneColonne = new Object[max];

          ResultSet donnees = prepStat.executeQuery();
          ResultSetMetaData meta = donnees.getMetaData();

          while (donnees.next()) {
               switch (meta.getColumnType(1)) {
                    case Types.VARCHAR:
                         stringLu = donnees.getString(1);
                         if (donnees.wasNull() == false) {
                              uneColonne[index] = stringLu;
                              index++;
                         }
                         break;
                    case Types.CHAR:
                         stringLu = donnees.getString(1);
                         if (donnees.wasNull() == false) {
                              uneColonne[index] = stringLu;
                              index++;
                         }
                         break;
                    case Types.INTEGER:
                         entierLu = donnees.getInt(1);
                         if (donnees.wasNull() == false) {
                              uneColonne[index] = entierLu;
                              index++;
                         }
                         break;
                    case Types.SMALLINT:
                         entierLu = donnees.getInt(1);
                         if (donnees.wasNull() == false) {
                              uneColonne[index] = entierLu;
                              index++;
                         }
                         break;
                    case Types.TINYINT:
                         entierLu = donnees.getInt(1);
                         if (donnees.wasNull() == false) {
                              uneColonne[index] = entierLu;
                              index++;
                         }
                         break;
                    case Types.REAL:
                         floatLu = donnees.getFloat(1);
                         if (donnees.wasNull() == false) {
                              uneColonne[index] = floatLu;
                              index++;
                         }
                         break;
                    case Types.DOUBLE:
                         doubleLu = donnees.getDouble(1);
                         if (donnees.wasNull() == false) {
                              uneColonne[index] = doubleLu;
                              index++;
                         }
                         break;
                    case Types.TIMESTAMP:
                         dateLue = donnees.getDate(1);
                         if (donnees.wasNull() == false) {
                              uneColonne[index] = dateLue;
                              index++;
                         }
                         break;
                    case Types.BIT:
                         booleenLu = donnees.getBoolean(1);
                         if (donnees.wasNull() == false) {
                              uneColonne[index] = booleenLu;
                              index++;
                         }
                         break;
               }
          }
          return uneColonne;
     }

}
