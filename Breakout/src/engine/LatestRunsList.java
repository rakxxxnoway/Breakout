package engine;

import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;

public class LatestRunsList
{
    private LinkedList<Integer> runs;
    private DefaultListModel<String> model;

    public LatestRunsList()
    {
        runs = new LinkedList<>();
        model = new DefaultListModel<>();
    }

    public DefaultListModel<String> getModel()
    {
        return model;
    }

    public void addRun(int score)
    {
        runs.add(score);

        if (runs.size() > 3)
        {
            runs.removeFirst();
        }

        updateModel();
    }

    private void updateModel()
    {
        model.clear();

        int place = 1;

        for (int i = runs.size() - 1; i >= 0; i--)
        {
            String text = place + ". " + runs.get(i);
            model.addElement(text);
            place++;
        }
    }

    public void setRuns(List<Integer> list)
    {
        runs.clear();

        for (int score : list)
        {
            runs.add(score);

            if (runs.size() > 3)
            {
                runs.removeFirst();
            }
        }

        updateModel();
    }

    public List<Integer> getRuns()
    {
        return runs;
    }
}