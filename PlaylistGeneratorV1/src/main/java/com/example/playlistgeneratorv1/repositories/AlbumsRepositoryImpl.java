package com.example.playlistgeneratorv1.repositories;


import com.example.playlistgeneratorv1.exceptions.EntityLongNotFoundException;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.models.Albums;
import com.example.playlistgeneratorv1.repositories.contracts.AlbumsRepository;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumsRepositoryImpl implements AlbumsRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public AlbumsRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Albums> get() {
        try (Session session = sessionFactory.openSession()) {
            Query<Albums> query = session.createQuery("from Albums", Albums.class);
            return query.list();
        }
    }

    @Override
    public Albums get(long id) {
        try (Session session = sessionFactory.openSession()) {
            Albums album = session.get(Albums.class, id);
            if (album == null) {
                throw new EntityLongNotFoundException("Albums", id);
            }
            return album;
        }
    }

    @Override
    public Albums get(String title) {
        try (Session session = sessionFactory.openSession()) {
            Query<Albums> query = session.createQuery("from Albums where title = :title", Albums.class);
            query.setParameter("title", title);

            List<Albums> albums = query.list();
            if (albums.isEmpty()) {
                throw new EntityNotFoundException("Albums", "title", title);
            }
            return albums.get(0);
        }
    }



    @Override
    public List<Albums> getByGenre(String genre) {
        try (Session session = sessionFactory.openSession()) {
            Query<Albums> query = session.createQuery(
                    "select a from Albums a join a.genre g where g.genre = :genre", Albums.class);
            query.setParameter("genre", genre);

            List<Albums> albums = query.list();
            if (albums.isEmpty()) {
                throw new EntityNotFoundException("Albums", "genre", genre);
            }
            return albums;
        }
    }

    @Override
    public void create(Albums album) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(album);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Albums album) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(album);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Albums album = session.get(Albums.class, id);
            if (album == null) {
                throw new EntityNotFoundException("Album", id);
            }
            session.remove(album);
            session.getTransaction().commit();
        }
    }
}