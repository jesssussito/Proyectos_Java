package Model;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Serializable{
    private static final long serialVersionUID=1L;
    Date date = new Date();
    private int identifier,priority,estimatedDuration;
    private String title,content;
    private boolean completed;
    public Task (int identifier, String title, Date date, String content, int priority, int estimatedDuration,boolean completed){
        this.identifier = identifier;
        this.title = title;
        this.date = date;
        this.content = content;
        this.priority = priority;
        this.estimatedDuration = estimatedDuration;
        this.completed = completed;
    }
    public Task() {
        
    }
    public int getIdentifier() {
        return identifier;
    }
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public String sacarApantalla(){
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        return String.valueOf(getIdentifier())+"\n"+getTitle()+"\n"+d.format(getDate())+"\n"+
        getContent()+"\n"+String.valueOf(getPriority())+"\n"+String.valueOf(getEstimatedDuration())+"\n"+String.valueOf(isCompleted())+"\n"+
        "--------------------------\n";
    }
}
