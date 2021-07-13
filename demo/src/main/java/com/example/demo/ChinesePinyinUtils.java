package com.example.demo;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: ChinesePinyinUtil
 * Description: ChinesePinyin Util
 *
 * @version 1.0
 */
public class ChinesePinyinUtils {
    private static HanyuPinyinOutputFormat DEFAULT_FORMAT = new HanyuPinyinOutputFormat();

    private static Logger log = LoggerFactory.getLogger(ChinesePinyinUtils.class);

    static {
        DEFAULT_FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);      // 输出拼音全部小写
        DEFAULT_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   // 不带声调
        DEFAULT_FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * 将文字转为汉语拼音全拼
     *
     * @param chineseStr 待转换的中文
     */
    public static String toPinyin(String chineseStr) {
        char[] cl_chars = chineseStr.trim().toCharArray();

        StringBuilder builder = new StringBuilder();

        try {
            for (char c : cl_chars) {
                String str = String.valueOf(c);
                if (str.matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音
                    builder.append(PinyinHelper.toHanyuPinyinStringArray(c, DEFAULT_FORMAT)[0]);
                } else if (str.matches("[0-9]|[a-z]|[A-Z]")) { // 如果字符是数字、字母, 不变
                    builder.append(c);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            log.error("chinese pinyin spring error : {}", chineseStr);
        }

        return builder.toString();
    }

    /**
     * 获取各个汉字的首字母
     *
     * @param chineseStr 待转换的中文
     */
    public static String getFirstLetters(String chineseStr) throws Exception {
        char[] cl_chars = chineseStr.trim().toCharArray();

        StringBuilder builder = new StringBuilder();

        try {
            for (char c : cl_chars) {
                String str = String.valueOf(c);
                if (str.matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                    builder.append(PinyinHelper.toHanyuPinyinStringArray(c, DEFAULT_FORMAT)[0].substring(0, 1));
                } else if (str.matches("[0-9]|[a-z]|[A-Z]")) { // 如果字符是数字、字母, 不变
                    builder.append(c);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            log.error("chinese pinyin spring error : {}", chineseStr);
        }


        return builder.toString();
    }
}
