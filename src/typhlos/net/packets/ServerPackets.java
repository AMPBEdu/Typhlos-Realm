package typhlos.net.packets;

import typhlos.client.Client;
import typhlos.net.packets.server.MapInfoPacket;
import typhlos.net.packets.server.TestPacket;

public enum ServerPackets {
	MAPINFO(58);

	int id;

	ServerPackets(int id) {
		this.id = id;
	}

	public int id() {
		return id;
	}

	public static void process(Client client, int id, byte[] data) {
		boolean known = false;
		for (ServerPackets packet : ServerPackets.values()) {
			if (id == packet.id) {
				known = true;
				parse(id, data).onReceive(client);
				System.out.println("s2c:Known: " + id);
				break;
			}
		}
		if (!known) {
			System.out.println("s2c:Unknown: " + id);
		}
	}

	public static ServerPacket parse(int id, byte[] data) {
		switch (id) {
		case 58:
			return new MapInfoPacket(data);
		default:
			return new ServerPacket(id, data);
		}
	}
}