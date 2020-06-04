package me.metrofico.hardcoins.command.moneysub;

import me.metrofico.hardcoins.Jecon;
import me.metrofico.hardcoins.command.MoneyCommand;
import me.metrofico.hardcoins.config.MessageStruct;
import me.metrofico.hardcoins.db.Database;
import org.bukkit.command.CommandSender;


public class Take implements MoneyCommand {

	private final MessageStruct message;
	private final Database db;

	public Take(Jecon jecon) {
		message = jecon.getMessageStruct();
		db = jecon.getDb();
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// 権限チェック
		if (!sender.hasPermission("hardcon.take")) {
			sender.sendMessage(message.getDontHavePermission());
			return;
		}
		// 引数チェック
		if (args.length > 3) {
			sender.sendMessage(message.getHelpTake());
			return;
		}

		double amount;
		// 金額指定をパース
		try {
			amount = Double.parseDouble(args[2]);
			// 0以下なら不正
			if (amount < 0) {
				sender.sendMessage(message.getInvalidAmount());
				return;
			}
		} catch (NumberFormatException e) {
			sender.sendMessage(message.getInvalidAmount());
			return;
		}

		String result;
		switch (db.withdrawPlayer(args[1], amount)) {
		case ACCOUNT_NOT_FOUND:
			sender.sendMessage(message.getAccountNotFound());
			return;
		case NOT_ENOUGH:
			result = message.getTakeNotEnough();
			break;
		case SUCCESS:
			result = message.getTakeSuccess();
			break;
		default:
			result = message.getUnknownError();
			break;
		}
		sender.sendMessage(result
				.replace(MessageStruct.MACRO_PLAYER, args[1])
				.replace(MessageStruct.MACRO_BALANCE, db.format(amount)));
	}
}
