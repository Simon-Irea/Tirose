package org.tirose.core.tool.util;

import org.tirose.core.tool.constant.StringPool;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ObjectUtil {

	/**
	 * 判断空对象 object、map、list、set、字符串、数组
	 *
	 * @param obj the object to check
	 * @return 数组是否为空
	 */
	public static boolean isEmpty(@Nullable Object obj) {
		return ObjectUtils.isEmpty(obj);
	}

	/**
	 * 字符串转 long，为空则返回0
	 *
	 * <pre>
	 *   $.toLong(null) = 0L
	 *   $.toLong("")   = 0L
	 *   $.toLong("1")  = 1L
	 * </pre>
	 *
	 * @param str the string to convert, may be null
	 * @return the long represented by the string, or <code>0</code> if
	 * conversion fails
	 */
	public static long toLong(final Object str) {
		return NumberUtil.toLong(String.valueOf(str));
	}
	/**
	 * 判断是否为空字符串
	 * <pre class="code">
	 * $.isBlank(null)		= true
	 * $.isBlank("")		= true
	 * $.isBlank(" ")		= true
	 * $.isBlank("12345")	= false
	 * $.isBlank(" 12345 ")	= false
	 * </pre>
	 *
	 * @param cs the {@code CharSequence} to check (may be {@code null})
	 * @return {@code true} if the {@code CharSequence} is not {@code null},
	 * its length is greater than 0, and it does not contain whitespace only
	 * @see Character#isWhitespace
	 */
	public static boolean isBlank(@Nullable final CharSequence cs) {
		return StringUtil.isBlank(cs);
	}

	/**
	 * 比较两个对象是否相等。<br>
	 * 相同的条件有两个，满足其一即可：<br>
	 *
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @return 是否相等
	 */
	public static boolean equals(Object obj1, Object obj2) {
		return Objects.equals(obj1, obj2);
	}

	/**
	 * 判断不为空字符串
	 * <pre>
	 * $.isNotBlank(null)	= false
	 * $.isNotBlank("")		= false
	 * $.isNotBlank(" ")	= false
	 * $.isNotBlank("bob")	= true
	 * $.isNotBlank("  bob  ") = true
	 * </pre>
	 *
	 * @param cs the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is
	 * not empty and not null and not whitespace
	 * @see Character#isWhitespace
	 */
	public static boolean isNotBlank(@Nullable final CharSequence cs) {
		return StringUtil.isNotBlank(cs);
	}

	/**
	 * 判断数组为空
	 *
	 * @param array the array to check
	 * @return 数组是否为空
	 */
	public static boolean isEmpty(@Nullable Object[] array) {
		return ObjectUtils.isEmpty(array);
	}

	/**
	 * 判断元素不为空
	 * @param obj object
	 * @return boolean
	 */
	public static boolean isNotEmpty(@Nullable Object obj) {
		return !ObjectUtils.isEmpty(obj);
	}


	/**
	 * 转换为String数组<br>
	 *
	 * @param str 被转换的值
	 * @return 结果
	 */
	public static String[] toStrArray(String str) {
		return toStrArray(",", str);
	}


	/**
	 * 转换为Long数组<br>
	 *
	 * @param str 被转换的值
	 * @return 结果
	 */
	public static Long[] toLongArray(String str) {
		return toLongArray(",", str);
	}

	/**
	 * 转换为Long数组<br>
	 *
	 * @param split 分隔符
	 * @param str   被转换的值
	 * @return 结果
	 */
	public static Long[] toLongArray(String split, String str) {
		if (StringUtil.isEmpty(str)) {
			return new Long[]{};
		}
		String[] arr = str.split(split);
		final Long[] longs = new Long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			final Long v = toLong(arr[i], 0);
			longs[i] = v;
		}
		return longs;
	}

	/**
	 * 字符串转 long，为空则返回默认值
	 *
	 * <pre>
	 *   $.toLong(null, 1L) = 1L
	 *   $.toLong("", 1L)   = 1L
	 *   $.toLong("1", 0L)  = 1L
	 * </pre>
	 *
	 * @param str          the string to convert, may be null
	 * @param defaultValue the default value
	 * @return the long represented by the string, or the default if conversion fails
	 */
	public static long toLong(@Nullable final Object str, final long defaultValue) {
		return NumberUtil.toLong(String.valueOf(str), defaultValue);
	}

	/**
	 * 转换为Long集合<br>
	 *
	 * @param str 结果被转换的值
	 * @return 结果
	 */
	public static List<Long> toLongList(String str) {
		return Arrays.asList(toLongArray(str));
	}

	/**
	 * 转换为Long集合<br>
	 *
	 * @param split 分隔符
	 * @param str   被转换的值
	 * @return 结果
	 */
	public static List<Long> toLongList(String split, String str) {
		return Arrays.asList(toLongArray(split, str));
	}


	/**
	 * 转换为String数组<br>
	 *
	 * @param split 分隔符
	 * @param str   被转换的值
	 * @return 结果
	 */
	public static String[] toStrArray(String split, String str) {
		if (isBlank(str)) {
			return new String[]{};
		}
		return str.split(split);
	}

	/**
	 * 字符串转 int，为空则返回默认值
	 *
	 * <pre>
	 *   $.toInt(null, 1) = 1
	 *   $.toInt("", 1)   = 1
	 *   $.toInt("1", 0)  = 1
	 * </pre>
	 *
	 * @param str          the string to convert, may be null
	 * @param defaultValue the default value
	 * @return the int represented by the string, or the default if conversion fails
	 */
	public static int toInt(@Nullable final Object str, final int defaultValue) {
		return NumberUtil.toInt(String.valueOf(str), defaultValue);
	}

	/**
	 * 强转string,并去掉多余空格
	 *
	 * @param str          字符串
	 * @param defaultValue 默认值
	 * @return String
	 */
	public static String toStr(Object str, String defaultValue) {
		if (null == str || str.equals(StringPool.NULL)) {
			return defaultValue;
		}
		return String.valueOf(str);
	}

	/**
	 * 对象组中是否存在 Empty Object
	 *
	 * @param os 对象组
	 * @return boolean
	 */
	public static boolean hasEmpty(Object... os) {
		for (Object o : os) {
			if (isEmpty(o)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 强转string,并去掉多余空格
	 *
	 * @param str 字符串
	 * @return String
	 */
	public static String toStr(Object str) {
		return toStr(str, "");
	}

}
