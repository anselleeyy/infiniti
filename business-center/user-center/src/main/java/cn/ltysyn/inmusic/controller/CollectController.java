package cn.ltysyn.inmusic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping(value = "/collect")
@Api(tags = "用户收藏接口类")
public class CollectController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CollectController.class);
	
	@GetMapping(value = "/{userId}")
	@ApiOperation(value = "查询用户收藏列表")
	public Object getAllCollects() {
		return "";
	}

}
