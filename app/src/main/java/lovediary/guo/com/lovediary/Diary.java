package lovediary.guo.com.lovediary;

public class Diary {
    private String _id;
    private String content;
    private String title;
    private String date;
    private String username;
    private String categoyr;
    private Integer stick;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategoyr() {
        return categoyr;
    }

    public void setCategoyr(String categoyr) {
        this.categoyr = categoyr;
    }

    public Integer getStick() {
        return stick;
    }

    public void setStick(Integer stick) {
        this.stick = stick;
    }
}
