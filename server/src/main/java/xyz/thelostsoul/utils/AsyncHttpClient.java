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

    private static ChannelPoolMap<URL, FixedChannelPool> poolMap;

    private static int maxConnections = 50;

    public AsyncHttpClient(String url, Map<String, String> param, AbstractResponseHandler responseHandler) throws Exception {
        AsyncHttpClient(url, param, responseHandler, 0);
    }

    public AsyncHttpClient(String url, Map<String, String> param, AbstractResponseHandler responseHandler, int maxConnections) throws Exception {
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

        Bootstrap boot = new Bootstrap().group(worker).channel(NioSocketChannel.class);

        initPoolMap(boot, responseHandler);

        if (0 < maxConnections) {
            this.maxConnections = maxConnections;
        }
    }

    public void setHeader(Map<String, String> headers) {
        if (headers == null) {
            return;
        }

        headers.forEach((k, v) -> this.httpHeaders.set(k, v));
    }

    public void get() throws Exception {

        final FixedChannelPool pool = poolMap.get(this.url);
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

    public void post(byte[] body) throws Exception {

        final FixedChannelPool pool = poolMap.get(this.url);
        Future<Channel> future = pool.acquire();
        future.addListener(f -> {
            Channel channel = (Channel) f.getNow();
            HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, url.toString());
            request.headers().add(httpHeaders);

            ChannelFuture lastWriteFuture = channel.writeAndFlush(request);
            if (null != body) {
                //请求体写入
                HttpContent content = new DefaultLastHttpContent();
                content.content().writeBytes(body);
                lastWriteFuture = channel.writeAndFlush(content);
            }
            lastWriteFuture.sync();
            pool.release(channel);
        });
    }

    public Future<?> shutdown() {
        return worker.shutdownGracefully();
    }

    private void initPoolMap(Bootstrap boot, AbstractResponseHandler responseHandler) {
        if (null != poolMap) {
            return;
        }
        poolMap = new AbstractChannelPoolMap<URL, FixedChannelPool>() {
            @Override
            protected FixedChannelPool newPool(URL url) {
                ChannelPoolHandler handler = new ChannelPoolHandler() {
                    /**
                     * 使用完channel需要释放才能放入连接池
                     */
                    @Override
                    public void channelReleased(Channel ch) {
                        // 刷新管道里的数据
                        // ch.writeAndFlush(Unpooled.EMPTY_BUFFER); // flush掉所有写回的数据
                    }

                    /**
                     * 当链接创建的时候添加channelHandler，只有当channel不足时会创建，但不会超过限制的最大channel数
                     */
                    @Override
                    public void channelCreated(Channel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                        pipeline.addLast(new HttpResponseDecoder());
                        // 客户端发送的是httpRequest，所以要使用HttpRequestEncoder进行编码
                        pipeline.addLast(new HttpRequestEncoder());
                        //消息聚合，最大512kb
                        pipeline.addLast("aggregator", new HttpObjectAggregator(512*1024));
                        pipeline.addLast(responseHandler);
                    }

                    /**
                     *  获取连接池中的channel
                     */
                    @Override
                    public void channelAcquired(Channel ch) {
                    }
                };

                return new FixedChannelPool(boot.remoteAddress(url.getHost(), url.getPort()), handler, 50);
            }
        };
    }

    @ChannelHandler.Sharable
    abstract static class AbstractResponseHandler extends ChannelInboundHandlerAdapter {
        public AbstractResponseHandler() {}

        public abstract void init();
    }

    public static class OutStreamResponseHandler extends AbstractResponseHandler {

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

    public static class Wait4ResponseHandler extends AbstractResponseHandler {
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
