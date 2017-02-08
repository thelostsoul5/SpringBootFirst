package xyz.thelostsoul.hello;

/**
 * @author zhouzm5
 * @version v1.0.0
 * @Copyright Copyright (c) 2017 AsiaInfo
 * @ClassName
 * @Description
 * @date 2017/2/6 <br>
 * Modification History:<br>
 * Date Author Version Description<br>
 * ---------------------------------------------------------*<br>
 * 2017/2/6 zhouzm5 v1.0.0 <br>
 */
public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
