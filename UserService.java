import java.util.*;

class UserService {
    private Map<Integer, User> usersById = new HashMap<>();

    public void addUser(int userId, String name, String passHash) {
        usersById.put(userId, new User(userId, name, passHash));
    }

    public void removeUser(int userId) {
        usersById.remove(userId);
    }

    public void addFriendRequest(int fromUserId, int toUserId) {
        User fromUser = usersById.get(fromUserId);
        User toUser = usersById.get(toUserId);
        if (fromUser != null && toUser != null) {
            toUser.receiveFriendRequest(fromUserId);
        }
    }

    public void approveFriendRequest(int fromUserId, int toUserId) {
        User fromUser = usersById.get(fromUserId);
        User toUser = usersById.get(toUserId);
        if (fromUser != null && toUser != null) {
            toUser.approveFriendRequest(fromUserId);
        }
    }

    public User getUser(int userId) {
        return usersById.get(userId);
    }
}

class User {
    private int userId;
    private String name;
    private String passHash;
    private Map<Integer, User> friends = new HashMap<>();
    private Map<Integer, PrivateChat> privateChats = new HashMap<>();
    private Map<Integer, GroupChat> groupChats = new HashMap<>();
    private Map<Integer, AddRequest> receivedFriendRequests = new HashMap<>();

    public User(int userId, String name, String passHash) {
        this.userId = userId;
        this.name = name;
        this.passHash = passHash;
    }

    public void messageUser(int friendId, String message) {
        if (friends.containsKey(friendId)) {
            PrivateChat chat = privateChats.computeIfAbsent(friendId, k -> new PrivateChat(this, friends.get(friendId)));
            chat.addMessage(new Message(message, new Date()));
            System.out.println(name + " to " + friends.get(friendId).name + ": " + message);
        } else {
            System.out.println("User is not your friend.");
        }
    }

    public void receiveFriendRequest(int friendId) {
        receivedFriendRequests.put(friendId, new AddRequest(friendId, this.userId, RequestStatus.UNREAD, new Date()));
        System.out.println("Friend request received from user " + friendId);
    }

    public void approveFriendRequest(int friendId) {
        if (receivedFriendRequests.containsKey(friendId)) {
            friends.put(friendId, new User(friendId, "Friend" + friendId, "hash")); // Example user
            receivedFriendRequests.remove(friendId);
            System.out.println("Friend request from " + friendId + " approved.");
        }
    }
}

abstract class Chat {
    protected List<User> users = new ArrayList<>();
    protected List<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        messages.add(message);
    }
}

class PrivateChat extends Chat {
    public PrivateChat(User firstUser, User secondUser) {
        this.users.add(firstUser);
        this.users.add(secondUser);
    }
}

class GroupChat extends Chat {
    public void addUser(User user) {
        users.add(user);
    }
}

class Message {
    private String message;
    private Date timestamp;

    public Message(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}

enum RequestStatus {
    UNREAD, READ, ACCEPTED, REJECTED;
}

class AddRequest {
    private int fromUserId;
    private int toUserId;
    private RequestStatus requestStatus;
    private Date timestamp;

    public AddRequest(int fromUserId, int toUserId, RequestStatus requestStatus, Date timestamp) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.requestStatus = requestStatus;
        this.timestamp = timestamp;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
