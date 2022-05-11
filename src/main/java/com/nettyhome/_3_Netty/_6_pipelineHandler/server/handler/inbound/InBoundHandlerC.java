package com.nettyhome._3_Netty._6_pipelineHandler.server.handler.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author jrk
 * @date 2022-05-11 17:54.
 */
public class InBoundHandlerC extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("IutBoundHandlerC: "+msg);
        //super.channelRead(ctx, msg);
        ctx.channel().writeAndFlush(msg);
    }
}
