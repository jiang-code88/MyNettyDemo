package com.nettyhome._3_Netty._6_pipelineHandler.server.handler.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author jrk
 * @date 2022-05-11 17:54.
 */
public class InBoundHandlerB extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("IutBoundHandlerB: "+msg);
        super.channelRead(ctx, msg);
    }
}
