package me.metrofico.hardcoins.command.moneysub;

import me.metrofico.hardcoins.Jecon;
import me.metrofico.hardcoins.command.MoneyCommand;
import me.metrofico.hardcoins.config.MessageStruct;
import me.metrofico.hardcoins.db.Database;
import org.bukkit.command.CommandSender;


public class Remove implements MoneyCommand {

	private final MessageStruct message;
	private final Database db;

	public Remove(Jecon jecon) {
		message = jecon.getMessageStruct();
		db = jecon.getDb();
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// 権限チェック
		if (!sender.hasPermission("hardcon.remove")) {
			sender.sendMessage(message.getDontHavePermission());
			return;
		}
		// 引数チェック
		if (args.length > 2) {
			sender.sendMessage(message.getHelpRemove());
			return;
		}

		// 残高取得
		double balance = db.getBalance(args[1]);

		String result;
		switch (db.removePlayerAccount(args[1])) {
		case ACCOUNT_NOT_FOUND:
			result = message.getAccountNotFound();
			break;
		case SUCCESS:
			result = message.getRemoveSuccess();
			break;
		default:
			result = message.getUnknownError();
		}
		// 削除したアカウントを通知
		sender.sendMessage(result
				.replace(MessageStruct.MACRO_PLAYER, args[1])
				.replace(MessageStruct.MACRO_BALANCE, db.format(balance)));
	}

}
