package com.chyss.myapplication.utils.analysis;

import org.json.JSONArray;
import org.json.JSONObject;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.data.ResultDesc;
import com.chyss.myapplication.utils.Logg;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 服务器返回json数据的解析.
 * 
 * @author chyss   2016-12-01
 *
 */
public class DataImpl
{
	/**
	 * 针对固定架构ResultDesc<T>类的json数据解析
	 * {"status":0,"msg":"succeed","commandCode":"\/v2\/versions","platform":"android","data":{"versions":[{"version":"10","update":"0","url":"dddd","uplog":"<img src=a onerror=alert(8)>"},{"version":"20","update":"1","url":"eeee","uplog":"<img src=a onerror=alert(8)>"}]}}
	 *	{"status":0,"msg":"succeed","commandCode":"\/v2\/versions","platform":"android","data":{"version":"10","update":"0","url":"dddd","uplog":"<img src=a onerror=alert(8)>"}}
	 * @param json 	需要解析的json数据
	 * @param t  		ResultDesc<T> 里面的 T  类
	 * @param <T> 		泛型，泛指ResultDesc<T> 里面的 T data
     * @return			解析好的数据ResultDesc<T>
     */
	public static <T> ResultDesc<T> getData(String json, Class<T> t)
	{
		if ("".equals(json))
		{
			return null;
		}
		ResultDesc<T> resultDesc = null;
		try
		{
			Type objectType = type(ResultDesc.class, t);
			//Type jsonType = new TypeToken<ResultDesc<Version>>() {}.getType();
			resultDesc = new Gson().fromJson(json, objectType);  //利用Gson解析json数据
		}
		catch (Exception e)
		{
			Logg.e(Net.TAG, "数据解析失败 : " + e);
		}
		return resultDesc;
	}

	/**
	 * 通过传入的不同类型参数，获取ParameterizedType用于Gson解析
	 *
	 * @param raw   	声明的类的Type
	 * @param args  	类中参数的Type数组
     * @return  		要解析的数据的ParameterizedType
     */
	private static ParameterizedType type(final Class raw, final Type... args) {
		return new ParameterizedType() {
			public Type getRawType() {
				return raw; //返回 type 对象，表示声明此类型的类或接口。
			}

			public Type[] getActualTypeArguments() {
				return args; //返回 type 对象，表示此类型实际类型参数的 Type 对象的数组。
			}

			public Type getOwnerType() {
				return null;
			}
		};
	}

	/**
	 * 针对有明确固定类型的json数据的解析
	 *
	 * @param json  	需要解析的json数据
	 * @param t 		json数据对应得实体类
	 * @param <T>		对应得实体类类型
     * @return
     */
	public static <T> T getBaseData(String json, Class<T> t)
	{
		if ("".equals(json))
		{
			return null;
		}
		T data = null;
		try
		{
			data = new Gson().fromJson(json, t);  //利用Gson解析json数据
		}
		catch (Exception e)
		{
			Logg.e(Net.TAG, "数据解析失败 : " + e);
		}
		return data;
	}

	/**
	 *	json数据解析
	 * @deprecated 	建议使用上面的getData方法解析数据
	 * @param json 	解析的json数据
	 * @param cla		解析在RsultDesc中的类
     * @return			ResultDesc对象
     */
	public static <T> ResultDesc<T> getDataImpl(String json, Class<T> cla)
	{
		if ("".equals(json))
		{
			return null;
		}
		Gson gson = new Gson();
		ResultDesc<T> resultDesc = gson.fromJson(json, ResultDesc.class);
		resultDesc.setData(getDataImpl(json, gson, resultDesc, cla));

		return resultDesc;
	}

	/**
	 * 利用泛型，统一了数据解析的入口，采用Gson进行数据的解析
	 *
	 * @param json   接口返回的json数据
	 * @param t      data对应得Class的传入
	 * @return
	 */
	private static <T> T getDataImpl(String json,Gson gson,ResultDesc resultDesc, Class<T> t)
	{
		if (resultDesc != null && resultDesc.getStatus() == 0)
		{
			try
			{
				if (resultDesc.toString().contains("data=[{"))     //JSONArray数组的解析
				{
					JSONArray jsonArray = new JSONObject(json).getJSONArray("data");
					resultDesc.setData(jsonArray);
				} else   //JSONObject解析
				{
					JSONObject jsonObject = new JSONObject(json).getJSONObject("data");
					resultDesc.setData(jsonObject);
				}

			} catch (Exception e)
			{
				Logg.e(Net.TAG, "数据解析失败 : " + e);
			}

			T data = gson.fromJson(resultDesc.getData().toString(), t);
			return data;

		} else
		{
			Logg.e(Net.TAG, "数据解析失败！");
			return null;
		}
	}
}
