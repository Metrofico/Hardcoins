package me.metrofico.hardcoins.command.moneysub;


import me.metrofico.hardcoins.Jecon;
import me.metrofico.hardcoins.command.MoneyCommand;
import me.metrofico.hardcoins.config.MessageStruct;
import me.metrofico.hardcoins.db.Database;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Top implements MoneyCommand {

	private final Database db;
	private final MessageStruct message;

	public Top(Jecon jecon) {
		db = jecon.getDb();
		message = jecon.getMessageStruct();
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// 権限チェック
		if (!sender.hasPermission("hardcon.top")) {
			sender.sendMessage(message.getDontHavePermission());
			return;
		}
		int page = 1;

		// 引数チェック
		if (args.length > 1) {
			try {
				page = Integer.parseInt(args[1]);
				// 0以下なら1に
				if (page < 1) {
					page = 1;
				}
			} catch (NumberFormatException e) {
				sender.sendMessage(message.getInvalidAmount());
				return;
			}
		}

		List<Database.Entry> list = db.topList(page);
		// 空チェック
		if (list.isEmpty()) {
			sender.sendMessage(message.getTopEmpty());
			return;
		}

		sender.sendMessage(message.getTopFirst().replace(MessageStruct.MACRO_PAGE, String.valueOf(page)));
		int i = (page - 1) * 10;
		for (Database.Entry entry : list) {
			++i;
			sender.sendMessage(message.getTopEntry()
					.replace(MessageStruct.MACRO_RANK, String.valueOf(i))
					.replace(MessageStruct.MACRO_PLAYER, entry.user)
					.replace(MessageStruct.MACRO_BALANCE, db.format(entry.balance)));
		}
	}

}
