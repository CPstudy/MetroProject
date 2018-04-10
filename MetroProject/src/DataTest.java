import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataTest {
	String str_line_table;
	ArrayList<StationList> arr = new ArrayList<>();
	
	public DataTest(String str) {
		this.str_line_table = str;
		String url = "http://guide94.cafe24.com/json2/";
		BufferedReader br = null;
		BufferedReader in = null;
        
        try {
			//��� ��ȯ
			String url_encode = URLEncoder.encode(str, "UTF-8");
			try {
				URL obj = new URL(url + url_encode + ".json");
				HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);

				conn.setRequestMethod("GET");

				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				System.out.println(response.toString());
				in.close();

				//�ǽð� ��ġ������ �����Ѵ�
				JSONParser js = new JSONParser();
				JSONArray jsonArr = (JSONArray) js.parse(response.toString());

				//���� �ǽð� ��ġ������ ������ �����Ѵ�  
				for (int i = 0; i < jsonArr.size(); i++) {
					String hobby = jsonArr.get(i).toString();
					JSONObject jj = (JSONObject) js.parse(hobby);
					arr.add(new StationList(
							jj.get("code").toString(),
							jj.get("name").toString(),
							Integer.parseInt(jj.get("type").toString()),
							Integer.parseInt(jj.get("express").toString()),
							jj.get("html").toString(),
							Integer.parseInt(jj.get("size").toString()),
							Integer.parseInt(jj.get("x").toString()),
							Integer.parseInt(jj.get("y").toString()),
							Integer.parseInt(jj.get("train").toString())));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
