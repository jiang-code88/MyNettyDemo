package com.nettyhome._4_wxIM.server.handler;

import com.nettyhome._4_wxIM.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author jrk
 * @date 2022-05-14 0:57.
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler() { }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!SessionUtil.hasLogin(ctx.channel())){
            // 未登录的数据包——直接强制关闭连接
            ctx.channel().close();
        }else{
            System.out.println("数据包验证登录已通过！");
            // 验证连接已经登录-可插拔的AuthHandler
            ctx.pipeline().remove(this);
            // 已登录的数据包-向下传递给后续的指令处理器
            super.channelRead(ctx, msg);
        }
    }

    // 当前的Handler被移除时触发的回调函数
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // handler被拆除时，如果连接已经关闭将拒绝触发拆除AuthHandler时的提醒信息。
        if(!ctx.channel().isActive()) return;
        if(SessionUtil.hasLogin(ctx.channel())){
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        }else{
            System.out.println("未登录验证，已强制关闭连接!");
        }
    }

    // 服务端当前 channel.close() 时触发的回调函数
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭！");
    }
}
