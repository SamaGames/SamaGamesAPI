package net.samagames.tools;

/**
 * ╱╲＿＿＿＿＿＿╱╲
 * ▏╭━━╮╭━━╮▕
 * ▏┃＿＿┃┃＿＿┃▕
 * ▏┃＿▉┃┃▉＿┃▕
 * ▏╰━━╯╰━━╯▕
 * ╲╰╰╯╲╱╰╯╯╱  Created by Silvanosky on 18/04/2016
 * ╱╰╯╰╯╰╯╰╯╲
 * ▏▕╰╯╰╯╰╯▏▕
 * ▏▕╯╰╯╰╯╰▏▕
 * ╲╱╲╯╰╯╰╱╲╱
 * ＿＿╱▕▔▔▏╲＿＿
 * ＿＿▔▔＿＿▔▔＿＿
 */


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Rules book utils
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class RulesBook
{
    private String title;
    private List<RulesBookPage> pages;
    private Set<String> owners;
    private Set<String> contributors;

    /**
     * Constructor
     * @param title The name of the game
     */
    public RulesBook(String title)
    {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.pages = new ArrayList<>();
        this.owners = new HashSet<>();
        this.contributors = new HashSet<>();
    }

    /**
     * Add a page to the book
     * @param title Page's title
     * @param content Page's content
     * @return Current book
     */
    public RulesBook addPage(String title, String content)
    {
        return addPage(title, content, true);
    }

    /**
     * Add a page to the book
     * @param title Page's title
     * @param content Page's content (lines)
     * @param summary true if the page should be added to Book Summary
     * @return Current book
     */
    public RulesBook addPage(String title, String[] content, boolean summary)
    {
        return addPage(title, String.join("\n", content), summary);
    }

    /**
     * Add a page to the book
     * @param title Page's title
     * @param content Page's content
     * @param summary true if the page should be added to Book Summary
     * @return Current book
     */
    public RulesBook addPage(String title, String content, boolean summary)
    {
        pages.add(new RulesBookPage(
                ChatColor.translateAlternateColorCodes('&', title),
                ChatColor.translateAlternateColorCodes('&', content),
                summary
        ));
        return this;
    }

    /**
     * Add a developer to the book.
     * @param name The developer's name
     * @return Current book
     */
    public RulesBook addOwner(String name)
    {
        owners.add(name);
        return this;
    }

    /**
     * Add a contributor to the projects.
     * Will be displayed in "Avec l'aide de" category.
     * @param name The contributor's name
     * @return Current book
     */
    public RulesBook addContributor(String name)
    {
        contributors.add(name);
        return this;
    }

    /**
     * Make an itemstack from the book.
     * Should only be called once (optimisation issues).
     * @return new ItemStack
     */
    public ItemStack toItemStack()
    {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta)item.getItemMeta();

        meta.setTitle(ChatColor.translateAlternateColorCodes('&', "&6&l" + ChatColor.stripColor(title) + " &7(Règles)"));

        String main = ChatColor.translateAlternateColorCodes('&',
                "\n   ]--------------[" +
                        "\n") + getCenteredText(title) + ChatColor.translateAlternateColorCodes('&', "&0" +
                "\n     par &lSamaGames&0" +
                "\n   ]--------------[" +
                "\n\n\n");
        int i = 1;
        for (RulesBookPage page : pages)
        {
            if (page.isInSummary())
                main += ChatColor.translateAlternateColorCodes('&', " &1" + i + ". &0") + page.getTitle() + "\n";
            i++;
        }
        meta.addPage(main);

        for (RulesBookPage page : pages)
            meta.addPage(getCenteredText(ChatColor.BOLD + page.getTitle()) + ChatColor.BLACK + "\n\n" + ChatColor.BLACK + page.getContent());

        String credits = ChatColor.translateAlternateColorCodes('&', "\n\nJeu développé par :\n");
        if (owners.isEmpty())
            credits += " - (Inconnu)\n";
        else
            for (String name : owners)
                credits += ChatColor.BLACK + " - " + ChatColor.BOLD + name + "\n";
        if (!contributors.isEmpty())
        {
            credits += ChatColor.BLACK + "\nAvec l'aide de :\n";
            for (String name : contributors)
                credits += ChatColor.BLACK + " - " + ChatColor.BOLD + name + "\n";
        }
        meta.addPage(credits);

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Return the title of the book
     * @return Title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Based on SamaGamesAPI algorithm
     * @param base The String to be centered
     */
    public static String getCenteredText(String base)
    {
        StringBuilder builder = new StringBuilder();
        int startPos = 12 - ChatColor.stripColor(base).length() / 2;

        for(int i = 0; i < startPos; i++)
        {
            builder.append(" ");
        }

        builder.append(base);

        return builder.toString();
    }

    private static class RulesBookPage
    {
        private String title;
        private String content;
        private boolean summary;

        private RulesBookPage(String title, String content, boolean summary)
        {
            this.title = title;
            this.content = content;
            this.summary = summary;
        }

        private String getTitle()
        {
            return title;
        }

        private String getContent()
        {
            return content;
        }

        private boolean isInSummary()
        {
            return summary;
        }
    }
}