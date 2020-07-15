package org.wcci.apimastery.controllers;

import org.springframework.web.bind.annotation.*;
import org.wcci.apimastery.entities.*;
import org.wcci.apimastery.storage.AlbumStorage;
import org.wcci.apimastery.storage.ArtistStorage;
import org.wcci.apimastery.storage.CommentStorage;
import org.wcci.apimastery.storage.SongStorage;

import java.util.Collection;

@RestController
public class AlbumController {
    AlbumStorage albumStorage;
    SongStorage songStorage;
    CommentStorage commentStorage;

    public AlbumController(AlbumStorage albumStorage, SongStorage songStorage, CommentStorage commentStorage) {
        this.albumStorage = albumStorage;
        this.songStorage = songStorage;
        this.commentStorage = commentStorage;
    }

    @GetMapping("/api/albums/")
    public Collection<Album> retrieveAllAlbums() {
        return albumStorage.retrieveAllAlbums();
    }

    @GetMapping("/api/albums/{id}")
    public Album retrieveAlbumById(@PathVariable long id){
        return albumStorage.retrieveAlbumById(id);
    }

//    @PostMapping("/api/albums/add/")
//    public Album addAlbum(@RequestBody Album album){
//        return albumStorage.save(album);
//    }

       @PatchMapping("/api/albums/{id}/Song/")
    public Album addSongToAlbum(@PathVariable long id, @RequestBody Song song){
        Album album = albumStorage.retrieveAlbumById(id);
        Song songToAdd = new Song(song.getSongName(), song.getDuration(), album, song.getImageUrl());
        songStorage.save(songToAdd);

        return songToAdd.getAlbum();
    }

    @PatchMapping("/api/albums/{id}/Comment/")
    public Album addCommentToAlbum(@PathVariable long id, @RequestBody AlbumComment comment) {
        Album album = albumStorage.retrieveAlbumById(id);
        AlbumComment commentToAdd = new AlbumComment(comment.getText(), comment.getAuthorName(), album);
        commentStorage.addComment(commentToAdd);
        return commentToAdd.getAlbum();
    }

    @DeleteMapping("/api/albums/delete/{id}")
    public Collection<Album> deleteAlbum(@PathVariable long id) {
        albumStorage.deleteAlbumById(id);
        return albumStorage.retrieveAllAlbums();
    }
}
