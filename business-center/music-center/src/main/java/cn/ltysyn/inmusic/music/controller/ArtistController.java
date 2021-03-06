package cn.ltysyn.inmusic.music.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;

import cn.ltysyn.inmusic.commons.Response;
import cn.ltysyn.inmusic.commons.ReturnCode;
import cn.ltysyn.inmusic.music.entity.Album;
import cn.ltysyn.inmusic.music.entity.Artist;
import cn.ltysyn.inmusic.music.utils.FileUpload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/artists")
@Api(value = "歌手控制器")
public class ArtistController extends BaseController {
	
	private static AtomicInteger songId = new AtomicInteger(10000000);
	
	@GetMapping
	@ApiOperation(value = "查询所有歌手列表")
	public Object getAllArtists() {
		List<Artist> artists = artistService.getAllArtists();
		Response response = new Response(ReturnCode.ARTIST_LIST_GOT, artists);
		return response;
	}
	
	@GetMapping(value = "/{artistId}")
	@ApiOperation(value = "查询单个歌手信息", notes = "根据歌手 id 查询歌手信息")
	public Object getArtistById(@PathVariable Integer artistId) {
		Artist artist = artistService.getByArtistId(artistId);
		Response response = new Response(ReturnCode.ARTIST_INFO_GOT, artist);
		return response;
	}
	
	@GetMapping(value = "/{artistId}/albums")
	@ApiOperation(value = "查询该歌手下的专辑信息", notes = "根据歌手 id 查询该歌手下的专辑信息")
	public Object getAlbumsByArtistId(@PathVariable Integer artistId) {
		List<Album> albums = albumService.getByArtistId(artistId);
		Response response = new Response(ReturnCode.ALBUMS_IN_ARTIST_GOT, albums);
		return response;
	}
	
	@GetMapping(value = "/search")
	public Object searchArtist(@RequestParam String keyword) {
		List<Artist> artists = artistService.searchArtist("%" + keyword + "%");
		Response response = new Response(ReturnCode.ARTIST_LIST_GOT, artists);
		return response;
	}
	
	/**
	 * 分页查询接口
	 * @return
	 */
	@GetMapping(value = "/page")
	public Object getByPage(@RequestParam int page, @RequestParam int size) {
		Page<Artist> artists = artistService.getByPage(page, size);
		Response response = new Response(ReturnCode.ARTIST_LIST_GOT, artists);
		return response;
	}
	
	@PostMapping(value = "/upload")
	public Object uploadPic(@RequestParam MultipartFile file) {
		Map<String, Object> map = Maps.newHashMap();
		try {
			int id = songId.getAndAdd(1);
			String picUrl = FileUpload.savePic(id, file);
			map.put("artistId", id);
			map.put("picUrl", picUrl);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}
	
	@PostMapping(value = "/add")
	public Object add(@RequestBody Artist artist) {
		Response response = null;
		boolean flag = artistService.addArtist(artist);
		if (flag) {
			response = new Response(ReturnCode.ARTIST_ADD_SUCCEED);
		} else {
			response = new Response(ReturnCode.ARTIST_ADD_FAILED);
		}
		return response;
	}
	
	@DeleteMapping(value = "/delete/{artistId}")
	public Object deleteSong(@PathVariable int artistId) {
		Response response = null;
		boolean flag = artistService.delArtist(artistId);
		if (flag) {
			response = new Response(ReturnCode.ARTIST_DELETE_SUCCEED);
		} else {
			response = new Response(ReturnCode.ARTIST_DELETE_FAILED);
		}
		return response;
	}

}
