package cn.kettas.pull.w3school;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import cn.kettas.pull.FileUtils;

/**
 * http://www.w3school.com.cn/js/index.asp
 * @author kettas
 */
public class ScriptApplication {
	static String codeType="GB2312";
	private String []src=new String[]{
			        "JS 教程"
					};
	private static String getHtml(String proDir,String url,String codeType,boolean deep,String fileName)throws Exception{
		String srcHtml=FileUtils.readURLToString(new URL(url),codeType);
		Document jd=Jsoup.parse(srcHtml, "http://www.w3school.com.cn");
		List list=jd.select("link");
		//去除广告，及其脚本
		for(int i=0;list!=null&&i<list.size();i++){
			Element e=(Element)list.get(i);
			String href=e.attr("href");
			String[]tmp=href.split("\\/");
			e.attr("href","../"+tmp[tmp.length-1]);
		}
		list=jd.select("script");
		for(int i=0;list!=null&&i<list.size();i++){
			Element e=(Element)list.get(i);
			e.remove();
		}
		list=jd.select("#course ul");
		Map linkMap=new HashMap();
		for(int k=0;list!=null&&k<list.size();k++){
			Element e2=(Element)list.get(k);
			if(k>=0){
				List a=e2.select("a");
				for(int i=0;a!=null&&i<a.size();i++){
					Element e=(Element)a.get(i);
					String href=e.attr("href");
					String href2=e.text();
					String text=e.text().indexOf("<")>-1?StringEscapeUtils.escapeHtml(e.text()):e.text();
					while(text.indexOf("/")>-1){
						text=text.replaceAll("/", "_");
					}
					String targetFile=text+".html";
					System.err.println(targetFile);
					linkMap.put(href, targetFile);
					linkMap.put(href2, targetFile);
				}
			}
		}
/*		linkMap.put("参考手册目录", "参考手册目录.html");
		linkMap.put("jQuery 隐藏/显示", "jQuery 隐藏_显示.html");
		linkMap.put("jQuery Get/Post", "jQuery Get_Post");
		linkMap.put("AJAX ASP/PHP", "AJAX ASP_PHP");
*/		for(int k=0;list!=null&&k<list.size();k++){
			Element e2=(Element)list.get(k);
			List a=e2.select("a");
			for(int i=0;a!=null&&i<a.size();i++){
				Element e=(Element)a.get(i);
				String href=e.text();
				String text=href.indexOf("<")>-1?StringEscapeUtils.escapeHtml(href):href;
				String targetFile=linkMap.containsKey(href)?linkMap.get(href).toString():text+".html";
				System.err.println(targetFile);
				String src=e.absUrl("href");
				e.attr("href",targetFile);
				if(deep){
					getHtml(proDir,src, codeType, false,targetFile);
				}
			}
		}
		List a=jd.select("a");
		for(int i=0;a!=null&&i<a.size();i++){
			Element e=(Element)a.get(i);
			if(linkMap.containsKey(e.attr("href"))){
				e.attr("href",linkMap.get(e.attr("href")).toString());
			}
		}
		FileUtils.writeStringToFile(new File(proDir,fileName), jd.html().toString(),codeType);
		return "";
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//getHtml("C:\\Users\\Administrator\\Desktop\\pull\\JavaScript","http://www.w3school.com.cn/b.asp", codeType,true,"index.html");
		//getHtml("C:\\Users\\Administrator\\Desktop\\pull\\JavaScript","http://www.w3school.com.cn/js/index.asp", codeType,true,"JavaScript.html");
		//getHtml("C:\\Users\\Administrator\\Desktop\\pull\\HTML DOM","http://www.w3school.com.cn/htmldom/index.asp", codeType,true,"HTML DOM.html");
		//getHtml("C:\\Users\\Administrator\\Desktop\\pull\\HTML DOM","http://www.w3school.com.cn/jsref/index.asp", codeType,true,"DOM 参考.html");
		//getHtml("C:\\Users\\Administrator\\Desktop\\pull\\jQuery","http://www.w3school.com.cn/jquery/index.asp", codeType,true,"jQuery.html");
		//getHtml("C:\\Users\\Administrator\\Desktop\\pull\\AJAX","http://www.w3school.com.cn/ajax/index.asp", codeType,true,"AJAX.html");
		//getHtml("C:\\Users\\Administrator\\Desktop\\pull\\JSON","http://www.w3school.com.cn/json/index.asp", codeType,true,"JSON.html");
		//getHtml("C:\\Users\\Administrator\\Desktop\\pull\\css","http://www.w3school.com.cn/css/index.asp", codeType,true,"css.html");
		//getHtml("C:\\Users\\Administrator\\Desktop\\pull\\css3","http://www.w3school.com.cn/css3/index.asp", codeType,true,"css.html");
		//getHtml("C:\\Users\\Administrator\\Desktop\\pull\\html5","http://www.w3school.com.cn/html5/index.asp", codeType,true,"index.html");
		//getHtml("C:\\Users\\Administrator\\Desktop\\pull\\xml","http://www.w3school.com.cn/xml/index.asp", codeType,true,"index.html");
		getHtml("C:\\Users\\Administrator\\Desktop\\pull\\xpath","http://www.w3school.com.cn/xpath/index.asp", codeType,true,"index.html");
	}

}
