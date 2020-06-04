package me.metrofico.hardcoins.command.moneysub;

import me.metrofico.hardcoins.Jecon;
import me.metrofico.hardcoins.command.MoneyCommand;
import me.metrofico.hardcoins.config.ConfigStruct;
import me.metrofico.hardcoins.config.MessageStruct;
import me.metrofico.hardcoins.db.Database;
import org.bukkit.command.CommandSender;

public class Create implements MoneyCommand {

	private final ConfigStruct config;
	private final MessageStruct message;
	private final Database db;

	public Create(Jecon jecon) {
		config = jecon.getConfigStruct();
		message = jecon.getMessageStruct();
		db = jecon.getDb();
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// 権限チェック
		if (!sender.hasPermission("hardcon.create")) {
			sender.sendMessage(message.getDontHavePermission());
			return;
		}
		// 引数チェック
		if (args.length < 2) {
			sender.sendMessage(message.getHelpCreate());
			return;
		}

		double balance = config.getDefaultBalance();
		// 金額指定があるか
		if (args.length > 2) {
			// あればパース
			try {
				balance = Double.parseDouble(args[2]);
				// 0以下なら不正
				if (balance < 0) {
					sender.sendMessage(message.getInvalidAmount());
					return;
				}
			} catch (NumberFormatException e) {
				sender.sendMessage(message.getInvalidAmount());
				return;
			}
		}

		String result;
		if (db.hasAccount(args[1])) {
			result = message.getCreateExists();
		} else if (db.createPlayerAccount(args[1], balance)) {
			result = message.getCreateSuccess();
		} else {
			result = message.getUnknownError();
		}
		sender.sendMessage(result
				.replace(MessageStruct.MACRO_PLAYER, args[1])
				.replace(MessageStruct.MACRO_BALANCE, db.format(balance)));
	}

}
