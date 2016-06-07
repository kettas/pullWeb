package cn.kettas.pull.w3school;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
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
			        
					};
	private static String getHtml(String proDir,String url,String codeType,boolean deep,String fileName)throws Exception{
		String srcHtml=FileUtils.readURLToString(new URL(url),codeType);
		Document jd=Jsoup.parse(srcHtml, "http://www.w3school.com.cn");
		List list=jd.select("link");
		//去除广告，及其脚本
		for(int i=0;list!=null&&i<list.size();i++){
			Element e=(Element)list.get(i);
			String[]tmp=e.attr("href").split("\\/");
			e.attr("href","../"+tmp[tmp.length-1]);
		}
		list=jd.select("script");
		for(int i=0;list!=null&&i<list.size();i++){
			Element e=(Element)list.get(i);
			e.remove();
		}
		list=jd.select("#course ul");
		for(int k=0;list!=null&&k<list.size();k++){
			Element e2=(Element)list.get(k);
			if(k==0){
				List a=e2.select("a");
				for(int i=0;a!=null&&i<a.size();i++){
					Element e=(Element)a.get(i);
					String text=StringEscapeUtils.escapeHtml3(e.text());
					String targetFile=text+".html";
					System.err.println(targetFile);
					String src=e.absUrl("href");
					e.attr("href",targetFile);
					if(deep){
						getHtml(proDir,src, codeType, false,targetFile);
					}
				}
			}else{
				List a=e2.select("a");
				for(int i=0;a!=null&&i<a.size();i++){
					Element e=(Element)a.get(i);
					String text=StringEscapeUtils.escapeHtml3(e.text());
					String targetFile=text+".html";
					System.err.println(targetFile);
					e.attr("href",targetFile);
				}
			}
		}
		FileUtils.writeStringToFile(new File(proDir,fileName), jd.html().toString(),codeType);
		return "";
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String proDir="C:\\Users\\Administrator\\Desktop\\pull\\JavaScript";
		String html=getHtml(proDir,"http://www.w3school.com.cn/b.asp", codeType,true,"index.html");
	}

}
