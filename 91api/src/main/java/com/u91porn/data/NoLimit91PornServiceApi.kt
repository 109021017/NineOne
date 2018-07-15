package com.u91porn.data

import io.reactivex.Observable
import org.jetbrains.annotations.NotNull
import retrofit2.http.*


/**
 * Created by Wen on 02/03/2018.
 */
interface NoLimit91PornServiceApi {

    @GET("/index.php")
    fun indexPhp(@NotNull @Header("Referer") paramString: String): Observable<String>

    @GET("/view_video.php")
    fun getVideoPlayPage(@Query("viewkey") paramString1: String, @Header("X-Forwarded-For") paramString2: String, @Header("Referer") paramString3: String): Observable<String>

    @GET("/v.php")
    fun getCategoryPage(@Query("category") category: String, @Query("viewtype") viewtype: String, @Query("page") page: Int?, @Query("m") m: String, @Header("Referer") Referer: String): Observable<String>

    @GET("/v.php")
    fun recentUpdates(@Query("next") paramString1: String, @Query("page") paramInteger: Int?, @Header("Referer") paramString2: String): Observable<String>

    @FormUrlEncoded
    @POST("/login.php")
    fun login(@Field("username") paramString1: String, @Field("password") paramString2: String, @Field("fingerprint") paramString3: String, @Field("fingerprint2") paramString4: String, @Field("captcha_input") paramString5: String, @Field("action_login") paramString6: String, @Field("x") paramString7: String, @Field("y") paramString8: String, @Header("Referer") paramString9: String): Observable<String>

    @FormUrlEncoded
    @POST("/signup.php")
    fun register(@Query("next") paramString1: String, @Field("username") paramString2: String, @Field("password1") paramString3: String, @Field("password2") paramString4: String, @Field("email") paramString5: String, @Field("captcha_input") paramString6: String, @Field("fingerprint") paramString7: String, @Field("vip") paramString8: String, @Field("action_signup") paramString9: String, @Field("submit.x") paramString10: String, @Field("submit.y") paramString11: String, @Header("Referer") paramString12: String, @Header("X-Forwarded-For") paramString13: String): Observable<String>

    @GET("/my_favour.php")
    fun myFavorite(@Query("page") paramInt: Int, @Header("Referer") paramString: String): Observable<String>

    @FormUrlEncoded
    @POST("/my_favour.php")
    fun deleteMyFavorite(@Field("rvid") paramString1: String, @Field("removfavour") paramString2: String, @Field("x") paramInt1: Int, @Field("y") paramInt2: Int, @Header("Referer") paramString3: String): Observable<String>

    @GET("/ajax/myajaxphp.php")
    fun favoriteVideo(@Query("cpaint_function") paramString1: String, @Query("cpaint_argument[]") paramString2: String, @Query("cpaint_argument[]") paramString3: String, @Query("cpaint_argument[]") paramString4: String, @Query("cpaint_response_type") paramString5: String, @Header("Referer") paramString6: String): Observable<String>

    @GET("/show_comments2.php")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun getVideoComments(@Query("VID") paramString1: String, @Query("start") paramInt1: Int, @Query("comment_per_page") paramInt2: Int, @Header("Referer") paramString2: String): Observable<String>

    @GET("/ajax/myajaxphp.php")
    fun commentVideo(@Query("cpaint_function") paramString1: String, @Query("cpaint_argument[]") paramString2: String, @Query("cpaint_argument[]") paramString3: String, @Query("cpaint_argument[]") paramString4: String, @Query("cpaint_response_type") paramString5: String, @Header("Referer") paramString6: String): Observable<String>

    @FormUrlEncoded
    @POST("/post_comment.php")
    fun replyComment(@Field("comment") paramString1: String, @Field("username") paramString2: String, @Field("VID") paramString3: String, @Field("comment_id") paramString4: String, @Header("Referer") paramString5: String): Observable<String>

    @GET("/search_result.php")
    fun search(@Query("viewtype") paramString1: String, @Query("page") paramInt: Int, @Query("search_type") paramString2: String, @Query("search_id") paramString3: String, @Query("sort") paramString4: String, @Header("Referer") paramString5: String, @Header("X-Forwarded-For") paramString6: String): Observable<String>

    @GET("/uvideos.php")
    fun authorVideos(@Query("UID") paramString1: String, @Query("type") paramString2: String, @Query("page") paramInt: Int): Observable<String>

}