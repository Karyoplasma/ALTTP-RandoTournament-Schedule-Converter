package core;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Broadcast {
	private ZonedDateTime date;
	private String players, commentators, channel, tracking;
	private List<Mode> modes;
	private ZoneId localZone;
	
	public Broadcast(String date, String players, String commentators, String channel, String tracking, List<Mode> modes) {
		this.date = this.parseDate(date);
		this.players = players;
		this.commentators = commentators;
		this.channel = channel;
		this.tracking = tracking;
		this.modes = modes;
		this.localZone = ZoneId.systemDefault();
	}
	public Broadcast(String date, String players, String commentators, String channel, String tracking, List<Mode> modes, ZoneId override) {
		this.date = this.parseDate(date);
		this.players = players;
		this.commentators = commentators;
		this.channel = channel;
		this.tracking = tracking;
		this.modes = modes;
		this.localZone = override;
	}
	public ZonedDateTime getDate() {
		return date.withZoneSameInstant(localZone);
	}

	public String getPlayers() {
		return players;
	}

	public String getCommentators() {
		return commentators;
	}

	public String getChannel() {
		return channel;
	}

	public String getTracking() {
		return tracking;
	}

	public List<Mode> getModes() {
		return modes;
	}
	
	public String getModesString() {
		StringBuffer buffer = new StringBuffer();
		for (Mode m : this.modes) {
			buffer.append(m.toString()).append(", ");
		}
		buffer.setLength(buffer.length() - 2);
		return buffer.toString();
	}
	public ZoneId getLocalZone() {
		return localZone;
	}

	public void setLocalZone(ZoneId localZone) {
		this.localZone = localZone;
	}
	
	public boolean knownRestream() {
		return channel.contains("Speed") || channel.contains("Randomizer");
	}
	
	public ZonedDateTime parseDate(String input) {
		int i = 0;
		int month = 0;
		int dayOfMonth = 0;
		int hour = 0;
		int minute = 0;
		for (String split : input.split(",?\\s+")) {
			switch (i++) {
				case 1: month = ShortMonth.valueOf(split).getValue();
						continue;
				case 2: dayOfMonth = Integer.parseInt(split);
						continue;
				case 3: hour = Integer.parseInt(split.substring(0, 2))%12;
						minute = Integer.parseInt(split.substring(3));
						continue;
				case 4: if (split.equals("PM")) {
							hour += 12;
						};
				default: continue;
			}
		}
		return ZonedDateTime.of(2018, month, dayOfMonth, hour, minute, 0, 0, ZoneId.of("America/New_York"));
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		buffer.append(formatter.format(date.withZoneSameInstant(localZone))).append("\t");
		buffer.append(players).append("\t");
		for (Mode mode : this.modes) {
			buffer.append(mode.toString()).append(", ");
		}
		buffer.setLength(buffer.length() - 2);
		buffer.append("\t");
		buffer.append(channel).append("\t");
		buffer.append(commentators).append("\t");
		buffer.append(tracking);
		return buffer.toString();
	}


	
}
