package thevoxstudios.chatcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.chat.Chat;

public class ChatCore extends JavaPlugin implements Listener 
{
	private Chat chat = null;
	
	private Chat getChat() 
	{
		return this.chat;
	}

	@Override 
	public void onEnable()
	{
		setupChat();
		if (!setupChat())
		{
			Bukkit.getLogger().info("[VChatCore] Failed to hook onto Vault, disabling plugin.");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getLogger().info("[VChatCore] Is now enabled!");
	}
	
	@Override
	public void onDisable()
	{
		Bukkit.getLogger().info("[VChatCore] Is now disabled...cya!");
	}
	
	@EventHandler
	public void onHash(AsyncPlayerChatEvent e) 
	{
		System.out.println("onHash fired");
		String msg = e.getMessage();	
		Player p = e.getPlayer();
		ChatColor cc = ChatColor.RESET;
		
		if (getChat().playerInGroup(p, "Owner"))
		{
			cc = ChatColor.RED;
		}
		else if (getChat().playerInGroup(p, "Dev"))
		{
			cc = ChatColor.RED;
		}
		else if (getChat().playerInGroup(p, "Admin"))
		{
			cc = ChatColor.RED;
		}
		else if (getChat().playerInGroup(p, "Mod"))
		{
			cc = ChatColor.LIGHT_PURPLE;
		}
		else if (getChat().playerInGroup(p, "Helper"))
		{
			cc = ChatColor.BLUE;
		}
		
		while (msg.contains("#"))
		{
			msg = msg.substring(msg.indexOf("#"));
			String hashtag = null;
			if (msg.indexOf(" ") == -1)
			{
				hashtag = msg.substring(1);
			}
			else
			{
				hashtag = msg.substring(msg.indexOf("#") + 1, msg.indexOf(" ", msg.indexOf("#")));
			}
			e.setMessage(e.getMessage().replace(hashtag, ChatColor.YELLOW + hashtag + cc).replace("#", ChatColor.YELLOW + "#"));
			if (msg.indexOf(" ") != -1)
			{
				msg = msg.substring(msg.indexOf(" "));
			}
			else
			{
				msg = "";
			}
		}	
	}	
	
	private boolean setupChat() 
	{
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
		if (chatProvider != null) 
		{
			this.chat = ((Chat)chatProvider.getProvider());
		}
		return this.chat != null;
	}
}
