package app.acosta.cf.com.example.ernesto.marcacionremota.Models;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by miguelvargas on 2/22/16.
 */
public class MarcacionDao extends GenericDao<Marcacion, Long>{

    public MarcacionDao(Dao<Marcacion, Long> dao) {
        this.dao = dao;
    }

    @Override
    public boolean deleteAll() {
        try {
            clearTable(Daos.helper, Marcacion.class);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateListOfObjects(List<Marcacion> list) {
        super.updateListOfObjects(list);
    }

    public long count() {
        try {
            return dao.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
