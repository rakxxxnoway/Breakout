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
    private static final Path SCORE_FILE = Paths.get("data", "data.txt");

    private ScoreStorage() {}

    private static void ensureFileExists() throws IOException
    {
        Files.createDirectories(SCORE_FILE.getParent());
        Files.write(SCORE_FILE, new byte[0], StandardOpenOption.CREATE);
    }

    public static void saveScore(String initials, int score)
    {
        try
        {
            ensureFileExists();

            if (initials == null || initials.trim().isEmpty())
                initials = "ANO";

            initials = initials.trim().toUpperCase().replace(";", ",");

            if (initials.length() > 3)
                initials = initials.substring(0, 3);

            Files.writeString(
                SCORE_FILE,
                initials + ";" + score + System.lineSeparator(),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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

                if (parts.length != 2)
                    continue;

                String initials = parts[0].trim();

                try
                {
                    int score = Integer.parseInt(parts[1].trim());
                    scores.add(new ScoreEntry(initials, score));
                }
                catch (NumberFormatException e)
                {
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return scores;
    }
}