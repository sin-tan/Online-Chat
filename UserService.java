import java.util.Scanner;

public class OnlineChat {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();

        System.out.println("Welcome to the Online Chat!");
        while (true) {
            System.out.println("1. Add User\n2. Send Friend Request\n3. Approve Friend Request\n4. Send Message\n5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter user ID: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter username: ");
                    String name = scanner.nextLine();
                    userService.addUser(userId, name, "pass");
                    System.out.println("User added successfully!");
                    break;
                case 2:
                    System.out.print("Enter your user ID: ");
                    int fromId = scanner.nextInt();
                    System.out.print("Enter friend's user ID: ");
                    int toId = scanner.nextInt();
                    userService.addFriendRequest(fromId, toId);
                    System.out.println("Friend request sent.");
                    break;
                case 3:
                    System.out.print("Enter your user ID: ");
                    int approverId = scanner.nextInt();
                    System.out.print("Enter friend's user ID: ");
                    int requesterId = scanner.nextInt();
                    userService.approveFriendRequest(requesterId, approverId);
                    break;
                case 4:
                    System.out.print("Enter your user ID: ");
                    int senderId = scanner.nextInt();
                    System.out.print("Enter friend's user ID: ");
                    int receiverId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter message: ");
                    String message = scanner.nextLine();
                    User sender = userService.getUser(senderId);
                    if (sender != null) {
                        sender.messageUser(receiverId, message);
                    } else {
                        System.out.println("User not found.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting chat application.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
