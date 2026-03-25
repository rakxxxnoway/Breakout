package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultListModel;

public class HighscoreList
{
    private List<ScoreEntry> entries;
    private DefaultListModel<String> model;

    public HighscoreList()
    {
        entries = new ArrayList<>();
        model = new DefaultListModel<>();
    }

    public DefaultListModel<String> getModel()
    {
        return model;
    }

    
    // Checks if the score qualifies to be saved
    
    public boolean qualifies(int score)
    {
        if (entries.size() < 10)
            return true;

        for (int i = 0; i < entries.size(); i++)
        {
            if (score > entries.get(i).getScore())
                return true;
        }
        return false;
    }
    

    public void addScore(String name, int score)
    {
        entries.add(new ScoreEntry(name, score));

        
        Collections.sort(entries, (a, b) -> b.getScore() - a.getScore());


        if (entries.size() > 10)
        {
            entries.remove(entries.size() - 1);
        }

        updateModel();
    }

    private void updateModel()
    {
        model.clear();

        for (int i = 0; i < entries.size(); i++)
        {
            ScoreEntry entry = entries.get(i);

            String text = (i + 1) + ". " + entry.getName() + " " + entry.getScore();
            model.addElement(text);
        }
    }

    public void setEntries(List<ScoreEntry> list)
    {
        entries.clear();

        for (ScoreEntry e : list)
        {
            entries.add(e);
        }

        Collections.sort(entries, (a, b) -> b.getScore() - a.getScore());

        while (entries.size() > 10)
        {
            entries.remove(entries.size() - 1);
        }


        updateModel();
    }

    public List<ScoreEntry> getEntries()
    {
        return entries;
    }
}