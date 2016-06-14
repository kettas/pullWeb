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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.mysql.jdbc.log.Log;

import cn.kettas.pull.FileUtils;
import cn.kettas.pull.HZPY;

/**
 * http://www.w3school.com.cn/js/index.asp
 * @author kettas
 */
public class ScriptApplication {
	static String codeType="GB2312";
	static String SYSDIR="C:\\Users\\Administrator\\Desktop\\W3School\\";
	static Map saveFileMap=new HashMap();
	
	private static String genHtml(List<Bean> item,String codeType) throws Exception{
		Map linkTemp=new HashMap();
		for(int i=0;item!=null&&i<item.size();i++){
			Bean b=item.get(i);
			linkTemp.put(b.getLink(), (b.getDirName().length()<1?"":"../"+b.getDirName()+"/")+b.getSaveFileName());
		}
		for(int i=0;item!=null&&i<item.size();i++){
			Bean b=item.get(i);
			getHtml(b.getDirName(),b.getLink(),codeType,0,b.getSaveFileName(),linkTemp);
		}
		return null;
	}
	
	private static String readURL(String url) throws MalformedURLException, IOException, SQLException{
		String srcHtml=(String)new DbUtils().queryToBean(String.class, "select body from pabody where url=?",new Object[]{url});
		if(srcHtml==null||srcHtml==""){
			try{
				System.err.println(url);
				srcHtml=FileUtils.readURLToString(new URL(url),codeType,5000);
				String sql="insert into pabody(url,body,createDate) values(?,?,?)";
				new DbUtils().executeUpdate(sql,new Object[]{url,srcHtml,new SimpleDateFormat("yyyy-MM-dd").format(new Date())});
			}catch(Exception x){
			}
		}
		return srcHtml;
	}
	/**
	 * @param menu
	 * @return
	 */
	private static Document genMenu(Document doc,Map hrefMap,String dirName){
		List list=doc.select("#menu li");
		for(int i=0;list!=null&&i<list.size();i++){
			Element a=(Element)list.get(i);
			List as=a.select("a");
			for(int j=0;as!=null&&j<as.size();j++){
				Element aHref=(Element)as.get(j);
				String href=aHref.absUrl("href");
				if(hrefMap.containsKey(href)){
					href=hrefMap.get(href).toString();
				}
				aHref.attr("href",href);
			}
		}
		return doc;
	}
	private static String formateHref(String srcHref){
		while(srcHref.indexOf("/")>-1){
			srcHref=srcHref.replaceAll("/", "╱");
		}
		while(srcHref.indexOf("?")>-1){
			srcHref=srcHref.replaceAll("?", "╱");
		}
		while(srcHref.indexOf("\\")>-1){
			srcHref=srcHref.replaceAll("\\", "﹨");
		}
		while(srcHref.indexOf("<")>-1){
			srcHref=srcHref.replaceAll("<", "＜");
		}
		while(srcHref.indexOf(">")>-1){
			srcHref=srcHref.replaceAll(">", "＞");
		}
		while(srcHref.indexOf(" ")>-1){
			srcHref=srcHref.replaceAll(" ", "");
		}
		try {
			return HZPY.toPingYin2(srcHref);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return srcHref;
	}
	
	private static String getHtml(String proDir,String url,String codeType,int deepTime,String fileName,Map linkMap)throws Exception{
		if(saveFileMap.containsKey(url)){
			return null;
		}
		saveFileMap.put(url, null);
		String srcHtml=readURL(url);
		if(srcHtml==null||srcHtml.length()<1){
			return null;
		}
		Document jd=Jsoup.parse(srcHtml, "http://www.w3school.com.cn");
		genMenu(jd, linkMap,"../"+proDir+"/"+fileName);
		List list=jd.select("link");
		//去除广告，及其脚本
		for(int i=0;list!=null&&i<list.size();i++){
			Element e=(Element)list.get(i);
			String href=e.attr("href");
			String[]tmp=href.split("\\/");
			e.attr("href","../"+tmp[tmp.length-1]);
		}
		jd.select("script").remove();
		list=jd.select("#navsecond ul");
		//缓存地址
		for(int k=0;list!=null&&k<list.size();k++){
			Element e2=(Element)list.get(k);
			if(k>=0){
				List a=e2.select("a");
				for(int i=0;a!=null&&i<a.size();i++){
					Element e=(Element)a.get(i);
					String href=e.attr("href");
					String href3=e.absUrl("href");
					readURL(href3);
					String text=formateHref(e.text());
					String targetFile=text+".html";
					linkMap.put(href, targetFile);
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
				String text=formateHref(e.text());
				String targetFile=linkMap.containsKey(href)?linkMap.get(href).toString():text+".html";
				e.attr("href2",e.attr("href"));
				e.attr("href",targetFile);
			}
		}
		list=jd.select("#navsecond a");
		for(int k=0;list!=null&&k<list.size();k++){
			Element e=(Element)list.get(k);
			String href=e.text();
			String text=formateHref(e.text());
			String targetFile=linkMap.containsKey(href)?linkMap.get(href).toString():text+".html";
			String src=e.absUrl("href");
			e.attr("href",targetFile);
			if(deepTime<4&&!url.equals(e.absUrl("href2"))){
				getHtml(proDir,e.absUrl("href2"), codeType, deepTime+1,targetFile,linkMap);
			}
		}
		List a1=jd.select("a");
		//更新页面所有地址
		for(int j=0;a1!=null&&j<a1.size();j++){
			Element e3=(Element)a1.get(j);
			if(linkMap.containsKey(e3.attr("href"))){
				e3.attr("href",linkMap.get(e3.attr("href")).toString());
			}
		}
		System.err.println(url+"\t"+proDir+"/"+fileName);
		FileUtils.writeStringToFile(new File(SYSDIR+proDir,fileName), jd.html(),codeType);
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
		item.add(new Bean("Html","http://www.w3school.com.cn/h.asp","index.html"));
		item.add(new Bean("JavaScript","http://www.w3school.com.cn/b.asp","index.html"));
//		item.add(new Bean("JavaScript","http://www.w3school.com.cn/jquerymobile/index.asp","jquery_index.html"));
		item.add(new Bean("Server Side","http://www.w3school.com.cn/s.asp","index.html"));
		
		item.add(new Bean("ASP.NET","http://www.w3school.com.cn/d.asp","index.html"));
		item.add(new Bean("XML","http://www.w3school.com.cn/x.asp", "index.html"));
		item.add(new Bean("Web Services","http://www.w3school.com.cn/ws.asp", "index.html"));
		item.add(new Bean("Web Building","http://www.w3school.com.cn/w.asp", "index.html"));
//		item.add(new Bean("","http://www.w3school.com.cn", "index.html"));
		
//		item.add(new Bean("jQuery","http://www.w3school.com.cn/jquery/index.asp","jQuery.html"));
//		item.add(new Bean("AJAX","http://www.w3school.com.cn/ajax/index.asp", "AJAX.html"));
//		item.add(new Bean("JSON","http://www.w3school.com.cn/json/index.asp", "JSON.html"));
//		item.add(new Bean("css","http://www.w3school.com.cn/css/index.asp", "css.html"));
//		item.add(new Bean("css3","http://www.w3school.com.cn/css3/index.asp", "css.html"));
//		item.add(new Bean("html5","http://www.w3school.com.cn/html5/index.asp", "index.html"));
//		item.add(new Bean("xml","http://www.w3school.com.cn/xml/index.asp", "index.html"));
//		item.add(new Bean("xpath","http://www.w3school.com.cn/xpath/index.asp", "index.html"));
//		item.add(new Bean("xsl","http://www.w3school.com.cn/xsl/index.asp", "index.html"));
//		
//		item.add(new Bean("Server Side","http://www.w3school.com.cn/s.asp", "index.html"));
//		item.add(new Bean("Server Side","http://www.w3school.com.cn/sql/index.asp", "sql_index.html"));
		genHtml(item, codeType);
	}

}
