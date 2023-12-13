package com.example.playlistgeneratorv1.helpers;

import com.example.playlistgeneratorv1.models.*;
import com.example.playlistgeneratorv1.exceptions.*;

public class CheckPermissions {

    public static final String AUTH_ERR_MESSAGE = "You are not authorized to perform this operation";


    public static void checkIfSameUser(User user, User userToUpdate) {
        if (!(user.getId() == userToUpdate.getId())) {
            throw new UnauthorizedOperationException(AUTH_ERR_MESSAGE);
        }
    }

    public static void checkUserAuthorization(int targetUserId, User executingUser) {
        if (!executingUser.isAdmin() && executingUser.getId() != targetUserId) {
            throw new UnauthorizedOperationException(AUTH_ERR_MESSAGE);
        }
    }
    public static void checkIfSameUserOrAdmin(User user, Playlists playlist) {
        if (!(user.equals(playlist.getCreatedBy()) || user.isAdmin())) {
            throw new UnauthorizedOperationException(AUTH_ERR_MESSAGE);
        }
    }

    public static void checkIfSameUserOrAdmin(int userId, User user) {
        if (!(userId == user.getId() || user.isAdmin())) {
            throw new UnauthorizedOperationException(AUTH_ERR_MESSAGE);
        }
    }
    public static void checkIfAdmin(User user) {
        if (!user.isAdmin()) {
            throw new UnauthorizedOperationException(AUTH_ERR_MESSAGE);
        }
    }
}
