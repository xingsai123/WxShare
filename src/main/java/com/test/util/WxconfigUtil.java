package com.test.util;





import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.sf.json.JSONObject;
/*
 * author:weili;
 * create:2018/5/13
 * desc:微信js-sdk注入信息
  */

//  1、使用APPID和APPSecret获取access_token；
//
//  2、使用access_token获取jsapi_ticket ；
//
//  3、用时间戳、随机数、jsapi_ticket和要访问的url按照签名算法拼接字符串；
//
//  4、对第三步的字符串进行SHA1加密，得到签名


public class WxconfigUtil {


    //使用APPID和APPSecret获取access_token；

    public static String getAccessToken (String APPID,String APPSECRET) {
        String access_token = "";
        String grant_type = "client_credential";//获取access_token填写client_credential
        String AppId=APPID;//第三方用户唯一凭证
        String secret=APPSECRET;//第三方用户唯一凭证密钥，即appsecret
        //这个url链接地址和参数皆不能变
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+grant_type+"&appid="+AppId+"&secret="+secret;

        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            System.out.println("JSON字符串："+demoJson);
            access_token = demoJson.getString("access_token");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return access_token;
    }

    // 使用access_token获取jsapi_ticket ；

    public static String getTicket(String access_token) {
        String ticket ="";

        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token +"&type=jsapi";//这个url链接和参数不能变
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            System.out.println("JSON字符串："+demoJson);
            ticket = demoJson.getString("ticket");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;
    }

  // SHA1算法加密
  public static String SHA1(String decript) {
      try {
          MessageDigest digest = MessageDigest.getInstance("SHA-1");
          digest.update(decript.getBytes());
          byte messageDigest[] = digest.digest();
          // Create Hex String
          StringBuffer hexString = new StringBuffer();
          // 字节数组转换为 十六进制 数
          for (int i = 0; i < messageDigest.length; i++) {
              String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
              if (shaHex.length() < 2) {
                  hexString.append(0);
              }
              hexString.append(shaHex);
          }
          return hexString.toString();

      } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
      }
      return "";
  }


  //main方法测试
    public static void main(String[] args) {
//需要传入参数：APPID,APPSECRET(这两个参数可以写死),URL是动态的；
//输出参数：signnature,noncestr
         String APPID="wx246bae8548b676c6";
         String APPSECRET="62dbd3330ad2284b37677da9e6315903";

        //1、获取AccessToken
        String accessToken = getAccessToken(APPID,APPSECRET);

        //2、获取Ticket
        String jsapi_ticket = getTicket(accessToken);

        //3、时间戳和随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳

        System.out.println("accessToken:"+accessToken+"\njsapi_ticket:"+jsapi_ticket+"\n时间戳："+timestamp+"\n随机字符串："+noncestr);

        //4、获取url
        String url="http://www.maomi.xn--fiqs8s/getDetails/1524409397585";

   /*  String[] ArrTmp = {"jsapi_ticket","timestamp","nonce","url"};
    Arrays.sort(ArrTmp);
    StringBuffer sf = new StringBuffer();
    for(int i=0;i<ArrTmp.length;i++){
        sf.append(ArrTmp[i]);
    }*/


        //5、将参数排序并拼接字符串
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;

        //6、将字符串进行sha1加密
        String signature =SHA1(str);
        System.out.println("参数："+str+"\n签名："+signature);
    }


    //signature接口
    public static Map<String,String> getWxInfo(String url1){

        //APPID,APPSECRET(这两个参数可以写死),URL是动态的；
        //输出参数：signnature,noncestr
        String APPID="wx246bae8548b676c6";
        String APPSECRET="62dbd3330ad2284b37677da9e6315903";

        //1、获取AccessToken，AccessToken有次数限制，这个后面再通过redis缓存处理；

        String accessToken = getAccessToken(APPID,APPSECRET);

        //2、获取Ticket
        String jsapi_ticket = getTicket(accessToken);

        //3、时间戳和随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳

        System.out.println("accessToken:"+accessToken+"\njsapi_ticket:"+jsapi_ticket+"\n时间戳："+timestamp+"\n随机字符串："+noncestr);

        //4、获取url
        String url=url1;


        //5、将参数排序并拼接字符串
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;

        //6、将字符串进行sha1加密
        String signature =SHA1(str);
        System.out.println("参数："+str+"\n签名："+signature);

        //7.存储到map
        Map map=new HashMap();
        map.put("noncestr",noncestr);
        map.put("timestamp",timestamp);
        map.put("signature",signature);

        return map;
    }


}
