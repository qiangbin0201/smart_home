package com.smart.home.utils.ua;

import android.content.Context;

public interface UserActionIn {

    /**
     * Activity-Resume
     */
    @UserActionAnnotation(needUpload = true)
    public void uaOnResume(Context context);

    /**
     * Activity-Pause
     */
    @UserActionAnnotation(needUpload = true)
    public void uaOnPause(Context context);

    /**
     * 微博登录
     */
    @UserActionAnnotation(needUpload = true)
    public void uaWeiboLogin(Context context, String type);

    /**
     * 微博登录
     */
    @UserActionAnnotation(needUpload = true)
    public void uaWeiboSkipLogin(Context context, String type);

    /**
     * 跳转到在搜tab
     */
    @UserActionAnnotation(needUpload = true)
    public void uaGotoTabZaisou(Context context, String type);

    /**
     * 刚刚看到这里,点击刷新
     */
    @UserActionAnnotation(needUpload = true)
    void uaRefreshRemindInFeed(Context context, String channelName);

    /**
     * 查看详情
     */
    @UserActionAnnotation(needUpload = true)
    void uaViewItemDetail(Context context, String channelName);

    /**
     * 查看详情
     */
    @UserActionAnnotation(needUpload = true)
    void uaViewItemDetail(Context context, String channelName, String api);

    /**
     * 下拉刷新次数
     */
    @UserActionAnnotation(needUpload = true)
    void uaRefreshDragDown(Context context, String channelName);

    /**
     * 上拉刷新次数
     */
    @UserActionAnnotation(needUpload = true)
    void uaRefreshDragUp(Context context, String channelName);

    /**
     * social tag点击展开
     */
    @UserActionAnnotation(needUpload = true)
    void uaSocialtagExpand(Context context, String tagName);

    /**
     * social tag点击收起
     */
    @UserActionAnnotation(needUpload = true)
    void uaSocialtagCollapse(Context context, String tagName);

    /**
     * 大V头像点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaSocialtagPortraitClick(Context context);

    /**
     * 大V评论内容点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaSocialtagCommentClick(Context context);

    /**
     * 更多按钮点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaSocialtagMoreClick(Context context);

    /**
     * 页面停留时长
     */
    @UserActionAnnotation(needUpload = true)
    void uaDetailPageReadingDuration(Context context, String channelName, int timeLen);

    /**
     * 页面停留位置
     */
    @UserActionAnnotation(needUpload = true)
    void uaDetailPageExitPosition(Context context, String channelName, int position);


    /**
     * 微博分享按钮点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaBodyShareWeibo(Context context, String channelName);

    /**
     * 微信分享按钮点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaBodyShareWebchat(Context context, String channelName);


    /**
     * 朋友圈分享按钮点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaBodyShareWebchatMoment(Context context, String channelName);

    /**
     * 相关阅读文章点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaNewsReferRecoLink(Context context, String channelName);

    /**
     * 朋友圈分享按钮点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaEnterApp(Context context);

    /**
     * 评论点赞
     *
     * @param context
     * @param userId
     */
    @UserActionAnnotation(needUpload = true)
    void uaCommentBtnLike(Context context, String userId);

    /**
     * 评获取更多评论
     */
    @UserActionAnnotation(needUpload = true)
    void uaCommentQueryMore(Context context);

    /**
     * “写评论”按钮点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaBottomBarComment(Context context);

    /**
     * “发送”按钮点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaBottomBarSendComment(Context context);

    /**
     * “同步到微博”按钮点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaBottomBarSyncWeibo(Context context);

    /**
     * 点击切换频道
     */
    @UserActionAnnotation(needUpload = true)
    void uaClickZaikanCategory(Context context, String channelName);

    /**
     * “收藏”按钮点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaBottomBarCollect(Context context, String typeName);

    /**
     * “分享”按钮点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaBottomBarShare(Context context);

    /**
     * 分享到微信
     */
    @UserActionAnnotation(needUpload = true)
    void uaPanelShareWechat(Context context, String typeName);

    /**
     * 分享到朋友圈
     */
    @UserActionAnnotation(needUpload = true)
    void uaPanelShareWechatMoments(Context context, String typeName);

    /**
     * 分享到微博
     */
    @UserActionAnnotation(needUpload = true)
    void uaPanelShareWeibo(Context context, String typeName);

    /**
     * 分享到QQ
     */
    @UserActionAnnotation(needUpload = true)
    void uaPanelShareQq(Context context, String typeName);

    /**
     * 分享到Qzone
     */
    @UserActionAnnotation(needUpload = true)
    void uaPanelShareQzone(Context context, String typeName);


    /**
     * “复制链接”按钮点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaPanelShareCopy(Context context, String typeName);

    /**
     * “收藏”按钮点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaPanelShareCollect(Context context, String typeName);

    /**
     * 推荐图集点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaPicReferRecoLink(Context context);

    /**
     * “账户设置”菜单点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaAccountSettingClick(Context context);

    /**
     * “我的收藏”菜单点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaMyCollectionClick(Context context);

    /**
     * “我的收藏”菜单点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaMyMessageClick(Context context);

    /**
     * 收藏卡片点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaCollectionItemClick(Context context);

    /**
     * 删除收藏
     */
    @UserActionAnnotation(needUpload = true)
    void uaCollectionItemDelete(Context context);

    /**
     * 退出登录 - 点击
     */
    @UserActionAnnotation(needUpload = true)
    void uaLogout(Context context);

    /**
     * 在搜今日兴趣上拉加载更多
     *
     * @param context
     */
    @UserActionAnnotation(needUpload = true)
    void uaZaisoPullUp(Context context);

    /**
     * 在搜推荐头像列表点击
     *
     * @param context
     */
    @UserActionAnnotation(needUpload = true)
    void uaZaisoPortraitClick(Context context);

    /**
     * 在搜"兴趣图谱"搜索次数
     *
     * @param context
     */
    @UserActionAnnotation(needUpload = true)
    void uaZaisoSearchInterest(Context context);

    /**
     * 在搜"人和文章"搜索次数
     *
     * @param context
     */
    @UserActionAnnotation(needUpload = true)
    void uaZaisoSearchArticle(Context context);

    /**
     * 在搜页面的新闻点击
     *
     * @param context
     */
    @UserActionAnnotation(needUpload = true)
    void uaZaisoNewsClick(Context context, String typeName);

    /**
     * 清除缓存菜单点击
     *
     * @param context
     * @param loginStatus
     */
    @UserActionAnnotation(needUpload = true)
    void uaClearCacheClicked(Context context, String loginStatus);

    /**
     * 当前版本菜单点击
     *
     * @param context
     * @param loginStatus
     */
    @UserActionAnnotation(needUpload = true)
    void uaCurrenVersionClicked(Context context, String loginStatus);


    /**
     * 非WIFI流量菜单点击
     *
     * @param context
     * @param loginStatus
     */
    @UserActionAnnotation(needUpload = true)
    void uaDataSavingClicked(Context context, String loginStatus);

    /**
     * 查看《用户协议》
     *
     * @param context
     * @param loginStatus
     */
    @UserActionAnnotation(needUpload = true)
    void uaUserAgreement(Context context, String loginStatus);

    /**
     * 成功提交反馈
     *
     * @param context
     * @param loginStatus
     */
    @UserActionAnnotation(needUpload = true)
    void uaFeedbackSuccess(Context context, String loginStatus);


    /**
     *  视频播放
     *
     * @param context
     * @param page
     */
    void uaVideoPlay(Context context, String page);

    /**
     * 段子、gif点赞点踩
     *
     * @param context
     * @param type
     */
    @UserActionAnnotation(needUpload = true)
    void uaJokeLike(Context context, String type);

    /**
     * 视频全屏播放
     *
     * @param context
     * @param page
     */
    void uaVideoFullScreenPlay(Context context, String page);

    /**
     *  Gif播放
     *
     * @param context
     * @param page
     */
    @UserActionAnnotation(needUpload = true)
    void uaGifPlay(Context context, String page);


    /**
     * 拖拽偏序的频道
     *
     * @param context
     * @param channelName
     */
    @UserActionAnnotation(needUpload = true)
    void uaChannelSort(Context context, String channelName);

    /**
     * 我的标签菜单点击
     *
     * @param context
     */
    @UserActionAnnotation(needUpload = true)
    void uaTagsClick(Context context);

    /**
     * 视频播放百分比
     *
     * @param context
     * @param percent
     */
    @UserActionAnnotation(needUpload = true)
    void uaVideoPlayPercent(Context context, int percent);

    /**
     * 相关视频的点击
     *
     * @param context
     */
    @UserActionAnnotation(needUpload = true)
    void uaVideoRelatedClick(Context context);

    /**
     * 视频的点赞或点踩
     *
     * @param context
     * @param action
     */
    @UserActionAnnotation(needUpload = true)
    void uaVideoAction(Context context, String action);

    /**
     * 推荐头像点击
     *
     * @param context
     */
    @UserActionAnnotation(needUpload = true)
    void uaTagHint(Context context);

    /**
     * "兴趣"搜索
     *
     * @param context
     */
    @UserActionAnnotation(needUpload = true)
    void uaTagInput(Context context);

    /**
     * 标签删除
     *
     * @param context
     */
    @UserActionAnnotation(needUpload = true)
    void uaTagDelete(Context context);

    /**
     * 设置
     *
     * @param context
     */
    @UserActionAnnotation(needUpload = true)
    void uaSettingItemClick(Context context);

    /**
     * 频道点击更新
     *
     * @param context
     * @param channelName
     */
    @UserActionAnnotation(needUpload = true)
    void uaChannelClickRefresh(Context context, String channelName);

    /**
     * 点击Tab的直播项
     *
     * @param context
     */
    @UserActionAnnotation(needUpload = true)
    void uaLiveTabClick(Context context);

    /**
     * 进入直播间
     */
    @UserActionAnnotation(needUpload = true)
    void uaLivePlay(Context context);

    /**
     * 直播分享
     */
    @UserActionAnnotation(needUpload = true)
    void uaLiveShare(Context context);

    /**
     * 直播分享
     */
    @UserActionAnnotation(needUpload = true)
    void uaClickLike(Context context, String channelName, String action);


}