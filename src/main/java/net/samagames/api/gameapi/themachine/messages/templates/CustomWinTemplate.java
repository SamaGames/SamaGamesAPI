package net.samagames.api.gameapi.themachine.messages.templates;

import net.samagames.tools.chat.ChatUtils;

import java.util.ArrayList;

public class CustomWinTemplate
{
    public ArrayList<String> prepare(ArrayList<String> lines)
    {
        ArrayList<String> finalLines = new ArrayList<>();
        
        for(String line : lines)
        {
            finalLines.add(ChatUtils.getCenteredText(line));
        }
        
        return finalLines;
    }
    
    public void execute(ArrayList<String> lines)
    {
        new WinMessageTemplate().execute(this.prepare(lines));
    }
}
