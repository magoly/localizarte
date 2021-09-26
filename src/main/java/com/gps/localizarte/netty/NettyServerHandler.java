package com.gps.localizarte.netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gps.localizarte.data.model.Coordinate;
import com.gps.localizarte.data.repository.CoordinateRepository;
import com.gps.localizarte.util.Convertor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

	@Autowired
	private Convertor convertor;
	@Autowired
	private CoordinateRepository coordinateRepository;

	/**
	 * 客户端连接会触发
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("Channel active......");
	}

	/**
	 * 客户端发消息会触发
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        log.info("服务器收到消息: {}", msg.toString());

		try {
			Coordinate coordinate = convertor.analyze(msg.toString());
	    	coordinateRepository.save(coordinate);
			ctx.write(coordinate.getResponse());
		} catch (Exception e) {
			log.error(e);
			ctx.write("LOAD");
		}
    	
		ctx.flush();
	}

	/**
	 * 发生异常触发
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
