package engine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager
{
    private static final Path SCORE_FILE = Paths.get("data", "data.txt");

    private ScoreManager() {}

    public static class ScoreEntry
    {
        private final String name;
        private final int score;

        public ScoreEntry(String name, int score)
        {
            this.name = name;
            this.score = score;
        }

        public String getName()
        {
            return name;
        }

        public int getScore()
        {
            return score;
        }
    }

    private static void ensureFileExists() throws IOException
    {
        if (Files.notExists(SCORE_FILE.getParent()))
            Files.createDirectories(SCORE_FILE.getParent());

        if (Files.notExists(SCORE_FILE))
            Files.createFile(SCORE_FILE);
    }

    public static void saveScore(String name, int score)
    {
        try
        {
            ensureFileExists();

            if (name == null || name.trim().isEmpty())
                name = "Anonymous";

            name = name.trim().replace(";", ",");

            Files.writeString(
                SCORE_FILE,
                name + ";" + score + System.lineSeparator(),
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
                String trimmed = line.trim();

                if (trimmed.isEmpty())
                    continue;

                String[] parts = trimmed.split(";");

                if (parts.length != 2)
                    continue;

                String name = parts[0].trim();

                try
                {
                    int score = Integer.parseInt(parts[1].trim());
                    scores.add(new ScoreEntry(name, score));
                }
                catch (NumberFormatException ignored)
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

    public static String getTopScoresText(int limit)
    {
        List<ScoreEntry> scores = loadScores();

        if (scores.isEmpty())
            return "No scores yet.";

        scores.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        StringBuilder sb = new StringBuilder();

        int max = Math.min(limit, scores.size());

        for (int i = 0; i < max; i++)
        {
            ScoreEntry entry = scores.get(i);

            sb.append(i + 1)
              .append(". ")
              .append(entry.getName())
              .append(" - ")
              .append(entry.getScore())
              .append("\n");
        }

        return sb.toString();
    }

    public static String getLatestRunsText(int limit)
    {
        List<ScoreEntry> scores = loadScores();

        if (scores.isEmpty())
            return "No runs yet.";

        StringBuilder sb = new StringBuilder();

        int count = 0;

        for (int i = scores.size() - 1; i >= 0 && count < limit; i--, count++)
        {
            ScoreEntry entry = scores.get(i);

            sb.append("Run ")
              .append(count + 1)
              .append(": ")
              .append(entry.getName())
              .append(" - ")
              .append(entry.getScore())
              .append("\n");
        }

        return sb.toString();
    }
}