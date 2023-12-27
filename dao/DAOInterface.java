package moviebookingapp.dao;

import java.util.ArrayList;

public interface DAOInterface<S> {
    ArrayList<S> list();
    boolean create(S s);
    boolean update(S s);
    boolean delete(int id);
    S findOne(int id);
}
