package com.example.playlistgeneratorv1.repositories;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.models.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Users> get() {
        try (Session session = sessionFactory.openSession()) {
            Query<Users> query = session.createQuery("from Users", Users.class);
            return query.list();
        }
    }

    @Override
    public Users get(int id) {
        try (Session session = sessionFactory.openSession()) {
            Users users = session.get(Users.class, id);
            if (users == null) {
                throw new EntityNotFoundException("User", id);
            }
            return users;
        }
    }

    @Override
    public Users get(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Users> query = session.createQuery(" from Users where username = :name", Users.class);
            query.setParameter("name", name);

            List<Users> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("User", "username", name);
            }

            return result.get(0);
        }
    }

    @Override
    public void create(Users users) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(users);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Users users) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(users);
            session.getTransaction().commit();
        }
    }

}