package my.java8.misc;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class StringJoinerExample {
	public static void main(String[] args) {
		//1.1 Join String by a delimiter
		StringJoiner sj = new StringJoiner(",");
		sj.add("aaa");
		sj.add("bbb");
		sj.add("ccc");
		String result = sj.toString(); // aaa,bbb,ccc
		System.out.println(result);
		
		//1.2 Join String by a delimiter and starting with a supplied prefix and ending with a supplied suffix.
		StringJoiner sj2 = new StringJoiner("/", "prefix-", "-suffix");
        sj.add("2016");
        sj.add("02");
        sj.add("26");
        String result2 = sj2.toString(); //prefix-2016/02/26-suffix
        System.out.println(result2);
        
        //2.1 Join String by a delimiter.
        String result3 = String.join("-", "2015", "10", "31" );
        System.out.println(result3);
        
        //2.2 Join a List by a delimiter.
        List<String> list = Arrays.asList("java", "python", "nodejs", "ruby");//java, python, nodejs, ruby
        String result4 = String.join(", ", list);
        System.out.println(result4);
        
        //Collectors.joining
        String result5 = list.stream().map(x -> x).collect(Collectors.joining(" | "));//java | python | nodejs | ruby
        System.out.println(result5);
        List<Game> objectList = Arrays.asList(
                new Game("Dragon Blaze", 5),
                new Game("Angry Bird", 5),
                new Game("Candy Crush", 5)
        );
        String result6 = objectList.stream()
        		.map(x -> x.getName())
        		.collect(Collectors.joining(", ", "{", "}"));//{Dragon Blaze, Angry Bird, Candy Crush}
        System.out.println(result6);
	}

}
class Game{
    String name;
    int ranking;

    public Game(String name, int ranking) {
        this.name = name;
        this.ranking = ranking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
