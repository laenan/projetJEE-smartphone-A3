package fr.exia.puydufou.Database;

/**
 * Created by Asvina on 18/06/2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {
    private static  final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "android";
    private static final String TABLE_NAME = "activite";
    private static final String KEY_ID="id";
    private static final String KEY_NAME= "name";
    private static final String KEY_DUREE= "duree";
    private static final String KEY_DESCRIPTION ="description";
    private static final String KEY_NBACTEUR="nb_acteur";
    private static final String KEY_EVTHISTORIQUE="evt_historique";

    private static final String[] COLONNES= { KEY_ID, KEY_NAME, KEY_DUREE, KEY_DESCRIPTION, KEY_NBACTEUR, KEY_EVTHISTORIQUE};

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("SQLite DB:Constructeur","Constructeur");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATION_TABLE_EXEMPLE = "CREATE TABLE activite ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, "
                + "duree TEXT, " + "description TEXT,"+"nb_acteur INTEGER,"+"evt_historique TEXT );";

        db.execSQL(CREATION_TABLE_EXEMPLE);
        Log.i("SQLite DB", "Creation");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        this.onCreate(db);
        Log.i("SQLite DB", "Upgrade");


    }

    /**

    public Activite showOne(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLONNES, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor != null)
            cursor.moveToFirst();
        Activite Activite = new Activite();
        Activite.setId(Integer.parseInt(cursor.getString(0)));
        Activite.setName(cursor.getString(1));
        Activite.setPrice(Float.parseFloat(cursor.getString(2)));
        Activite.setQuantity(Integer.parseInt(cursor.getString(3)));
        // log
        Log.i("SQLite DB : Show one  : id=  "+id, Activite.toString());

        return Activite;
    }

    public List<Activite> showAll() {

        List<Activite> Activites = new LinkedList<Activite>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Activite Activite = null;
        if (cursor.moveToFirst()) {
            do {
                Activite = new Activite();
                Activite.setId(Integer.parseInt(cursor.getString(0)));
                Activite.setName(cursor.getString(1));
                Activite.setPrice(Float.parseFloat(cursor.getString(2)));
                Activite.setQuantity(Integer.parseInt(cursor.getString(3)));
                Activites.add(Activite);
            } while (cursor.moveToNext());
        }
        Log.i("SQLite DB : Show All  : ", Activites.toString());
        return Activites;
    }

    public void addOne(Activite Activite) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Activite.getName());
        values.put(KEY_PRICE, Activite.getPrice());
        values.put(KEY_QUANTITY, Activite.getQuantity());
        // insertion
        db.insert(TABLE_NAME, // table
                null, values);

        db.close();
        Log.i("SQLite DB : Add one  : id=  " + Activite.getId(), Activite.toString());
    }

    public int updateOne(Activite Activite) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Activite.getName());
        values.put(KEY_PRICE, Activite.getPrice());
        values.put(KEY_QUANTITY, Activite.getQuantity());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(Activite.getId()) });

        db.close();
        Log.i("SQLite DB : Update one  : id=  "+Activite.getId(), Activite.toString());

        return i;
    }
*/
}
