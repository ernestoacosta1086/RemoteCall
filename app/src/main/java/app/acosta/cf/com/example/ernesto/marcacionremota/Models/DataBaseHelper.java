package app.acosta.cf.com.example.ernesto.marcacionremota.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import app.acosta.cf.com.example.ernesto.marcacionremota.R;

/**
 * Created by miguelvargas on 2/22/16.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "BDMarcaciones.sqlite";
    private static final int DATABASE_VERSION = 1;

    private Dao<Marcacion, Long> marcacionDao;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        final boolean dbExist = checkDatabase(context);
        if (!dbExist) {
            try {
                copyDatabase(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkDatabase(Context context) {
        final File dbFile = context.getDatabasePath(DATABASE_NAME);
        final boolean checkDb = dbFile.exists();
        return checkDb;
    }

    private void copyDatabase(Context context) throws IOException {
        InputStream myInput = null;
        OutputStream myOutput = null;

        try {
            // Open your local db as the input stream
            myInput = context.getAssets().open(DATABASE_NAME);

            // Open the empty db as the output stream
            final File dbFile = context.getDatabasePath(DATABASE_NAME);
            final File parentFile = dbFile.getParentFile();
            parentFile.mkdirs();
            myOutput = new FileOutputStream(dbFile);

            // transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
        } finally {
            // Close the streams
            if (myOutput != null) {
                myOutput.flush();
                myOutput.close();
            }
            if (myInput != null) {
                myInput.close();
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
//        try {
//            TableUtils.createTable(connectionSource, Voucher.class);
//            TableUtils.createTable(connectionSource, Brand.class);
//            TableUtils.createTable(connectionSource, SmartShopper.class);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) {
            return;
        }
        try {
            //upgradeSomeEntity(connectionSource, oldVersion);
            //Timber.i("upgradeCategory OK");

            if (database != null) {
                database.setVersion(DATABASE_VERSION);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not update the database", e);
        }
    }

    public Dao<Marcacion, Long> getMrcacionDao() throws SQLException {
        if(marcacionDao == null) {
            marcacionDao = getDao(Marcacion.class);
        }
        return marcacionDao;
    }
}
