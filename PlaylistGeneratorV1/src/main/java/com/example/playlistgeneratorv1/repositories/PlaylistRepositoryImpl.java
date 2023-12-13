package com.example.playlistgeneratorv1.repositories;

import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.models.PlaylistFilterOptions;
import com.example.playlistgeneratorv1.models.Playlists;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.repositories.contracts.PlaylistRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PlaylistRepositoryImpl implements PlaylistRepository {
    private final SessionFactory sessionFactory;


    @Autowired
    public PlaylistRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Playlists> getAll(){
        try (Session session = sessionFactory.openSession()) {
            Query<Playlists> query = session.createQuery("from Playlists ", Playlists.class);
            List<Playlists> playlists = query.list();
            return playlists;
        }
    }

    @Override
    public List<Playlists> getAllCount() {
        try (Session session = sessionFactory.openSession()) {
            Query<Playlists> query = session.createQuery("from Playlists ", Playlists.class);
            return query.list();
        }
    }

    public List<Playlists> getUsersPlaylists(User user){
        try (Session session = sessionFactory.openSession()) {
            Query<Playlists> query = session.createQuery("FROM Playlists WHERE createdBy = :user", Playlists.class);
            query.setParameter("user", user);
            List<Playlists> playlists = query.list();
            return playlists;
        }
    }

    @Override
    public List<Playlists> getHighestRank(){
        try (Session session = sessionFactory.openSession()) {
            Query<Playlists> query = session.createQuery("FROM Playlists ORDER BY rank DESC", Playlists.class);
            query.setMaxResults(3);
            return query.list();
        }
    }

    @Override
    public List<Playlists> get(PlaylistFilterOptions playlistFilterOptions) {
        try (Session session = sessionFactory.openSession()) {
            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            playlistFilterOptions.getName().ifPresent(value -> {
                filters.add(" name like :name ");
                params.put("name", String.format("%%%s%%", value));
            });

            playlistFilterOptions.getDuration().ifPresent(value -> {
                filters.add(" duration like :duration ");
                params.put("duration", String.format("%%%s%%", value));
            });

            playlistFilterOptions.getGenre().ifPresent(value -> {
                filters.add(" genre.type like :genre ");
                params.put("genre", String.format("%%%s%%", value));
            });

            StringBuilder queryString = new StringBuilder("from Playlist ");
            if (!filters.isEmpty()) {
                queryString.append(" where ")
                        .append(String.join(" and ", filters));
            }


            queryString.append(generateOrderBy(playlistFilterOptions));
            Query<Playlists> query = session.createQuery(queryString.toString(), Playlists.class);
            query.setProperties(params);
            return query.list();
        }
    }

    private String generateOrderBy(PlaylistFilterOptions playlistFilterOptions) {
        if (playlistFilterOptions.getSortBy().isEmpty()) {
            return "";
        }
        String orderBy = "";
        switch (playlistFilterOptions.getSortBy().get()) {
            case "name":
                orderBy = "name";
                break;
            case "duration":
                orderBy = "duration";
                break;
            case "genres":
                orderBy = "genres.type";
                break;
            default:
                return "";
        }
        orderBy = String.format(" order by %s", orderBy);
        if (playlistFilterOptions.getSortOrder().isPresent()
                && playlistFilterOptions.getSortOrder().get().equalsIgnoreCase("desc")) {
            orderBy = String.format("%s desc", orderBy);
        }
        return orderBy;
    }

    @Override
    public Playlists getByPlaylistId(int id) {
        try (
                Session session = sessionFactory.openSession()
        ) {
            Playlists playlist = session.get(Playlists.class, id);
            if (playlist == null) {
                throw new EntityNotFoundException("Playlist", id);
            }
            return playlist;
        }
    }


    @Override
    public void create(Playlists playlist) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(playlist);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Playlists playlist) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(playlist);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int playlistId) {
        Playlists playlistToDelete = getByPlaylistId(playlistId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(playlistToDelete);
            session.getTransaction().commit();
        }
    }
}
