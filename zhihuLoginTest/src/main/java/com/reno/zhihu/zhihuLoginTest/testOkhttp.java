package com.reno.zhihu.zhihuLoginTest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class testOkhttp {
	
	public static CookieManager cookieManager;
	
	
	private static OkHttpClient client ;
	
	static{
		//cookieManager = new CookieManager();
		//cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		//client=new OkHttpClient.Builder()
		//	    .cookieJar(new JavaNetCookieJar(cookieManager))
		//	    .build();
		client= new OkHttpClient.Builder()
		    .cookieJar(new CookieJar() {
		        private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<HttpUrl, List<Cookie>>();

		        
		        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
		            cookieStore.put(url, cookies);
		        }

		       
		        public List<Cookie> loadForRequest(HttpUrl url) {
		            List<Cookie> cookies = cookieStore.get(url);
		            return cookies != null ? cookies : new ArrayList<Cookie>();
		        }
		    })
		    .build();
	}
	
	
	
	
	
	
	private static String xsrf;

	public static void login(String randCode, String email, String password)
			throws IOException {
		FormBody.Builder builder = new FormBody.Builder();
		builder.add("_xsrf", xsrf)
		.add("captcha", randCode)
		.add("email", email)
		.add("password", password)
		.add("remember_me", "true")
		.build();


		RequestBody formBody = builder.build();

		Request login = new Request.Builder()
				.url("https://www.zhihu.com/login/email")
				.post(formBody)
				.addHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36")
				.build();

		System.out.println(login + "   " + randCode.length());
		Response execute = client.newCall(login).execute();
		System.out.println( execute.header("Set-Cookie") + "        execute   cookie");
		// System.out.println(execute.networkResponse());

		System.out.println(decode(execute.body().string()));

	}

	public static void loginWithcookie(String email, String password) throws IOException {
		FormBody.Builder builder = new FormBody.Builder();
		builder.add("_xsrf", xsrf)
				.add("email", email)
				.add("password", password)
				.add("remember_me", "true")
				.add("Cookie",
						"l_n_c=1; q_c1=1299d7a0cbce4cd39cf5ba1929841bdf|1483775850000|1483775850000; _xsrf=c1211f9802f29b3a999d5a7d9578d609; cap_id=\"NDk3NzEwNmE2MjVmNDdiMDkyMTg5Y2RlN2I0YThlMTU=|1483775850|5e7fc8752a0ba8910c042311626aaf646d533aa7\"; l_cap_id=\"ZDE1MzVlMDhlOTA1NDE0ZmExMTllZDE5ZmQwZjM0MDA=|1483775850|fc54daffeb24240ef0281906936e8379d8658a53\"; d_c0=\"AIDCxlkPHguPToVq6B2O3LMI4ogAzRWSKx8=|1483775851\"; _zap=1b0a352a-cb32-4e22-8eee-c55a9ae4329b; __utma=51854390.890767438.1483775853.1483775853.1483775853.1; __utmb=51854390.0.10.1483775853; __utmc=51854390; __utmz=51854390.1483775853.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmv=51854390.000--|3=entry_date=20170107=1; r_cap_id=\"Yjc2MDlkZWU5NTNhNDRhZTg0ZTNhZDlkNDVhZjBkNDY=|1483775851|20afc492b194511cae21b3035a9dce17ab97428e\"; login=\"ZTg0OTg2N2U5MjlmNGYyY2E5YmE0NTQwNDA1ZTllNzg=|1483775873|9a7620476646a4c480d230cdd5654519042096e2\"; z_c0=Mi4wQUFDQTN2b1pBQUFBZ01MR1dROGVDeGNBQUFCaEFsVk5aeTJZV0FDNmtHRnJWX0pHTndSMlhCRHBHczFOSkliWVNR|1483776103|a615f4dc31303a793f8d8cc118d26edc65b52870")
				.build();

		RequestBody formBody = builder.build();

		Request login = new Request.Builder()
				.url("https://www.zhihu.com/login/email")
				.post(formBody)
				.addHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36") 
				.build();
		
		Response execute = client.newCall(login).execute();

		// System.out.println(execute.networkResponse());

		System.out.println(decode(execute.body().string()));
	}

	public static void getCode() throws IOException {
		Request request = new Request.Builder()
				.url("https://www.zhihu.com/#signin")
				.addHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36")
				.build();
		Response response = client.newCall(request).execute();
		
		System.out.println( response.header("Set-Cookie") + "           cookie");
		
		
		String result = response.body().string();
		Document parse = Jsoup.parse(result);

		result = parse.select("input[type=hidden]").get(0).attr("value").trim();
		xsrf = result;
		System.out.println("_xsrf:" + result);
		String codeUrl = "https://www.zhihu.com/captcha.gif?r=";
		codeUrl += System.currentTimeMillis()+"&type=login";
		// codeUrl +=" &type=login&lang=cn";
		System.out.println("codeUrl:" + codeUrl);
		Request getcode = new Request.Builder()
				.url(codeUrl)
				.addHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36")
				.build();

		Response code = client.newCall(getcode).execute();
		System.out.println( code.header("Set-Cookie") + "         code  cookie");
		byte[] bytes = code.body().bytes();
		saveCode(bytes, "code.png");
		
	}

	public static void saveCode(byte[] bfile, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			file = new File(fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static String decode(String unicodeStr) {
		if (unicodeStr == null) {
			return null;
		}
		StringBuffer retBuf = new StringBuffer();
		int maxLoop = unicodeStr.length();
		for (int i = 0; i < maxLoop; i++) {
			if (unicodeStr.charAt(i) == '\\') {
				if ((i < maxLoop - 5)
						&& ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
								.charAt(i + 1) == 'U')))
					try {
						retBuf.append((char) Integer.parseInt(
								unicodeStr.substring(i + 2, i + 6), 16));
						i += 5;
					} catch (NumberFormatException localNumberFormatException) {
						retBuf.append(unicodeStr.charAt(i));
					}
				else
					retBuf.append(unicodeStr.charAt(i));
			} else {
				retBuf.append(unicodeStr.charAt(i));
			}
		}
		return retBuf.toString();
	}

	// {"img_size":[200,44],"input_points":[[61.2,25.1],[102.2,30.1]]}

	public static void main(String[] args) {
		try {
			
			getCode();
			//loginWithcookie("qxl19920213@1633.com", "88947635qxl");
			System.out.println(xsrf);
			Scanner sc = new Scanner(System.in);
			String code = sc.next();
			System.out.println(code);
			login(code, "qxl19920213@1633.com", "88947635qxl");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
