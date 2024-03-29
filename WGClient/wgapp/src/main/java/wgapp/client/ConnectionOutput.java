package wgapp.client;

import io.socket.client.Socket;
/**
 * Class responsible for output to socket bound to server.
 * Various user events are sent to server. Custom named event is sent to server with some JSON data if required.
 * These events are then captured at server side and in turn server sends response or performs some actions at server side.
 * 
 * @author ak
 *
 */
public class ConnectionOutput implements Runnable{
	private Socket socket = ConnectionSocket.getSocket();

	public ConnectionOutput() {
		while (!this.socket.connected());
	}

	@Override
	public void run() {
		getWorkGroupList();
		User.getUser().setSocketID(ConnectionSocket.getSocket().id());
	}
	
	public void logIn(User user) {
		System.out.println(user.toJSON());
		this.socket.emit(Events.LOG_IN, user.toJSON());
	}
	
	public void createNewUser(User user) {
		this.socket.emit(Events.NEW_USER, user.toJSON());
	}
	/**
	 * Method to create group.
	 * Sending "newgroup" event to server with JSON string as user.
	 * @param user User JSON representation.
	 */
	public void createGroup(User user) {
		this.socket.emit(Events.NEW_GROUP, user.toJSON());
	}
	/**
	 * Event - "join_group" sending to server.
	 * @param user User JSON representation.
	 */
	public void joinGroup(User user) {
		this.socket.emit(Events.JOIN_GROUP, user.toJSON());
	}
	
	/**
	 * Event - "message" sending to server.
	 * @param user User JSON representation.
	 */
	public void sendMessage(String msg) {
		this.socket.emit("message", msg);
	}

	/**
	 * Event - ""get_workgroup_list"" sending to server.
	 * @param user User JSON representation.
	 */
	public void getWorkGroupList() {
		this.socket.emit(Events.WORKGROUP_LIST);
	}

	public void leaveGroup() {
		this.socket.emit("leave_group");
	}
}
