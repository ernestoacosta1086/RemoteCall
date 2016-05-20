package app.acosta.cf.com.example.ernesto.marcacionremota.Models;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by miguelvargas on 2/22/16.
 */

public abstract class GenericDao<T, K> {

    protected Dao<T, K> dao;
    /**
     * Creates the object if not exists.
     *
     * @throws SQLException
     */
//    T add(T object) throws SQLException;

    /**
     * Updates the object.
     *
     * @throws SQLException
     */
//    int update(T object) throws SQLException;

    /**
     * Saves or updates the object.
     */
    public T saveOrUpdate(T object) {
        try {
            dao.createOrUpdate(object);
            return object;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Saves or updates objects.
     */
    public void updateListOfObjects(final List <T> list) {
        try {
            dao.callBatchTasks(new Callable<Object>(){
                @Override
                public Object call() throws Exception {
                    for (T obj : list) {
                        try {
                            dao.createOrUpdate(obj);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Deletes the object and its dependants.
     *
     * @throws SQLException
     */
//    int delete(T object) throws SQLException;

    /**
     * Refreshes the object.
     *
     * @throws SQLException
     */
    public int refresh(T object) {
        try {
           return dao.refresh(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Returns the object with the given id.
     *
     * @throws SQLException
     */
    public T getById(K id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns all the objects.
     *
     * @throws SQLException
     */
    public List<T> getAll() {
        List<T> result = new ArrayList<>();
        try {
            result = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

//    public void updateColumnById{
//    }

    /**
     * Deletes all information in this table
     *
     * @throws SQLException
     */
    protected void clearTable(OrmLiteSqliteOpenHelper helper, Class<T> type) throws SQLException {
        TableUtils.clearTable(Daos.helper.getConnectionSource(), type);
    }

    /**
     * Deletes all information in this table, is the public method
     *
     * @throws SQLException
     */
    public abstract boolean deleteAll();
    /**
     * Deletes the object with the given id and its dependants.
     */
    public int deleteById(K id) {
        try {
            return dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
