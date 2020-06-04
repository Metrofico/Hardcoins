package me.metrofico.hardcoins.command.moneysub;

import me.metrofico.hardcoins.Jecon;
import me.metrofico.hardcoins.command.MoneyCommand;
import me.metrofico.hardcoins.config.MessageStruct;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class Reload implements MoneyCommand {

	private final Jecon jecon;
	private final MessageStruct message;

	public Reload(Jecon jecon) {
		this.jecon = jecon;
		message = jecon.getMessageStruct();
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// 権限チェック
		if (!sender.hasPermission("hardcon.reload")) {
			sender.sendMessage(message.getDontHavePermission());
			return;
		}
		// リロード
		jecon.onEnable();
		// リロード完了のメッセージ
		sender.sendMessage("[" + ChatColor.GREEN + "Hardcon" + ChatColor.RESET + "] Config has been reloaded!!");
	}
}
