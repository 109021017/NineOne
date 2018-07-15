package com.u91porn.data;

import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.u91porn.cookie.SetCookieCache;
import com.u91porn.cookie.SharedPrefsCookiePersistor;
import com.u91porn.utils.CommonHeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author flymegoc
 * @date 2018/1/27
 */
public class ApiManager {

    private static final String TAG = ApiManager.class.getSimpleName();

    private NoLimit91PornServiceApi mNoLimit91PornServiceApi;
    private SharedPrefsCookiePersistor sharedPrefsCookiePersistor;
    private SetCookieCache setCookieCache;
    private PersistentCookieJar cookieJar;
    private Context context;
    private static ApiManager sInstance = null;

    /**
     * 需是applicationContext
     */
    public ApiManager(Context context) {
        this.context = context;
    }

    public static ApiManager getInstance() {
        return sInstance;
    }

    public static void init(Context context){
        sInstance = new ApiManager(context);
    }

    /**
     * 清除cookies
     */
    public void cleanCookies() {
        if (cookieJar == null) {
            return;
        }
        cookieJar.clear();
    }

    public SetCookieCache getSetCookieCache() {
        return setCookieCache;
    }

    public SharedPrefsCookiePersistor getSharedPrefsCookiePersistor() {
        return sharedPrefsCookiePersistor;
    }

    /**
     * 初始化Retrifit网络请求
     */
    public NoLimit91PornServiceApi init91PornRetrofitService() {
        sharedPrefsCookiePersistor = new SharedPrefsCookiePersistor(context);
        setCookieCache = new SetCookieCache();
        cookieJar = new PersistentCookieJar(setCookieCache, sharedPrefsCookiePersistor);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.cookieJar(cookieJar);
        builder.addInterceptor(new CommonHeaderInterceptor());
        builder.readTimeout(5, TimeUnit.SECONDS);
        builder.writeTimeout(5, TimeUnit.SECONDS);
        builder.connectTimeout(5, TimeUnit.SECONDS);

        //动态切换baseUrl 配置
        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://91porn.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        return retrofit.create(NoLimit91PornServiceApi.class);
    }

    public NoLimit91PornServiceApi getNoLimit91PornService() {
        if (mNoLimit91PornServiceApi == null) {
            synchronized (NoLimit91PornServiceApi.class) {
                mNoLimit91PornServiceApi = init91PornRetrofitService();
            }
        }
        return mNoLimit91PornServiceApi;
    }

}
