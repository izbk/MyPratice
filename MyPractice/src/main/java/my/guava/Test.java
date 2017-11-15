package my.guava;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;

public class Test {
	public static void main(String[] args) {
		// 判断字符串是否为空
		String s = "";
		System.out.println(Strings.isNullOrEmpty(s));

		// 获得两个字符串相同的前缀或者后缀
		String a = "com.jd.coo.Hello";
		String b = "com.jd.coo.Hi";
		String c = "com.google.Hello";
		String d = "com.jd.Hello";
		String ourCommonPrefix = Strings.commonPrefix(a, b);
		String ourCommonSuffix = Strings.commonSuffix(c, d);
		System.out.println(ourCommonPrefix);
		System.out.println(ourCommonSuffix);

		// 补全字符串
		int minLength = 4;
		System.out.println(Strings.padEnd("123", minLength, '0'));
		System.out.println(Strings.padStart("123", minLength, '0'));

		// Splitter类可以方便的根据正则表达式来拆分字符串
		Iterable<String> splitResults = Splitter.onPattern("[,，]{1,}").trimResults().omitEmptyStrings()
				.split("hello,word,,世界，和平");
		for (String item : splitResults) {
			System.out.println(item);
		}
		String toSplitString = "a=b;c=d,e=f";
		Map<String, String> kvs = Splitter.onPattern("[,;]{1,}").withKeyValueSeparator('=').split(toSplitString);
		for (Map.Entry<String, String> entry : kvs.entrySet()) {
			System.out.println(String.format("%s=%s", entry.getKey(), entry.getValue()));
		}
		// Joiner类来做字符串的合并
		String joinResult = Joiner.on(" ").join(new String[] { "hello", "world" });
		System.out.println(joinResult);

		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "b");
		map.put("c", "d");
		String mapJoinResult = Joiner.on(",").withKeyValueSeparator("=").join(map);
		System.out.println(mapJoinResult);
		
		//不可变集合
		Set<String> immutableNamedColors = ImmutableSet.<String>builder()
                .add("red", "green","black","white","grey")
                .build();
        //immutableNamedColors.add("abc");
        for (String color : immutableNamedColors) {
            System.out.println(color);
        }
        ImmutableSet.of("red","green","black","white","grey");
        ImmutableSet.copyOf(new String[]{"red","green","black","white","grey"});


	}

	public void doSomething(String name, int age, String desc) {
		Preconditions.checkNotNull(name, "name may not be null");
		Preconditions.checkArgument(age >= 18 && age < 99, "age must in range (18,99)");
		Preconditions.checkArgument(desc != null && desc.length() < 10, "desc too long, max length is ", 10);
		// do things
	}
	public void doIt() throws SQLException {
        try {
            // do someThing
        } catch (Throwable throwable) {
        	Throwables.getRootCause(throwable);
        	Throwables.getCausalChain(throwable);
        	Throwables.getStackTraceAsString(throwable);
            Throwables.throwIfInstanceOf(throwable, SQLException.class);
            Throwables.throwIfUnchecked(throwable);
        }
    }

}
