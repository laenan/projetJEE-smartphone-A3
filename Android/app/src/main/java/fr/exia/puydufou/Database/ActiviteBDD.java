package fr.exia.puydufou.Database;

/**
 * Created by Asvina on 19/06/2015.
 */

/**public class ActiviteBDD {
private static final int VERSION_BDD=1;

    private static final String Parcours= "Parcours";

    private static final String KEY_ID= "name";
    private static final int NUM_KEY_ID=0;

    private static final String KEY_NAME= "name";
    private static final int NUM_KEY_NAME=1;

    private static final String KEY_DUREE= "duree";
    private static final int NUM_KEY_DUREE=2;

    private static final String KEY_DESCRIPTION ="description";
    private static final int NUM_KEY_DESCRIPTION=3;

    private static final String KEY_NBACTEUR="nb_acteur";
    private static final int NUM_KEY_NBACTEUR=4;

    private static final String KEY_EVTHISTORIQUE="evt_historique";
    private static final int NUM_KEY_EVTHISTORIQUE=5;

    private SQLiteDatabase bdd;
    private SQLiteDatabaseHandler SQLiteDatabaseHandler;

    public ActiviteBDD(Context context){
        SQLiteDatabaseHandler = new SQLiteDatabaseHandler(context, android, null, VERSION_BDD)
    }
    public void open(){
        bdd = SQLiteDatabaseHandler.getWritableDatabase();

    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertActivite(Activite Activite){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(KEY_NAME, Activite.getNom_activite());
        values.put(KEY_DUREE, Activite.getDuree_activite());
        values.put(KEY_DESCRIPTION, Activite.getDescription_activite());
        values.put(KEY_NBACTEUR, Activite.getNb_acteurs());
        values.put(KEY_EVTHISTORIQUE, Activite.getEvt_historique());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(Parcours, null, values);
    }

    public int updateLivre(int id, Activite Activite){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Activite.getNom_activite());
        values.put(KEY_DUREE, Activite.getDuree_activite());
        values.put(KEY_DESCRIPTION, Activite.getDescription_activite());
        values.put(KEY_NBACTEUR, Activite.getNb_acteurs());
        values.put(KEY_EVTHISTORIQUE, Activite.getEvt_historique());

        return bdd.update(Parcours, values, KEY_ID + " = " +id, null);
    }

    public int removeLivreWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(Parcours, KEY_ID + " = " +id, null);
    }

    public Activite getActiviteWithName(String name){
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(Parcours, new String[] {KEY_ID, KEY_DESCRIPTION, KEY_DUREE,KEY_NBACTEUR, KEY_EVTHISTORIQUE}, KEY_NAME + " LIKE \"" + name +"\"", null, null, null, null);
        return cursorToLivre(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Activite cursorToLivre(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();

        //On créé un livre
        Activite livre = new Activite();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        livre.setId(c.getInt(NUM_KEY_ID));
        livre.setNom_activite(c.getString(NUM_KEY_NAME));
        livre.setDuree_activite(c.getInt(NUM_KEY_DUREE));
        livre.setDescription_activite(c.getString(NUM_KEY_DESCRIPTION));
        livre.setNb_acteurs(c.getInt(NUM_KEY_NBACTEUR));
        livre.setEvt_historique(c.getString(NUM_KEY_EVTHISTORIQUE));
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return livre;
    }
}
 */
