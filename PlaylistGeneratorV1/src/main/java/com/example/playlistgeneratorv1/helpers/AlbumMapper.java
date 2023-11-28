package com.example.playlistgeneratorv1.helpers;

import com.example.playlistgeneratorv1.models.AlbumDto;
import com.example.playlistgeneratorv1.models.Albums;
import com.example.playlistgeneratorv1.services.contracts.AlbumsService;
import com.example.playlistgeneratorv1.services.contracts.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper {

    private final AlbumsService albumsService;

    private final GenresService genresService;

    @Autowired

    public AlbumMapper(AlbumsService albumsService, GenresService genresService) {
        this.albumsService = albumsService;
        this.genresService = genresService;
    }

    public Albums fromDto(int id, AlbumDto dto) {
        Albums albums = fromDto(dto);
        albums.setId(id);
        Albums repositoryAlbums = albumsService.get(id);
        return albums;
    }

    public Albums fromDto(AlbumDto dto) {
        Albums albums = new Albums();
        albums.setTitle(dto.getTitle());
        albums.setTrackList(dto.getTrackList());
        albums.setGenre(genresService.get(dto.getGenresId()));
        return albums;
    }

    public AlbumDto toDto(Albums albums) {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setTitle(albums.getTitle());
        albumDto.setTrackList(albums.getTitle());
        albumDto.setGenresId(albums.getGenre().getId());
        return albumDto;
    }
}
