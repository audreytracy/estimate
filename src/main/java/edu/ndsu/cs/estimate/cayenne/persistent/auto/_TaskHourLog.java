package edu.ndsu.cs.estimate.cayenne.persistent.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;

import edu.ndsu.cs.estimate.cayenne.persistent.Task;

/**
 * Class _TaskHourLog was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _TaskHourLog extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String PK_PK_COLUMN = "PK";

    public static final Property<Integer> HOURS = Property.create("hours", Integer.class);
    public static final Property<LocalDateTime> TIMESTAMP = Property.create("timestamp", LocalDateTime.class);
    public static final Property<Task> TASK = Property.create("task", Task.class);

    protected int hours;
    protected LocalDateTime timestamp;

    protected Object task;

    public void setHours(int hours) {
        beforePropertyWrite("hours", this.hours, hours);
        this.hours = hours;
    }

    public int getHours() {
        beforePropertyRead("hours");
        return this.hours;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        beforePropertyWrite("timestamp", this.timestamp, timestamp);
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        beforePropertyRead("timestamp");
        return this.timestamp;
    }

    public void setTask(Task task) {
        setToOneTarget("task", task, true);
    }

    public Task getTask() {
        return (Task)readProperty("task");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "hours":
                return this.hours;
            case "timestamp":
                return this.timestamp;
            case "task":
                return this.task;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "hours":
                this.hours = val == null ? 0 : (int)val;
                break;
            case "timestamp":
                this.timestamp = (LocalDateTime)val;
                break;
            case "task":
                this.task = val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeInt(this.hours);
        out.writeObject(this.timestamp);
        out.writeObject(this.task);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.hours = in.readInt();
        this.timestamp = (LocalDateTime)in.readObject();
        this.task = in.readObject();
    }

}
