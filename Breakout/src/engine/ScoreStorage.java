package engine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ScoreStorage
{
    private static final Path SCORE_FILE = Paths.get(Settings.dataRootPath + Settings.DATA_FILE);

    
    private ScoreStorage() {}

    
    private static void ensureFileExists() throws IOException
    {
        if( !Files.exists(SCORE_FILE) )
        	Files.createFile(SCORE_FILE);
    }

    
    public static void saveScore(String initials, int score)
    {
        try
        {
            ensureFileExists();
            
            initials = initials.trim().toUpperCase().replace(";", ",");

            if (initials.length() > 3)
                initials = initials.substring(0, 3);

            String str = "%s;%d\n".formatted(initials, score);
            
            Files.writeString( SCORE_FILE, str, StandardOpenOption.APPEND );
        }
        catch (IOException e) 		{ e.printStackTrace(); }
    }

    
    public static List<ScoreEntry> loadScores()
    {
        List<ScoreEntry> scores = new ArrayList<>();

        try
        {
            ensureFileExists();

            List<String> lines = Files.readAllLines(SCORE_FILE, StandardCharsets.UTF_8);

            for (String line : lines)
            {
                line = line.trim();

                if (line.isEmpty())
                    continue;

                String[] parts = line.split(";");

                String initials = parts[0].trim();


                int score = Integer.parseInt(parts[1].trim());
                scores.add(new ScoreEntry(initials, score));
            }
        } catch (IOException e) 	{ e.printStackTrace(); }

        return scores;
    }
}