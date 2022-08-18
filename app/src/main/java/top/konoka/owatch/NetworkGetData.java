package top.konoka.owatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkGetData {
    URL url;
    HttpsURLConnection httpsURLConnection;

    public NetworkGetData(String urlStr) {
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getHtmlData() {
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        try {
            try {
                reConfigHttpsURLConnection();
                //getInputStream()隐式调用connect()
                inputStream = httpsURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder returnValue = new StringBuilder();
                String data;
                while ((data = bufferedReader.readLine()) != null) {
                    returnValue.append(data);
                }
                return returnValue.toString();
            } finally {
                //Keep-Alive时关闭输入流不会disconnect()连接
                //https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/net/HttpURLConnection.html
                //每个HttpURLConnection实例用于发出单个请求，但是与HTTP服务器的基础网络连接可以由其他实例透明地共享。
                //在请求之后调用HttpURLConnection的InputStream或OutputStream上的close()方法可以释放与此实例关联的网络资源，
                // 但不会影响任何共享持久连接。
                //如果此时持久连接处于空闲状态，则调用disconnect()方法可能会关闭底层套接字。
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void reConfigHttpsURLConnection() throws IOException {
        httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setConnectTimeout(5000);
        httpsURLConnection.setReadTimeout(5000);
        httpsURLConnection.setRequestMethod("GET");
        httpsURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpsURLConnection.setRequestProperty("User-Agent", "OWatch/1.0");
    }
}
