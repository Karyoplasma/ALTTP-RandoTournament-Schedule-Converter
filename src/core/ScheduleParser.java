package core;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ScheduleParser {
	
	public ScheduleParser() {
		
	}
	
	
	public void printInBerlinTime(ZonedDateTime americaTime) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        System.out.println(format.format(americaTime.withZoneSameInstant(ZoneId.of("Europe/Berlin"))));
	}
	
	public List<Broadcast> parseSchedule(boolean knownRestreamOnly) {
		List<Broadcast> broadcasts = new ArrayList<Broadcast>();
		Pattern p = Pattern.compile("([\\w-]*,\\s)*[\\w-]*\\s");
		try {
			String players = "";
			Document doc = Jsoup.connect("http://speedgaming.org/alttpr/").get();
			Elements tableElements = doc.select("table tbody tr");
			for (int i = 1; i < tableElements.size() - 1; i++) {
				List<Mode> modes = new ArrayList<Mode>();
				Elements data = tableElements.get(i).select("td");
				players = data.get(1).text();
				Matcher m = p.matcher(players);
				if (m.find()) {
					
					String modeString = m.group(0).trim();
					players = players.substring(m.end());
					for (String s : modeString.split(",\\s")){
							if (s.equals("Ganon")) {
								modes.add(Mode.DefeatGanon);
								continue;
							}
							if (s.equals("All-dungeons")) {
								modes.add(Mode.AllDungeons);
								continue;
							}
							if (s.equals("Uncle-assured")) {
								modes.add(Mode.UncleAssured);
								continue;
							}
							modes.add(Mode.valueOf(s));
					}
				} else {
					modes.add(Mode.None);
				}				
				Broadcast b = new Broadcast(data.get(0).text(), players, data.get(3).text(), data.get(2).text(), data.get(4).text(), modes);
				broadcasts.add(b);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return broadcasts;
	}

}
