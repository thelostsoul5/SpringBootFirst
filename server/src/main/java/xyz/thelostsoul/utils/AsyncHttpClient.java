package xyz.thelostsoul.utils;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.Future;

import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class AsyncHttpClient {

    private URL url;

    private HttpHeaders httpHeaders = new DefaultHttpHeaders();

    private EventLoopGroup worker;

    private Bootstrap boot;

    private static ChannelPoolMap<String, FixedChannelPool> poolMap;

    private boolean havingBoot = false;

    public AsyncHttpClient(String url, Map<String, String> param) throws Exception {
        String paramString = null;
        if (param != null && param.size() > 0) {
            StringBuilder finalParamString = new StringBuilder();
            param.forEach((k, v) -> finalParamString.append(k).append("=").append(v).append("&"));
            finalParamString.deleteCharAt(finalParamString.length() - 1);
            paramString = finalParamString.toString();
        }

        String urlWithParam = (paramString == null) ? url : url + "?" + paramString;

        this.url = new URL(urlWithParam);

        this.worker = new NioEventLoopGroup();

        this.boot = new Bootstrap().group(worker);
    }

    public void setHeader(Map<String, String> headers) {
        if (headers == null) {
            return;
        }

        headers.forEach((k, v) -> this.httpHeaders.set(k, v));
    }

    public void initBoot(String host, int port) throws Exception {
        if (!havingBoot) {
            boot.channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .option(ChannelOption.SO_KEEPALIVE, false)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
            havingBoot = true;
        }
    }

    public void asyncGet(DefaultResponseHandler responseHandler) throws Exception {
        String host = url.getHost();
        int port = url.getPort();
        
        if (null == poolMap) {
            initBoot(host, port);
            initPoolMap(boot, responseHandler);
        }

        final FixedChannelPool pool = poolMap.get(host + ":" + port + "|" + responseHandler.getClass());
        Future<Channel> future = pool.acquire();
        future.addListener(f -> {
            Channel channel = (Channel) f.getNow();
            HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, url.toString());
            request.headers().add(httpHeaders);

            ChannelFuture lastWriteFuture = channel.writeAndFlush(request);
            lastWriteFuture.sync();
            pool.release(channel);
        });
    }

    public Future<?> shutdown() {
        return worker.shutdownGracefully();
    }

    private void initPoolMap(Bootstrap boot, DefaultResponseHandler responseHandler) throws Exception {
        poolMap = new AbstractChannelPoolMap<String, FixedChannelPool>() {
            @Override
            protected FixedChannelPool newPool(String s) {
                ChannelPoolHandler handler = new ChannelPoolHandler() {
                    /**
                     * 使用完channel需要释放才能放入连接池
                     */
                    @Override
                    public void channelReleased(Channel ch) throws Exception {
                        // 刷新管道里的数据
                        // ch.writeAndFlush(Unpooled.EMPTY_BUFFER); // flush掉所有写回的数据
                    }

                    /**
                     * 当链接创建的时候添加channelHandler，只有当channel不足时会创建，但不会超过限制的最大channel数
                     */
                    @Override
                    public void channelCreated(Channel ch) throws Exception {
                        // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                        ch.pipeline().addLast(new HttpResponseDecoder());
                        // 客户端发送的是httpRequest，所以要使用HttpRequestEncoder进行编码
                        ch.pipeline().addLast(new HttpRequestEncoder());
                        ch.pipeline().addLast(responseHandler);
                    }

                    /**
                     *  获取连接池中的channel
                     */
                    @Override
                    public void channelAcquired(Channel ch) throws Exception {
                    }
                };

                return new FixedChannelPool(boot, handler, 50); //单个host连接池大小
            }
        };
    }

    @ChannelHandler.Sharable
    abstract static class DefaultResponseHandler extends ChannelInboundHandlerAdapter {
        public DefaultResponseHandler() {}

        public abstract void init();
    }

    static class OutStreamResponseHandler extends DefaultResponseHandler {

        private OutputStream os;

        public OutStreamResponseHandler(OutputStream os) {
            this.os = os;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof HttpResponse)
            {
                HttpResponse response = (HttpResponse) msg;
                System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaderNames.CONTENT_TYPE));

            }
            if(msg instanceof HttpContent)
            {
                HttpContent content = (HttpContent)msg;
                ByteBuf buf = content.content();

                os.write(ByteBufUtil.getBytes(buf));
                os.flush();
                buf.release();
            }
        }

        @Override
        public void init() {

        }
    }
    
    static class Wait4ResponseHandler extends DefaultResponseHandler {
        private CountDownLatch count = new CountDownLatch(1);
        private int contentLength = 0;
        private ByteBuf response = Unpooled.buffer();

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            if (msg instanceof HttpResponse)
            {
                HttpResponse response = (HttpResponse) msg;
                System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaderNames.CONTENT_TYPE));
                contentLength = response.headers().getInt(HttpHeaderNames.CONTENT_LENGTH);
                if (count.getCount() == 0) {
                    count = new CountDownLatch(1);
                }
            }
            if(msg instanceof HttpContent)
            {
                HttpContent content = (HttpContent)msg;
                ByteBuf buf = content.content();

                response.writeBytes(buf);
                buf.release();
                if (response.readableBytes() >= contentLength) {
                    count.countDown();
                    ctx.close();
                }
            }
        }

        public byte[] getContent() {
            try {
                count.await();
            } catch (Exception e) {

            }
            return ByteBufUtil.getBytes(response);
        }

        @Override
        public void init() {
            count = new CountDownLatch(1);
            contentLength = 0;
            response.release();
            response = Unpooled.buffer();
        }
    }
}
