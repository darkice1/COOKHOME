/**
 * 
 */
package cookiehome;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;

import easy.io.EHttpClient;
import easy.util.Format;

/**
 * @author starneo@gmail.com 2016年10月18日
 */
public class CookHome
{
	private EHttpClient client = new EHttpClient();
	private static HashMap<String,String> USERMAP = null;
	
	private static Map<String,String> getUserMap()
	{
		if (USERMAP == null)
		{
			USERMAP = new HashMap<String,String>();
			
			USERMAP.put("_cityid", "110100");
			USERMAP.put("_device", Format.getRandAllString(36));
			USERMAP.put("_idfa", Format.getRandAllString(36));
			USERMAP.put("_osversion", "10.0.2");
			USERMAP.put("_platform", "iOS");
			USERMAP.put("_screen", "414*736");
			USERMAP.put("_time", "2016-10-17 18:21:33");
			USERMAP.put("_version", "3.0.0");
			USERMAP.put("coordinate", "116.427309,39.858895");
			USERMAP.put("date_type", "0");

			USERMAP.put("user_coordinate", "116.427160,39.858273");
			USERMAP.put("utoken", Format.getRandAllString(72));
		}
		
		return USERMAP;
	}
	
	public JSONObject post(final String url,final Map<String,String> ppost,final Map<String,String> phead) throws ClientProtocolException, IOException
	{
		JSONObject json = null;
		
		HashMap<String,String> post = new HashMap<String,String>();
		post.putAll(getUserMap());

		if (ppost != null)
		{
			post.putAll(ppost);
		}
		
		HashMap<String, String> head = new HashMap<String, String>();
//		head.put("User-Agent", "HomeCook/3.0.0 (iPhone; iOS 10.0.2; Scale/3.00)");
//		head.put("Content-Type", "application/x-www-form-urlencoded");
		
		if (phead != null)
		{
			head.putAll(phead);
		}
		//System.out.println(post);
		
		
		String result = client.postToString(url, post, head);
		
		json = JSONObject.fromObject(result);
		//System.out.println(json);
		
		return json;
	}
	
	/**
	 * 获取附近厨房列表
	 * @param coordinate 位置如116.427309,39.858895
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public JSONObject kitchenList(String coordinate) throws ClientProtocolException, IOException
	{
		HashMap<String, String> head = new HashMap<String, String>();
		head.put("coordinate", coordinate);
		head.put("size", "10000");

		return post("http://user.mapi.jiashuangkuaizi.com/Kitchen/kitchenList", head, null);
	}
	
	/**
	 * 获取菜系
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public JSONObject getKitchenSticker() throws ClientProtocolException, IOException
	{
		return post("http://user.mapi.jiashuangkuaizi.com/Kitchen/getKitchenSticker", null, null);
	}
	
	public JSONObject dishList(String kitchen_id) throws ClientProtocolException, IOException
	{
		HashMap<String, String> post = new HashMap<String, String>();
		post.put("kitchen_id", kitchen_id);
		
		return post("http://user.mapi.jiashuangkuaizi.com/Kitchen/dishList", post, null);
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException
	{		
		CookHome c = new CookHome();
		JSONObject json = c.kitchenList("116.427309,39.858895");
		
		JSONArray arr = json.getJSONObject("data").getJSONArray("list");
		
		StringBuffer buf = new StringBuffer();
		buf.append(String.format("名称\t地址\t哪里人\t菜名\t价格\t销售数量\n"));
		for (int i=0,len=arr.size();i<len;i++)
		{
			JSONObject k = arr.getJSONObject(i);
			String name = k.getString("kitchen_name");
			String id = k.getString("kitchen_id");
			String addr = k.getString("kitchen_address");
			String nativepop = k.getString("native_place");
			
			//System.out.println(k);
			
			JSONObject ds = c.dishList(id);
			JSONArray darr = ds.getJSONObject("data").getJSONArray("recommends");
			
			for (int j=0,jlen=darr.size();j<jlen;j++)
			{
				JSONObject d = darr.getJSONObject(j);
				
				//System.out.println(d);
				buf.append(String.format("%s\t%s\t%s\t%s\t%s\t%s\n", name,addr,nativepop,d.getString("dish_name"),d.getString("price"),d.getString("eat_num")));
			}
			
		}
		System.out.println(buf.toString());
		
		
//		System.out.println(c.getKitchenSticker());
//		System.out.println(c.dishList("23050"));

	}

}
