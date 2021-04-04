package io.github.kimmking.gateway.outbound.netty4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.nio.charset.StandardCharsets;

public class NettyHttpClientOutboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        URI url = new URI("/test");
        String meg = "hello";

        //配置HttpRequest的请求数据和一些配置信息
        FullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_0, HttpMethod.GET, url.toASCIIString(), Unpooled.wrappedBuffer(meg.getBytes(StandardCharsets.UTF_8)));

        request.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8")
                //开启长连接
                .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE)
                //设置传递请求内容的长度
                .set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

        //发送数据
        ctx.writeAndFlush(request);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        try {

            if (msg instanceof DefaultHttpResponse) {
                HttpResponse response = (DefaultHttpResponse) msg;
                HttpHeaders headers = response.headers();
                System.out.println("headers:" + System.getProperty("line.separator") + headers.toString());
                return;
            }

            LastHttpContent response = (DefaultLastHttpContent) msg;

            ByteBuf content = response.content();

            System.out.println("content:" + System.getProperty("line.separator") + content.toString(CharsetUtil.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}