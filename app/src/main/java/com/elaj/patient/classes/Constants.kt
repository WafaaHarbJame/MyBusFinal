package com.elaj.patient.classes

import com.elaj.patient.MainActivityBottomNav


object Constants {

    const val LOCAL = "local"
    const val FACEBOOK = "facebook"
    const val TWITTER = "twitter"
    const val GOOGLE = "google"

    const val Arabic = "ar"
    const val English = "en"

    val MAIN_ACTIVITY_CLASS = MainActivityBottomNav::class.java
    //    public static final Class<LoginRegisterActivity> INVALID_TOKEN_ACTIVITY = LoginRegisterActivity.class;

    const val NORMAL_FONT = "Cairo-Regular.ttf"
    const val BOLD_FONT = "Cairo-Bold.ttf"
    const val ICON_AWSM_FONT = "fa_light_300.otf"
    const val ICON_OLD_AWSM_FONT = "fontawesome-webfont.ttf"
    const val ICON_MOON_FONT = "icomoon.ttf"

    const val FAVORITE = "favorite"
    const val HISTORY = "history"
    const val NORMAL = "normal"

    const val SALON = "salon"
    const val HOME = "home"

    const val AVATAR = "avatar"
    const val COVER = "cover"
    const val GALLERY = "gallery"

    const val TIME_ZONE = "GMT+3"
    const val COUNTRY_CODE = 966
    const val COUNTRY_CODE_STR = "00966"
    const val COUNTRY_CODE_PLUS = "+966"
    const val Paly_Link = "https://tjmeat.com/app"
    const val PRIVACY_POLICY = "privacy-policy"
    const val NOTIFICATION_TOPIC = "tjmeat"
    const val AUTHENTICATED = "authenticated"
    const val UNAUTHENTICATED = "AuthenticationException"

    const val LOADING = "loading"
    const val SUCCESS = "success"
    const val NO_DATA = "no_data"
    const val FAIL_DATA = "fail_data"
    const val SHOW_DATA = "show_data"
    const val ERROR_DATA = "error_data"
    const val NO_CONNECTION = "no_connection"


    const val CUSTOMER = "customer"
    const val PROVIDER = "provider"

    const val CURRENT = "upcoming"
    const val COMPLETED = "completed"

    const val ORDER_STATUS_PENDING = 0
    const val ORDER_STATUS_ACCEPTED = 1
    const val ORDER_STATUS_COMPLETED = 2
    const val ORDER_STATUS_REJECTED = 3

    const val MALE = "male"
    const val FEMALE = "female"
    const val BOTH = "both"

    const val ACTION_REFRESH_CHAT_STATUS = "action_refresh_chat_status"
    const val ACTION_REFRESH_CHAT_COUNT = "action_refresh_chat_count"
    const val PAGE_ABOUT = 1
    const val PAGE_COMPANY_SERVICES = 11
    const val PAGE_PEOPLE_SERVICES = 12
    const val PAGE_TERMS = 18
    const val PAGE_ADMIN_WORD = 7
    const val BOOK_APPOINTMENT_ID = 1

    const val REFRESH_MAIN_POSTS = "refresh_main_posts"
    const val REFRESH_CHAT_LIST = "refresh_cha_list"
    const val MESSAGE_SENT = "sent"
    const val MESSAGE_RECEIVED = "received"

    //    const val NO_CONNECTION = "no connection"
    const val TOKEN_PREFIX = "Bearer "
    const val TOKEN_INVALID = "Unauthorized"

//    const val ERROR = "error"
//    const val NULL = "null"
//    const val FAIL = "fail"

    const val UNCONFIRM = "unconfirm"
    const val AUTH_EXCEPTION = "AuthenticationException"
    const val COMING = "current"
    const val ARCHIVE = "expired"

    const val PHOTO_ALBUM = "photo_album"
    const val VIDEO_ALBUM = "video_album"

    const val KEY_MEMBER = "key_member"
    const val KEY_FIREBASE_TOKEN = "firebase_token"
    const val KEY_MEMBER_LOCATION = "key_member_location"
    const val KEY_MEMBER_LANGUAGE = "key_member_language"

    const val KEY_FIRST_RUN = "key_first_run"
    const val KEY_IS_RATE_APP = "key_is_rate_app"
    const val KEY_IS_NOTIFY = "key_is_notify"
    const val KEY_IS_CHAT_LIST = "key_is_chat_list"
    const val KEY_NOTIFY_TYPE = "key_notify_type"
    const val KEY_CATEGORIES = "key_categories"
    const val KEY_IS_CUSTOMER = "key_is_customer"
    const val KEY_CITIES = "key_cities"
    const val KEY_SETTINGS = "key_settings"
    const val KEY_GENDER = "key_gender"
    const val KEY_USERNAME = "key_username"
    const val KEY_USER_ID = "key_user_id"
    const val KEY_IMAGE_URL = "key_image_url"
    const val KEY_USER_NAME = "key_user_name"
    const val KEY_CHAT_ID = "key_chat_id"
    const val KEY_CAT_NAME = "key_cat_name"
    const val KEY_CAT_ID = "key_cat_id"
    const val KEY_PROVIDER_MODEL = "key_provider_model"
    const val KEY_IS_FROM_LOCAL = "key_is_from_local"
    const val KEY_IMAGES_LIST = "key_IMAGES_LIST"
    const val KEY_IMAGES_POS = "key_images_pos"

    //    const val KEY_PROVIDER_SERVICES = "key_provider_services"
    const val KEY_STAGE_NAME = "key_stage_name"
    const val KEY_REQUEST_ID = "key_request_id"
    const val KEY_PROVIDER_ID = "key_provider_id"
    const val KEY_LAT = "key_lat"
    const val KEY_LNG = "key_lng"
    const val KEY_SUB_CATS = "key_sub_cats"
    const val KEY_BOOK_URL = "key_book_url"
    const val KEY_BOOK_ID = "key_book_id"
    const val KEY_COUNTRIES = "key_countries"
    const val KEY_NEWS_MODEL = "key_news_model"
    const val KEY_IS_FAV = "key_is_fav"
    const val KEY_BOOK_POS = "key_book_pos"
    const val KEY_TITLE = "key_title"
    const val KEY_URL = "key_url"
    const val KEY_SEARCH_CITY = "key_search_city"
    const val KEY_SEARCH_SALARY = "key_search_salary"
    const val KEY_IS_BOOK_DOCTOR = "key_is_book_doctor"
    const val KEY_DOCTOR_MODEL = "key_doctor_model"
    const val KEY_DOCTOR_ID = "key_doctor_id"
    const val KEY_SERVICE_ID = "key_service_id"
    const val KEY_SERVICE_NAME = "key_service_name"
    const val KEY_IS_SELECT_LOCATION = "key_is_select_location"
    const val KEY_ALBUM_NAME = "key_album_name"
    const val KEY_DATE = "key_date"
    const val KEY_BODY = "key_body"
    const val KEY_SLIDER_URL = "key_slider_url"
    const val KEY_SLIDER_TITLE = "key_slider_title"
    const val KEY_IMAGE_DATA = "key_image_data"
    const val KEY_MESSAGE_ID = "key_message_id"
    const val KEY_COMPANY_CATEGORIES = "key_company_categories"
    const val KEY_CONVERSATION_ID = "key_conversation_id"
    const val KEY_TYPE = "key_type"
    const val KEY_NOTIFICATION_ID = "key_notification_id"
    const val KEY_COUNTRY_CODE = "key_country_code"
    const val KEY_MOBILE = "key_mobile"
    const val KEY_CODE_SENT = "key_code_sent"

    const val KEY_PASSWORD = "key_password"
    const val PASSWORD = "password"
    const val KEY_WALKTHROUGH_IMG_RES = "key_walkthrough_img_res"
    const val KEY_WALKTHROUGH_TITLE = "key_walkthrough_title"
    const val KEY_WALKTHROUGH_DESC = "key_walkthrough_desc"
    const val KEY_FRAGMENT_TYPE = "key_fragment_type"
    const val KEY_OPEN_TYPE = "key_open_type"
    const val KEY_LOCATION = "key_location"
    const val BROADCAST_REFRESH = "broadcast_refresh"
    const val KEY_LOGIN_PREFERANCE = "key_login_preferance"
    const val FRAG_TERMS = "frag_terms"
    const val FRAG_ABOUT = "frag_about"
    const val FRAG_POLICY = "frag_policy"
    const val FRAG_DELETE_ACCOUNT = "frag_delete_account"
    const val FRAG_CHAT_ARCHIVE = "frag_chat_archive"
    const val FRAG_BLOCK_ACCOUNTS = "frag_block_accounts"
    const val FRAG_MUTE_ACCOUNTS = "frag_mute_accounts"

    /** */
    const val DB_Sliders = "sliders_db"
    const val DB_Categories = "categories_db"
    const val DB_Settings = "settings_db"
    const val DB_Plans = "plans_db"
    const val DB_ServicesModel = 4
    const val DB_TermsModel = 5
    const val DB_RequestsModel = 6
    const val DB_CitiesModel = 7
    const val DB_AboutModel = 8
    const val DB_CompanyServicesModel = 9
    const val DB_PeopleServicesModel = 10
    const val DB_AdminWordModel = 11
    const val DB_CompletedOrdersModel = 12
    const val DB_ConfigModel = 13
    const val DB_Model = 14

    const val CAPTURE = "capture"
    const val PICK = "pick"
    const val SAVE = "save"
    const val CLEAR = "clear"
}