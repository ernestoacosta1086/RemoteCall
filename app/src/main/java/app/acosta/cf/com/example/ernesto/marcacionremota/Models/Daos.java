package app.acosta.cf.com.example.ernesto.marcacionremota.Models;

import android.content.Context;

import java.sql.SQLException;

/**
 * Created by miguelvargas on 2/22/16.
 */
public class Daos {

    static public DataBaseHelper helper;

    static public MarcacionDao marcacionDao;

    static public void init(Context context) {
        helper = new DataBaseHelper(context);
        try {
            marcacionDao = new MarcacionDao(helper.getMrcacionDao());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
