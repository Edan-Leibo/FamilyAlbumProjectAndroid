package com.example.adima.familyalbumproject.Album.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by adima on 03/03/2018.
 */

public class AlbumListViewModel extends ViewModel {
    private LiveData<List<Album>> albums;

    public AlbumListViewModel() {
        albums = AlbumRepository.instance.getAllAlbums();
    }

    public LiveData<List<Album>> getAlbumssList() {
        return albums;
    }
}