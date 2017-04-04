package com.smart.home.utils.ua;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

class UserActionImpl extends BaseAgent implements UserActionIn {

    @Override
    public void uaOnResume(Context context) {
        super.onResume(context);
    }

    @Override
    public void uaOnPause(Context context) {
        super.onPause(context);
    }

    @Override
    public void uaWeiboLogin(Context context, String type) {
        super.onEvent(context, type);
    }

    @Override
    public void uaWeiboSkipLogin(Context context, String type) {
        super.onEvent(context, type);
    }

    @Override
    public void uaGotoTabZaisou(Context context, String type) {
        super.onEvent(context, type);
    }

    @Override
    public void uaRefreshRemindInFeed(Context context, String channelName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UARefreshRemindInFeed.TYPE_CHANNEL_NAME, channelName);
        super.onEvent(context, UserActionConstants.UARefreshRemindInFeed.EVENT, map);
    }

    @Override
    public void uaViewItemDetail(Context context, String channelName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAViewItemDetail.TYPE_CHANNEL_NAME, channelName);
        super.onEvent(context, UserActionConstants.UAViewItemDetail.EVENT, map);
    }

    @Override
    public void uaViewItemDetail(Context context, String channelName, String api) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAViewItemDetail.TYPE_CHANNEL_NAME, channelName);
        map.put(UserActionConstants.UAViewItemDetail.API, api);
        super.onEvent(context, UserActionConstants.UAViewItemDetail.EVENT, map);
    }

    @Override
    public void uaRefreshDragDown(Context context, String channelName) {
        Log.e("channelName", "channelName:" + channelName);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UARefreshDragDown.TYPE_CHANNEL_NAME, channelName);
        super.onEvent(context, UserActionConstants.UARefreshDragDown.EVENT, map);
    }

    @Override
    public void uaRefreshDragUp(Context context, String channelName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UARefreshDragUp.TYPE_CHANNEL_NAME, channelName);
        super.onEvent(context, UserActionConstants.UARefreshDragUp.EVENT, map);
    }

    @Override
    public void uaSocialtagExpand(Context context, String tagName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UASocialtagExpand.TYPE_TAG_NAME, tagName);
        super.onEvent(context, UserActionConstants.UASocialtagExpand.EVENT, map);
    }

    @Override
    public void uaSocialtagCollapse(Context context, String tagName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UASocialtagCollapse.TYPE_TAG_NAME, tagName);
        super.onEvent(context, UserActionConstants.UASocialtagCollapse.EVENT, map);
    }

    @Override
    public void uaSocialtagPortraitClick(Context context) {
        super.onEvent(context, UserActionConstants.UASocialtagPortraitClick.EVENT);
    }

    @Override
    public void uaSocialtagCommentClick(Context context) {
        super.onEvent(context, UserActionConstants.UASocialtagCommentClick.EVENT);
    }

    @Override
    public void uaSocialtagMoreClick(Context context) {
        super.onEvent(context, UserActionConstants.UASocialtagMoreClick.EVENT);
    }

    @Override
    public void uaDetailPageReadingDuration(Context context, String channelName, int timeLen) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UADetailPageReadingDuration.TYPE_CHANNEL_NAME, channelName);
        super.onEventValue(context, UserActionConstants.UADetailPageReadingDuration.EVENT, map, timeLen);
    }

    @Override
    public void uaDetailPageExitPosition(Context context, String channelName, int position) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UADetailPageExitPosition.TYPE_CHANNEL_NAME, channelName);
        super.onEventValue(context, UserActionConstants.UADetailPageExitPosition.EVENT, map, position);
    }

    @Override
    public void uaBodyShareWeibo(Context context, String channelName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UABodyShareWeibo.TYPE_CHANNEL_NAME, channelName);
        super.onEvent(context, UserActionConstants.UABodyShareWeibo.EVENT, map);
    }

    @Override
    public void uaBodyShareWebchat(Context context, String channelName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UABodyWebchat.TYPE_CHANNEL_NAME, channelName);
        super.onEvent(context, UserActionConstants.UABodyWebchat.EVENT, map);
    }

    @Override
    public void uaBodyShareWebchatMoment(Context context, String channelName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UABodyShareWebchatMoment.TYPE_CHANNEL_NAME, channelName);
        super.onEvent(context, UserActionConstants.UABodyShareWebchatMoment.EVENT, map);
    }

    @Override
    public void uaNewsReferRecoLink(Context context, String channelName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UANewsReferRecoLink.TYPE_CHANNEL_NAME, channelName);
        super.onEvent(context, UserActionConstants.UANewsReferRecoLink.EVENT, map);
    }

    @Override
    public void uaEnterApp(Context context) {
        super.onEvent(context, UserActionConstants.UAEnterApp.EVENT);
    }

    @Override
    public void uaCommentBtnLike(Context context, String userId) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UACommentBtnLike.TYPE_USER_ID, userId);
        super.onEvent(context, UserActionConstants.UACommentBtnLike.EVENT, map);
    }

    @Override
    public void uaCommentQueryMore(Context context) {
        super.onEvent(context, UserActionConstants.UACommentQueryMore.EVENT);
    }

    @Override
    public void uaBottomBarComment(Context context) {
        super.onEvent(context, UserActionConstants.UABottomBarComment.EVENT);
    }

    @Override
    public void uaBottomBarSendComment(Context context) {
        super.onEvent(context, UserActionConstants.UABottomBarSendComment.EVENT);
    }

    @Override
    public void uaBottomBarSyncWeibo(Context context) {
        super.onEvent(context, UserActionConstants.UABottomBarSyncWeibo.EVENT);
    }

    @Override
    public void uaBottomBarCollect(Context context, String typeName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UABottomBarCollect.TYPE_NAME, typeName);
        super.onEvent(context, UserActionConstants.UABottomBarCollect.EVENT, map);
    }

    @Override
    public void uaBottomBarShare(Context context) {
        super.onEvent(context, UserActionConstants.UABottomBarShare.EVENT);
    }

    @Override
    public void uaPanelShareWechat(Context context, String typeName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAPanelShareWechat.TYPE_NAME, typeName);
        super.onEvent(context, UserActionConstants.UAPanelShareWechat.EVENT, map);
    }

    @Override
    public void uaPanelShareWechatMoments(Context context, String typeName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAPanelShareWechatMoments.TYPE_NAME, typeName);
        super.onEvent(context, UserActionConstants.UAPanelShareWechatMoments.EVENT, map);
    }

    @Override
    public void uaPanelShareWeibo(Context context, String typeName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAPanelShareWeibo.TYPE_NAME, typeName);
        super.onEvent(context, UserActionConstants.UAPanelShareWeibo.EVENT, map);
    }

    @Override
    public void uaPanelShareQq(Context context, String typeName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAPanelShareQq.TYPE_NAME, typeName);
        super.onEvent(context, UserActionConstants.UAPanelShareQq.EVENT, map);
    }

    @Override
    public void uaPanelShareQzone(Context context, String typeName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAPanelShareQzone.TYPE_NAME, typeName);
        super.onEvent(context, UserActionConstants.UAPanelShareQzone.EVENT, map);
    }

    @Override
    public void uaPanelShareCopy(Context context, String typeName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAPanelShareCopy.TYPE_NAME, typeName);
        super.onEvent(context, UserActionConstants.UAPanelShareCopy.EVENT, map);
    }

    @Override
    public void uaPanelShareCollect(Context context, String typeName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAPanelShareCollect.TYPE_NAME, typeName);
        super.onEvent(context, UserActionConstants.UAPanelShareCollect.EVENT, map);
    }

    @Override
    public void uaClickZaikanCategory(Context context, String channelName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAClickZaikanCategory.TYPE_CHANNEL_NAME, channelName);
        super.onEvent(context, UserActionConstants.UAClickZaikanCategory.EVENT, map);
    }

    @Override
    public void uaPicReferRecoLink(Context context) {
        super.onEvent(context, UserActionConstants.UAPicRefreRecoLink.EVENT);
    }

    @Override
    public void uaAccountSettingClick(Context context) {
        super.onEvent(context, UserActionConstants.UAAccountSettingClick.EVENT);
    }

    @Override
    public void uaMyCollectionClick(Context context) {
        super.onEvent(context, UserActionConstants.UAMyCollectionClick.EVENT);
    }

    @Override
    public void uaMyMessageClick(Context context) {
        super.onEvent(context, UserActionConstants.UAMyMessageClick.EVENT);
    }

    @Override
    public void uaCollectionItemClick(Context context) {
        super.onEvent(context, UserActionConstants.UACollectionItemClick.EVENT);
    }

    @Override
    public void uaCollectionItemDelete(Context context) {
        super.onEvent(context, UserActionConstants.UACollectionItemDelete.EVENT);
    }

    @Override
    public void uaLogout(Context context) {
        super.onEvent(context, UserActionConstants.UALogout.EVENT);
    }

    @Override
    public void uaZaisoPullUp(Context context) {
        super.onEvent(context, UserActionConstants.UAZaisoPullUp.EVENT);
    }

    @Override
    public void uaZaisoPortraitClick(Context context) {
        super.onEvent(context, UserActionConstants.UAZaisoPortraitClick.EVENT);
    }

    @Override
    public void uaZaisoSearchInterest(Context context) {
        super.onEvent(context, UserActionConstants.UAZaisoSearchInterest.EVENT);
    }

    @Override
    public void uaZaisoSearchArticle(Context context) {
        super.onEvent(context, UserActionConstants.UAZaisoSearchArticle.EVENT);
    }

    @Override
    public void uaZaisoNewsClick(Context context, String typeName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAZaisoNewsClick.CHANNEL_NAME, typeName);
        super.onEvent(context, UserActionConstants.UAZaisoNewsClick.EVENT, map);
    }

    @Override
    public void uaClearCacheClicked(Context context, String loginStatus) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAClearCacheClicked.LOGIN_STATUS, loginStatus);
        super.onEvent(context, UserActionConstants.UAClearCacheClicked.EVENT, map);
    }

    @Override
    public void uaCurrenVersionClicked(Context context, String loginStatus) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UACurrenVersionClicked.LOGIN_STATUS, loginStatus);
        super.onEvent(context, UserActionConstants.UACurrenVersionClicked.EVENT, map);
    }

    @Override
    public void uaDataSavingClicked(Context context, String loginStatus) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UADataSavingClicked.LOGIN_STATUS, loginStatus);
        super.onEvent(context, UserActionConstants.UADataSavingClicked.EVENT, map);
    }

    @Override
    public void uaUserAgreement(Context context, String loginStatus) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAUserAgreement.LOGIN_STATUS, loginStatus);
        super.onEvent(context, UserActionConstants.UAUserAgreement.EVENT, map);
    }

    @Override
    public void uaFeedbackSuccess(Context context, String loginStatus) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAFeedbackSuccess.LOGIN_STATUS, loginStatus);
        super.onEvent(context, UserActionConstants.UAFeedbackSuccess.EVENT, map);
    }

    @Override
    public void uaVideoPlay(Context context, String page) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAVideoPlay.TYPE, page);
        super.onEvent(context, UserActionConstants.UAVideoPlay.EVENT, map);
    }

    @Override
    public void uaJokeLike(Context context, String type) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAJokeLike.TYPE, type);
        super.onEvent(context, UserActionConstants.UAJokeLike.EVENT, map);
    }

    @Override
    public void uaChannelSort(Context context, String channelName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAChannelSort.TYPE, channelName);
        super.onEvent(context, UserActionConstants.UAChannelSort.EVENT, map);
    }

    @Override
    public void uaTagsClick(Context context) {
        super.onEvent(context, UserActionConstants.UATagsClick.EVENT);
    }

    @Override
    public void uaGifPlay(Context context, String page) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAGifPlay.TYPE, page);
        super.onEvent(context, UserActionConstants.UAGifPlay.EVENT, map);
    }

    @Override
    public void uaVideoFullScreenPlay(Context context, String page) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAVideoFullScreenPlay.TYPE, page);
        super.onEvent(context, UserActionConstants.UAVideoFullScreenPlay.EVENT, map);
    }

    @Override
    public void uaVideoPlayPercent(Context context, int percent) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAVideoPlayPercent.TYPE, String.valueOf(percent));
        super.onEvent(context, UserActionConstants.UAVideoPlayPercent.EVENT, map);
    }

    @Override
    public void uaVideoRelatedClick(Context context) {
        super.onEvent(context, UserActionConstants.UAVideoRelatedClick.EVENT);
    }


    @Override
    public void uaVideoAction(Context context, String action) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAVideoAction.TYPE, action);
        super.onEvent(context, UserActionConstants.UAVideoAction.EVENT, map);
    }

    @Override
    public void uaTagHint(Context context) {
        super.onEvent(context, UserActionConstants.UATagHint.EVENT);
    }

    @Override
    public void uaTagInput(Context context) {
        super.onEvent(context, UserActionConstants.UATagInput.EVENT);
    }

    @Override
    public void uaTagDelete(Context context) {
        super.onEvent(context, UserActionConstants.UATagDelete.EVENT);
    }

    @Override
    public void uaSettingItemClick(Context context) {
        super.onEvent(context, UserActionConstants.UASettingItemClick.EVENT);
    }


    @Override
    public void uaChannelClickRefresh(Context context, String channelName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAChannelClickRefresh.TYPE, channelName);
        super.onEvent(context, UserActionConstants.UAChannelClickRefresh.EVENT, map);
    }

    @Override
    public void uaLiveTabClick(Context context) {
        super.onEvent(context, UserActionConstants.UALiveTabClick.EVENT);
    }

    @Override
    public void uaLivePlay(Context context) {
        super.onEvent(context, UserActionConstants.UALivePlay.EVENT);
    }

    @Override
    public void uaLiveShare(Context context) {
        super.onEvent(context, UserActionConstants.UALiveShare.EVENT);
    }

    @Override
    public void uaClickLike(Context context, String channelName, String action) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(UserActionConstants.UAChannelClickRefresh.TYPE, channelName);
        super.onEvent(context, action, map);
    }


}