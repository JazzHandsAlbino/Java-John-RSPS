package com.johnrsps.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main GameServer class for John Java RSPS
 * Bootstraps the 317 protocol server and handles client connections
 */
public class GameServer {
    
    private static final Logger logger = LoggerFactory.getLogger(GameServer.class);
    
    private static final int PORT = 43594;
    private static final int BOSS_THREADS = 1;
    private static final int WORKER_THREADS = 4;
    
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    
    public GameServer() {
        this.bossGroup = new NioEventLoopGroup(BOSS_THREADS);
        this.workerGroup = new NioEventLoopGroup(WORKER_THREADS);
    }
    
    /**
     * Start the game server
     */
    public void start() throws InterruptedException {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new GameServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true);
            
            ChannelFuture future = bootstrap.bind(PORT).sync();
            logger.info("========================================");
            logger.info("John Java RSPS Server Started");
            logger.info("Protocol: 317");
            logger.info("Port: {}", PORT);
            logger.info("========================================");
            
            future.channel().closeFuture().sync();
        } finally {
            shutdown();
        }
    }
    
    /**
     * Shutdown the server gracefully
     */
    public void shutdown() {
        logger.info("Shutting down John Java RSPS Server...");
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
    
    /**
     * Main entry point
     */
    public static void main(String[] args) {
        GameServer server = new GameServer();
        try {
            server.start();
        } catch (InterruptedException e) {
            logger.error("Server interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}