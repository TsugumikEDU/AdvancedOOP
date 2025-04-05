import java.util.List;

public class Group {
    private String groupName;
    private List<Person> members;

    public Group(String groupName, List<Person> members) {
        this.groupName = groupName;
        this.members = members;
    }

    public List<Person> getMembers() {
        return members;
    }

    public String getGroupName() {
        return groupName;
    }
}
