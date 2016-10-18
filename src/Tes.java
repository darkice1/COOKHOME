import java.io.IOException;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;

import easy.io.EHttpClient;

public class Tes
{

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, ClientProtocolException, IOException
	{

		EHttpClient client = new EHttpClient();
		HashMap<String, String> head = new HashMap<String, String>();
		head.put("User-Agent", "HomeCook/3.0.0 (iPhone; iOS 10.0.2; Scale/3.00)");
		head.put("Content-Type", "application/x-www-form-urlencoded");
		
		HashMap<String, String> post = new HashMap<String, String>();
		
		post.put("", "_cityid=110100&_device=68C1C625-F58C-4958-B912-78A0AA22430C&_idfa=E9E3EB8F-51C4-49DF-9C1E-6F70BDEF0C65&_osversion=10.0.2&_platform=iOS&_screen=414%2A736&_time=2016-10-17%2018%3A21%3A33&_version=3.0.0&coordinate=116.427309%2C39.858895&date_type=0&kitchen_id=23050&send_date=2016-10-17&user_coordinate=116.427160%2C39.858273&utoken=MTkxZDczYjI1MmM2ZWQ0ZWFmMjE2OTBkZGIyNjc1ODVfMTM5MTEwNzgxNjAjMTY4MzQwOA%3D%3D");

		
		String url = "http://user.mapi.jiashuangkuaizi.com/Kitchen/kitchenList";//附件饭店
		//String url = "http://user.mapi.jiashuangkuaizi.com/Kitchen/getKitchenSticker";// 菜系
		//String url = "http://user.mapi.jiashuangkuaizi.com/Kitchen/dishList";// 店

		
		String html = client.postToString(url, post, head);
		
		JSONObject json = JSONObject.fromObject(html);
		System.out.println(json);
		
		
	}
}
