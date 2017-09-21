package com.chyss.myapplication.widget.retrofit.inter;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/3/28.
 */

public interface RequestServ
{
    //post表单传递
    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url,@FieldMap Map<String,String> map);

    //post json传递
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST
    Call<String> post(@Url String url,@Body RequestBody body);

    @GET
    Call<String> get(@Url String url,@QueryMap Map<String,String> map);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String,String> fieldMap, @HeaderMap Map<String,String> headMap);

    @GET
    Call<String> get(@Url String url,@QueryMap Map<String,String> queryMap,@HeaderMap Map<String,String> headMap);

    @FormUrlEncoded
    @POST
    Flowable<String> postFlowable(@Url String url, @FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST
    Observable<String> postObservable(@Url String url, @FieldMap Map<String,String> map);

    @GET
    Flowable<String> getFlowable(@Url String url,@QueryMap Map<String,String> map);

    @GET
    Observable<String> getObservable(@Url String url, @QueryMap Map<String,String> map);

    /**
     * 用于图片的上传服务器
     *
     * @param url   请求的url
     * @param imgs  请求时上传的图片
     * @return
     */
    @Multipart
    @POST
    Call<String> postUploadImgs(@Url String url,@PartMap Map<String, RequestBody> imgs);

    /**
     *  name="uploadFile";filename="larg.jpg"
     * @param url
     * @param map
     * @param img
     * @return
     */
    @Multipart
    @POST
    Flowable<String> postUploadImg(@Url String url,@PartMap Map<String,String> map,@Part("uploadFile\";filename=\"larg.jpg\"") RequestBody img);

    //body传递
//    @FormUrlEncoded
//    @POST("versions")
//    Call<ResponseBody> post(@Body Info info);

    //post表单传递
//    @FormUrlEncoded
//    @POST("versions")
//    Call<Base<Info>> post(@FieldMap Map<String,String> map);

    //post表单传递
//    @FormUrlEncoded
//    @POST("versions")
//    Call<ResponseBody> posturl(@Field("version") int version, @Field("platform") String platform);
//
//    @GET("versions")
//    Call<ResponseBody> get(@Query("platform") String platform,@Query("version") int version);
//
//    @GET("versions")
//    Call<String> get(@QueryMap Map<String,String> map);
//
//    //如果请求的相对地址也是需要调用方传递，那么可以使用@Path注解
//    @GET("book/{id}")
//    Call<ResponseBody> getBook(@Path("id") String id,@Header("Content-Range") String contentRange);

}
