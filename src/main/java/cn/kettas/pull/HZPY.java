package cn.kettas.pull;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
/**
 * 汉字、拼音相关操作
 * 3:14:07 PM
 */
public class HZPY {
	private String getHZFirstPY(String ss) {
		String sHEX = getHZNM(ss);
		String temp = "";
		if ((sHEX.compareTo("B0A1") >= 0) && (sHEX.compareTo("B0C4") <= 0)) {
			temp = "A";
		}
		if ((sHEX.compareTo("B0C5") >= 0) && (sHEX.compareTo("B2C0") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[B]";
			else
				temp = "B";
		}
		if ((sHEX.compareTo("B2C1") >= 0) && (sHEX.compareTo("B4ED") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[C]";
			else
				temp = "C";
		}
		if ((sHEX.compareTo("B4EE") >= 0) && (sHEX.compareTo("B6E9") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[D]";
			else
				temp = "D";
		}
		if ((sHEX.compareTo("B6EA") >= 0) && (sHEX.compareTo("B7A1") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[E]";
			else
				temp = "E";
		}
		if ((sHEX.compareTo("B7A2") >= 0) && (sHEX.compareTo("B8C0") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[F]";
			else
				temp = "F";
		}
		if ((sHEX.compareTo("B8C1") >= 0) && (sHEX.compareTo("B9FD") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[G]";
			else
				temp = "G";
		}
		if ((sHEX.compareTo("B9FE") >= 0) && (sHEX.compareTo("BBF6") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[H]";
			else
				temp = "H";
		}
		if ((sHEX.compareTo("BBF7") >= 0) && (sHEX.compareTo("BFA5") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[J]";
			else
				temp = "J";
		}
		if ((sHEX.compareTo("BFA6") >= 0) && (sHEX.compareTo("C0AB") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[K]";
			else
				temp = "K";
		}
		if ((sHEX.compareTo("C0AC") >= 0) && (sHEX.compareTo("C2E7") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[L]";
			else
				temp = "L";
		}
		if ((sHEX.compareTo("C2E8") >= 0) && (sHEX.compareTo("C4C2") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[M]";
			else
				temp = "M";
		}
		if ((sHEX.compareTo("C4C3") >= 0) && (sHEX.compareTo("C5B5") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[N]";
			else
				temp = "N";
		}
		if ((sHEX.compareTo("C5B6") >= 0) && (sHEX.compareTo("C5BD") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[O]";
			else
				temp = "O";
		}
		if ((sHEX.compareTo("C5BE") >= 0) && (sHEX.compareTo("C6D9") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[P]";
			else
				temp = "P";
		}
		if ((sHEX.compareTo("C6DA") >= 0) && (sHEX.compareTo("C8BA") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[Q]";
			else
				temp = "Q";
		}
		if ((sHEX.compareTo("C8BB") >= 0) && (sHEX.compareTo("C8F5") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[R]";
			else
				temp = "R";
		}
		if ((sHEX.compareTo("C8F6") >= 0) && (sHEX.compareTo("CBF9") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[S]";
			else
				temp = "S";
		}
		if ((sHEX.compareTo("CBFA") >= 0) && (sHEX.compareTo("CDD9") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[T]";
			else
				temp = "T";
		}
		if ((sHEX.compareTo("CDDA") >= 0) && (sHEX.compareTo("CEF3") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[W]";
			else
				temp = "W";
		}
		if ((sHEX.compareTo("CEF4") >= 0) && (sHEX.compareTo("D1B8") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[X]";
			else
				temp = "X";
		}
		if ((sHEX.compareTo("D1B9") >= 0) && (sHEX.compareTo("D4D0") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[Y]";
			else
				temp = "Y";
		}
		if ((sHEX.compareTo("D4D1") >= 0) && (sHEX.compareTo("D7F9") <= 0)) {
			if (!temp.equals(""))
				temp = temp + "[Z]";
			else
				temp = "Z";
		}
		return temp;
	}

	private String getHZNM(String ss) {
		try {
			String hexStr0 = "";
			String hexStr1 = "";
			byte[] fb = new byte[2];
			fb = ss.getBytes("GBK");
			hexStr0 = Integer.toHexString(fb[0]);
			hexStr1 = Integer.toHexString(fb[1]);

			if ((hexStr0.length() < 8) || (hexStr1.length() < 8)) {
				return "";
			}
			return (hexStr0.substring(6, 8) + hexStr1.substring(6, 8))
					.toUpperCase();
		} catch (Exception es) {
		}
		return "";
	}

	public String outPutFirstPY(String ss) {
		String s = "";
		String sReturn = "";
		ss = ss.trim();
		for (int i = 0; i < ss.length(); i++) {
			s = ss.substring(i, i + 1);
			sReturn = sReturn + getHZFirstPY(s);
		}
		return sReturn;
	}
	public static String toPingYin2(String src)throws Exception{
		String[]args=src.split("|");
		StringBuilder text=new StringBuilder();
		for(int i=0;i<args.length;i++){
			if(i<=1){
				text.append(toPingYin(args[i]));
			}else{
				text.append(toPingYin(args[i]).toUpperCase().charAt(0));
			}
		}
		return SpecialCharactersFiter(text.toString());
	}
	/**
	 * 将中文转换为拼音(使用pinyin4j-2.5.0.jar及以上版本)
	 * @param cn 中文字符
	 * @throws Exception
	 */
	public static String toPingYin(String src) throws Exception{
		char[] t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				} else{
					t4 += java.lang.Character.toString(t1[i]);
				}
			}
			return t4;
		} catch (Exception e1) {
			return src;
		}
	}

	public static String toPinyinFirstAlphabet(String src)throws Exception{
		String[]args=src.split("|");
		StringBuilder text=new StringBuilder();
		for(int i=1;i<args.length;i++){
			char[] t1 = args[i].toCharArray();
			String[] t2 = new String[t1.length];
			HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
			t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			t3.setVCharType(HanyuPinyinVCharType.WITH_V);
			String t4 = "";
			int t0 = t1.length;
			try {
				for (int j = 0; j < t0; j++) {
					if (java.lang.Character.toString(t1[j]).matches("[\\u4E00-\\u9FA5]+")) {// 仅取汉字字符
						t2 = PinyinHelper.toHanyuPinyinStringArray(t1[j], t3);
						t4 += t2[0];
					} 
				}
			} catch (Exception e1) {
				t4 = src;
			}
			if(t4.length() > 0){
				text.append(t4.toUpperCase().charAt(0));
			}
		}
		return SpecialCharactersFiter(text.toString());
	}
	public static String SpecialCharactersFiter(String src){
		String rePath = src;
		try{
			rePath = rePath.replace("~", "");
			rePath = rePath.replace("！", "");
			rePath = rePath.replace("·", "");
			rePath = rePath.replace("#", "");
			rePath = rePath.replace("￥", "");
			rePath = rePath.replace("%", "");
			rePath = rePath.replace("…", "");
			rePath = rePath.replace("……", "");
			rePath = rePath.replace("—", "");
			rePath = rePath.replace("——", "");
			rePath = rePath.replace("*", "");
			rePath = rePath.replace("（", "(");
			rePath = rePath.replace("）", ")");
			rePath = rePath.replace("～", "");
			rePath = rePath.replace("＃", "");
			rePath = rePath.replace("％", "");
			rePath = rePath.replace("＊", "");
			rePath = rePath.replace("!", "");
			rePath = rePath.replace("@", "");
			rePath = rePath.replace("$", "");
			rePath = rePath.replace("%", "");
			rePath = rePath.replace("^", "");
			rePath = rePath.replace("&", "");
			rePath = rePath.replace("１", "");
			rePath = rePath.replace("２", "");
			rePath = rePath.replace("３", "");
			rePath = rePath.replace("４", "");
			rePath = rePath.replace("５", "");
			rePath = rePath.replace("６", "");
			rePath = rePath.replace("７", "");
			rePath = rePath.replace("８", "");
			rePath = rePath.replace("９", "");
			rePath = rePath.replace("０", "");
			rePath = rePath.replace("＋", "");
			rePath = rePath.replace("＝", "");
			rePath = rePath.replace("－", "");
			rePath = rePath.replace("|", "");
			rePath = rePath.replace(";", "");
			rePath = rePath.replace(":", "");
			rePath = rePath.replace("'", "");
			rePath = rePath.replace("\"", "");
			rePath = rePath.replace(",", "");
			rePath = rePath.replace(".", "");
			rePath = rePath.replace("{", "");
			rePath = rePath.replace("}", "");
			rePath = rePath.replace("[", "");
			rePath = rePath.replace("]", "");
			rePath = rePath.replace("，", "");
			rePath = rePath.replace("。", "");
			rePath = rePath.replace("、", "");
			rePath = rePath.replace("；", "");
			rePath = rePath.replace("‘", "");
			rePath = rePath.replace("：", "");
			rePath = rePath.replace("“", "");
			rePath = rePath.replace("●", "");
			rePath = rePath.replace("〖", "");
			rePath = rePath.replace("〗", "");
			rePath = rePath.replace("【", "");
			rePath = rePath.replace("】", "");
			rePath = rePath.replace("〔", "(");
			rePath = rePath.replace("〕", ")");
			rePath = rePath.replace(" ", "");
			rePath = rePath.replace("　", "");
		}catch(Exception e){
			rePath = src;
			e.printStackTrace();
		}
		return rePath;
	}
	public static void main(String...args){
		try {
			System.out.println(toPinyinFirstAlphabet("中国人民(*A1234,+_)！·＃……％％！·９８３５１重庆"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}