package cn.ltysyn.infiniti.music.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.ltysyn.infiniti.music.entity.Album;

public interface IAlbumDao extends JpaRepository<Album, Long> {

}