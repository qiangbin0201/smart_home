package com.smart.home.utils.ua;

public class UserActionConstants {
    public static class UAStartWeiboLogin {
        public final static String EVENT = "stat_weibo_login";
    }

    public static class UASkipWeiboLogin {
        public final static String EVENT = "stat_skip_login";
    }

    public static class UAGotoTabZaisou {
        public final static String EVENT = "stat_goto_tab_zaisou";
    }

    public static class UARefreshRemindInFeed {
        public final static String EVENT = "stat_refresh_remind_in_feed";
        public final static String TYPE_CHANNEL_NAME = "channel_name";
    }

    public static class UAViewItemDetail {
        public final static String EVENT = "stat_view_item_detail";
        public final static String TYPE_CHANNEL_NAME = "channel_name";
        public final static String API = "api";
    }

    public static class UARefreshDragDown {
        public final static String EVENT = "stat_refresh_drag_down";
        public final static String TYPE_CHANNEL_NAME = "channel_name";
    }

    public static class UARefreshDragUp {
        public final static String EVENT = "stat_refresh_drag_up";
        public final static String TYPE_CHANNEL_NAME = "channel_name";
    }

    public static class UASocialtagExpand {
        public final static String EVENT = "stat_btn_socialtag_expand";
        public final static String TYPE_TAG_NAME = "tag_name";
    }

    public static class UASocialtagCollapse {
        public final static String EVENT = "stat_btn_socialtag_collapse";
        public final static String TYPE_TAG_NAME = "tag_name";
    }

    public static class UASocialtagPortraitClick {
        public final static String EVENT = "stat_socialtag_portrait_click";
    }

    public static class UASocialtagCommentClick {
        public final static String EVENT = "stat_socialtag_comment_click";
    }

    public static class UASocialtagMoreClick {
        public final static String EVENT = "stat_socialtag_more_click";
    }

    public static class UADetailPageReadingDuration {
        public final static String EVENT = "stat_detail_page_reading_duration";
        public final static String TYPE_CHANNEL_NAME = "channel_name";
    }

    public static class UAClickAction {
        public final static String EVENT = "stat_detail_page_exit_position";
        public final static String TYPE_CHANNEL_NAME = "channel_name";
    }

    public static class UADetailPageExitPosition {
        public final static String EVENT = "stat_detail_page_exit_position";
        public final static String TYPE_CHANNEL_NAME = "channel_name";
    }

    public static class UABodyShareWeibo {
        public final static String EVENT = "stat_body_share_weibo";
        public final static String TYPE_CHANNEL_NAME = "channel_name";
    }

    public static class UABodyWebchat {
        public final static String EVENT = "stat_body_share_wechat";
        public final static String TYPE_CHANNEL_NAME = "channel_name";
    }

    public static class UABodyShareWebchatMoment {
        public final static String EVENT = "stat_body_share_wechat_moments";
        public final static String TYPE_CHANNEL_NAME = "channel_name";
    }

    public static class UANewsReferRecoLink {
        public final static String EVENT = "stat_news_refer_reco_link";
        public final static String TYPE_CHANNEL_NAME = "channel_name";
    }

    public static class UACommentBtnLike {
        public final static String EVENT = "stat_comment_btn_like";
        public final static String TYPE_USER_ID = "user_id";
    }

    public static class UACommentQueryMore {
        public final static String EVENT = "stat_comment_query_more";
    }

    public static class UABottomBarComment {
        public final static String EVENT = "stat_bottom_bar_comment";
    }

    public static class UABottomBarSendComment {
        public final static String EVENT = "stat_bottom_bar_send_comment";
    }

    public static class UABottomBarSyncWeibo {
        public final static String EVENT = "stat_bottom_bar_sync_weibo";
    }

    public static class UABottomBarCollect {
        public final static String EVENT = "stat_bottom_bar_collect";
        public final static String TYPE_NAME = "type";
    }

    public static class UABottomBarShare {
        public final static String EVENT = "stat_bottom_bar_share";
    }

    public static class UAPanelShareWechat {
        public final static String EVENT = "stat_panel_share_wechat";
        public final static String TYPE_NAME = "type";
    }

    public static class UAPanelShareWechatMoments {
        public final static String EVENT = "stat_panel_share_wechat_moments";
        public final static String TYPE_NAME = "type";
    }

    public static class UAPanelShareWeibo {
        public final static String EVENT = "stat_panel_share_weibo";
        public final static String TYPE_NAME = "type";
    }

    public static class UAPanelShareQq {
        public final static String EVENT = "stat_panel_share_qq";
        public final static String TYPE_NAME = "type";
    }

    public static class UAPanelShareQzone {
        public final static String EVENT = "stat_panel_share_qzone";
        public final static String TYPE_NAME = "type";
    }

    public static class UAPanelShareCopy {
        public final static String EVENT = "stat_panel_share_copy";
        public final static String TYPE_NAME = "type";
    }

    public static class UAPanelShareCollect {
        public final static String EVENT = "stat_panel_share_collect";
        public final static String TYPE_NAME = "type";
    }

    public static class UAClickZaikanCategory {
        public final static String TYPE_CHANNEL_NAME = "channel_name";
        public final static String EVENT = "stat_click_zaikan_category";
    }


    public static class UAPicRefreRecoLink {
        public final static String EVENT = "stat_pic_refer_reco_link";
    }

    public static class UAAccountSettingClick {
        public final static String EVENT = "stat_my_account_click";
    }

    public static class UAMyCollectionClick {
        public final static String EVENT = "stat_my_collection_click";
    }

    public static class UAMyMessageClick {
        public final static String EVENT = "stat_my_message_click";
    }

    public static class UACollectionItemClick {
        public final static String EVENT = "stat_collection_item_click";
    }

    public static class UACollectionItemDelete {
        public final static String EVENT = "stat_collection_item_delete";
    }

    public static class UALogout {
        public final static String EVENT = "stat_logout";
    }

    public static class UAEnterApp {
        public final static String EVENT = "stat_enter_app";
    }

    public static class UAZaisoPullUp {
        public final static String EVENT = "stat_zaisou_feed_refresh_drag_up";
        public final static String USER_TYPE = "weibo_user_type";
    }

    public static class UAZaisoPortraitClick {
        public final static String EVENT = "stat_zaisou_reco_portrait_click";
        public final static String USER_TYPE = "weibo_user_type";
    }

    public static class UAZaisoSearchInterest {
        public final static String EVENT = "stat_zaisou_input_search_interest";
    }

    public static class UAZaisoSearchArticle {
        public final static String EVENT = "stat_zaisou_input_search_article";
    }

    public static class UAZaisoNewsClick {
        public final static String EVENT = "stat_zaisou_feed_card_click";
        public final static String CHANNEL_NAME = "channel_name";
    }

    public static class UAClearCacheClicked {
        public final static String EVENT = "stat_setting_clear_cache";
        public final static String LOGIN_STATUS = "login_status";
    }

    public static class UACurrenVersionClicked {
        public final static String EVENT = "stat_setting_current_version";
        public final static String LOGIN_STATUS = "login_status";
    }


    public static class UADataSavingClicked {
        public final static String EVENT = "stat_setting_data_saving";
        public final static String LOGIN_STATUS = "login_status";
    }

    public static class UAUserAgreement {
        public final static String EVENT = "stat_user_agreement";
        public final static String LOGIN_STATUS = "login_status";
    }

    public static class UAFeedbackSuccess {
        public final static String EVENT = "stat_feedback_success";
        public final static String LOGIN_STATUS = "login_status";
    }

    public static class UAVideoPlay {
        public final static String EVENT = "stat_video_view";
        public final static String TYPE = "position";
        public final static String VALUE_CHANNEL_PAGE = "channel_page";
        public final static String VALUE_DETAIL_PAGE = "detail_page";
    }

    public static class UAGifPlay {
        public final static String EVENT = "stat_gif_view";
        public final static String TYPE = "position";
        public final static String VALUE_CHANNEL_PAGE = "channel_page";
        public final static String VALUE_DETAIL_PAGE = "detail_page";
    }

    public static class UAJokeLike {
        public final static String EVENT = "news_detail_like";
        public final static String TYPE = "type";
        public final static String VALUE_GIF_LIKE = "gif_like";
        public final static String VALUE_LIKE = "like";
        public final static String VALUE_DISLIKE = "dislike";
        public final static String VALUE_GIF_DISLIKE = "gif_dislike";
        public final static String VALUE_JOKE_LIKE = "joke_like";
        public final static String VALUE_JOKE_DISLIKE = "joke_dislike";
    }

    public static class UAChannelSort {
        public final static String EVENT = "stat_channel_sort";
        public final static String TYPE = "channel_name";
    }

    public static class UATagsClick {
        public final static String EVENT = "stat_my_tags_click";
    }

    public static class UAVideoFullScreenPlay {
        public final static String EVENT = "stat_video_view_fullscreen";
        public final static String TYPE = "position";
        public final static String VALUE_CHANNEL_PAGE = "channel_page";
        public final static String VALUE_DETAIL_PAGE = "detail_page";
    }

    public static class UAVideoPlayPercent {
        public final static String EVENT = "stat_video_view_length";
        public final static String TYPE = "length";
    }

    public static class UAVideoRelatedClick {
        public final static String EVENT = "stat_video_refer_reco_link";
    }

    public static class UAVideoAction {
        public final static String EVENT = "stat_video_like";
        public final static String TYPE = "type";
        public final static String VALUE_LIKE = "like";
        public final static String VALUE_DISLIKE = "dislike";
    }

    public static class UATagHint {
        public final static String EVENT = "stat_add_tag_hint";
    }

    public static class UATagInput {
        public final static String EVENT = "stat_add_tag_input";
    }

    public static class UATagDelete {
        public final static String EVENT = "stat_tag_delete";
    }

    public static class UASettingItemClick {
        public final static String EVENT = "stat_app_setting_click";
    }

    public static class UAChannelClickRefresh {
        public final static String EVENT = "stat_refresh_channel_click";
        public final static String TYPE = "channel_name";
    }


    public static class UALiveTabClick {
        public final static String EVENT = "stat_goto_tab_show";
    }

    public static class UALivePlay {
        public final static String EVENT = "stat_view_show";
    }

    public static class UALiveShare {
        public final static String EVENT = "stat_share_show";
    }

//	public static class UAMainPage {
//		public final static String EVENT = "MainPage";
//		public final static String TYPE_BBS = "查看公告板";
//		public final static String TYPE_CAREFREE_INDEX = "查看畅行指数";
//		public final static String TYPE_USE_CAREFREE = "使用畅快方式";
//		public final static String TYPE_POSITION_BANNER = "推荐位1Banner";
//		public final static String TYPE_POSITION_LIST = "推荐位2美车堂";
//	}
//
//	public static class UAMainMapPage {
//		public final static String EVENT = "MainMapPage";
//		public final static String TYPE_TMC_LAYER = "实时路况图层";
//		public final static String TYPE_TRAFFIC_EVENT_LAYER = "交通事件图层";
//		public final static String VALUE_OPEN = "开";
//		public final static String VALUE_CLOSE = "关";
//	}
//
//	public static class UAAppStart {
//		public final static String EVENT = "AppStart";
//		public final static String TYPE_NOTIFY = "查看notify消息启动";
//		public final static String TYPE_SHARE = "飞享启动";
//	}
//
//	public static class UAMapSelect {
//		public final static String EVENT = "MapSelect";
//		public final static String TYPE_MAIN = "主地图界面";
//		public final static String TYPE_SEARCH = "搜索结果界面";
//	}
//
//	public static class UAWeiluPage {
//		public final static String EVENT = "WeiluPage";
//		public final static String TYPE_FOLLOW = "使用跟她走";
//	}
//
//	public static class UASearchPage {
//		public final static String EVENT = "SearchPage";
//		public final static String TYPE_SEARCH = "查看搜索记录";
//		public final static String TYPE_POI = "查看收藏记录";
//		public final static String TYPE_NAVI = "查看导航记录";
//	}
//
//	public static class UASearchResult {
//		public final static String EVENT = "SearchResult";
//		public final static String TYPE_CITY = "查看切换城市列表";
//		public final static String TYPE_LIST = "查看搜索结果列表";
//	}
//
//	public static class UAPOICollect {
//		public final static String EVENT = "POICollect";
//		public final static String TYPE_COLLECT = "POI收藏操作";
//		public final static String VALUE_UPLOAD = "云收藏";
//		public final static String VALUE_DOWNLOAD = "云同步";
//		public final static String TYPE_CAREFREE = "畅快设置";
//		public final static String VALUE_HOME = "畅快回家";
//		public final static String VALUE_WORK = "畅快上班";
//		public final static String VALUE_POINT_1 = "畅快目的地一";
//		public final static String VALUE_POINT_2 = "畅快目的地二";
//	}
//
//	public static class UANaviRecord {
//		public final static String EVENT = "NaviRecord";
//		public final static String TYPE_SYNC = "云同步操作";
//		public final static String TYPE_START = "查看起点";
//		public final static String TYPE_END = "查看终点";
//	}
//
//	public static class UARoutePlan {
//		public final static String EVENT = "RoutePlan";
//		public final static String TYPE_ANALYZE = "线路分析";
//		public final static String VALUE_SPEED = "查看速度分析";
//		public final static String VALUE_COST = "查看耗时分析";
//		public final static String VALUE_ENVIRONMENT = "查看环保分析";
//		public final static String TYPE_OPERATION = "线路规划操作";
//		public final static String VALUE_ROUTE_PLAN = "查看线路规划";
//		public final static String VALUE_ROUTE_ANALYZE = "查看线路分析";
//		public final static String VALUE_TRAFFIC_EVENT = "查看交通事件";
//		public final static String TYPE_NAVI = "点击导航";
//		public final static String VALUE_TMC = "甩堵路线";
//		public final static String VALUE_BEST = "常规路线";
//		public final static String TYPE_TIME = "线路分析查看时间段";
//	}
//
//	public static class UANavi {
//		public final static String EVENT = "Navi";
//		public final static String TYPE_END = "导航结束";
//		public final static String VALUE_END_HORIZONTAL = "横屏结束";
//		public final static String VALUE_END_VERTICAL = "竖屏结束";
//		public final static String VALUE_END_MIDWAY_VERTICAL = "中途竖屏结束";
//		public final static String VALUE_END_MIDWAY_HORIZONTAL = "中途横屏结束";
//		public final static String TYPE_REMAIN = "余程切换";
//		public final static String VALUE_SWITCH_4 = "4公里";
//		public final static String VALUE_SWITCH_8 = "8公里";
//		public final static String VALUE_SWITCH_MIDWAY = "余程";
//		public final static String TYPE_OPERATION = "导航操作";
//		public final static String VALUE_SPEEDER_LOOK = "查看加速器";
//		public final static String VALUE_SPEEDER_USE = "使用加速器";
//		public final static String VALUE_BROWSE_ALL = "查看全景";
//		public final static String VALUE_ROUTE_CHANGE = "道路切换";
//		public final static String TYPE_NAVI = "导航时长";
//
//	}
//
//	public static class UAActiveLogin {
//		public final static String EVENT = "ActiveLogin";
//		public final static String TYPE_MORNING = "早高峰(7点-10点)";
//		public final static String TYPE_NIGHT = "晚高峰(16点-20点)";
//		public final static String TYPE_NORMAL = "平时";
//	}
//
//	public static class UAShare {
//		public final static String EVENT = "Share";
//		public final static String TYPE_SMS = "短信";
//		public final static String TYPE_WEIXIN = "微信";
//	}
//
//	public static class UAOfflineMap {
//		public final static String EVENT = "OfflineMap";
//		public final static String TYPE_ROUTE = "下载路口放大图";
//		public final static String TYPE_MAP = "下载离线地图";
//	}
//
//	public static class UAMerchantDetail {
//		public final static String EVENT = "MerchantDetail";
//		public final static String TYPE_DETAIL = "查看详情";
//		public final static String TYPE_ROUTE_PLAN = "导航规划";
//		public final static String TYPE_COUPON = "查看优惠券";
//	}
//
//	public static class UACarefree {
//		public final static String EVENT = "Carefree";
//		public final static String TYPE_HOME = "畅快回家";
//		public final static String TYPE_WORK = "畅快上班设置";
//		public final static String TYPE_CAREFREE_1 = "畅快目的地1";
//		public final static String TYPE_CAREFREE_2 = "畅快目的地2";
//		public final static String VALUE_USE = "使用";
//		public final static String VALUE_COMPLETE = "完成";
//	}
//
//	public static class UACoupon {
//		public final static String EVENT = "Coupon";
//		public final static String TYPE_SMS = "短信飞享";
//		public final static String TYPE_WEIXIN = "微信飞享";
//		public final static String TYPE_WEIBO = "微博飞享";
//		public final static String TYPE_GET = "领取优惠券";
//		public final static String TYPE_LIST = "查看商家列表";
//		public final static String TYPE_OPEN_SMS = "短信飞享打开优惠券";
//	}

}