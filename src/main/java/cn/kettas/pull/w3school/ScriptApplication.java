package cn.kettas.pull.w3school;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
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
	
	private static String genHtml(List<Bean> item,String proDir,String codeType,boolean deep) throws Exception{
		Map linkTemp=new HashMap();
		for(int i=0;item!=null&&i<item.size();i++){
			Bean b=item.get(i);
			linkTemp.put(b.getLink(), b.getSaveFileName());
		}
		for(int i=0;item!=null&&i<item.size();i++){
			Bean b=item.get(i);
			getHtml("C:\\Users\\Administrator\\Desktop\\pull\\"+b.getDirName(),b.getLink(),codeType,deep,b.getSaveFileName(),linkTemp);
		}
		return null;
	}
	
	private static String readURL(String url) throws MalformedURLException, IOException, SQLException{
		String srcHtml=(String)new DbUtils().queryToBean(String.class, "select body from pabody where url=?",new Object[]{url});
		if(srcHtml==null||srcHtml==""){
			try{
				srcHtml=FileUtils.readURLToString(new URL(url),codeType,5000);
				String sql="insert into pabody(url,body,createDate) values(?,?,?)";
				new DbUtils().executeUpdate(sql,new Object[]{url,srcHtml,new SimpleDateFormat("yyyy-MM-dd").format(new Date())});
			}catch(Exception x){
			}
		}
		return srcHtml;
	}
	
	private static String getHtml(String proDir,String url,String codeType,boolean deep,String fileName,Map linkMap)throws Exception{
		String srcHtml=readURL(url);
		if(srcHtml==null||srcHtml.length()<1){
			return null;
		}
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
		for(int k=0;list!=null&&k<list.size();k++){
			Element e2=(Element)list.get(k);
			if(k>=0){
				List a=e2.select("a");
				for(int i=0;a!=null&&i<a.size();i++){
					Element e=(Element)a.get(i);
					String href=e.attr("href");
					String href3=e.absUrl("href");
					String href2=e.text();
					String text=e.text().indexOf("<")>-1?StringEscapeUtils.escapeHtml(e.text()):e.text();
					while(text.indexOf("/")>-1){
						text=text.replaceAll("/", "_");
					}
					String targetFile=text+".html";
					System.err.println(targetFile);
					linkMap.put(href, targetFile);
					linkMap.put(href2, targetFile);
					linkMap.put(href3, targetFile);
				}
			}
		}
		for(int k=0;list!=null&&k<list.size();k++){
			Element e2=(Element)list.get(k);
			List a=e2.select("a");
			for(int i=0;a!=null&&i<a.size();i++){
				Element e=(Element)a.get(i);
				String href=e.text();
				String text=href.indexOf("<")>-1?StringEscapeUtils.escapeHtml(href):href;
				String targetFile=linkMap.containsKey(href)?linkMap.get(href).toString():text+".html";
				System.err.println(href+"\t"+targetFile);
				String src=e.absUrl("href");
				e.attr("href",targetFile);
				if(deep){
					getHtml(proDir,src, codeType, false,targetFile,linkMap);
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
	public static class Bean{
		private String dirName;
		private String link;
		private String saveFileName;
		public Bean(String dirName, String link, String saveFileName) {
			super();
			this.dirName = dirName;
			this.link = link;
			this.saveFileName = saveFileName;
		}
		public String getDirName() {
			return dirName;
		}
		public String getLink() {
			return link;
		}
		public String getSaveFileName() {
			return saveFileName;
		}
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		List<Bean> item=new ArrayList<Bean>();
//		item.add(new Bean("JavaScript","http://www.w3school.com.cn/b.asp","index.html"));
//		item.add(new Bean("JavaScript","http://www.w3school.com.cn/js/index.asp","JavaScript.html"));
//		
//		item.add(new Bean("HTML DOM","http://www.w3school.com.cn/htmldom/index.asp","HTML DOM.html"));
//		item.add(new Bean("HTML DOM","http://www.w3school.com.cn/jsref/index.asp","DOM 参考.html"));
//		
//		item.add(new Bean("jQuery","http://www.w3school.com.cn/jquery/index.asp","jQuery.html"));
//		item.add(new Bean("AJAX","http://www.w3school.com.cn/ajax/index.asp", "AJAX.html"));
//		item.add(new Bean("JSON","http://www.w3school.com.cn/json/index.asp", "JSON.html"));
//		item.add(new Bean("css","http://www.w3school.com.cn/css/index.asp", "css.html"));
//		item.add(new Bean("css3","http://www.w3school.com.cn/css3/index.asp", "css.html"));
//		item.add(new Bean("html5","http://www.w3school.com.cn/html5/index.asp", "index.html"));
//		item.add(new Bean("xml","http://www.w3school.com.cn/xml/index.asp", "index.html"));
//		item.add(new Bean("xpath","http://www.w3school.com.cn/xpath/index.asp", "index.html"));
		item.add(new Bean("xsl","http://www.w3school.com.cn/xsl/index.asp", "index.html"));
		
		item.add(new Bean("Server Side","http://www.w3school.com.cn/s.asp", "index.html"));
		item.add(new Bean("Server Side","http://www.w3school.com.cn/sql/index.asp", "sql_index.html"));
		genHtml(item,"C:\\Users\\Administrator\\Desktop\\pull\\", codeType,true);
	}

}
