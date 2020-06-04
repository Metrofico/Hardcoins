package me.metrofico.hardcoins.command.moneysub;

import me.metrofico.hardcoins.Jecon;
import me.metrofico.hardcoins.command.MoneyCommand;
import me.metrofico.hardcoins.config.MessageStruct;
import me.metrofico.hardcoins.db.Database;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Pay implements MoneyCommand {

	private final MessageStruct message;
	private final Database db;
	private final Jecon jecon;

	public Pay(Jecon jecon) {
		this.jecon = jecon;

		message = jecon.getMessageStruct();
		db = jecon.getDb();
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// 権限チェック
		if (!sender.hasPermission("hardcon.pay")) {
			sender.sendMessage(message.getDontHavePermission());
			return;
		}

		// プレイヤーチェック
		if (!(sender instanceof Player)) {
			sender.sendMessage(MessageStruct.PLAYER_ONRY);
			return;
		}

		// 引数チェック
		if (args.length < 3) {
			sender.sendMessage(message.getHelpPay());
			return;
		}

		double amount;
		// 金額指定をパース
		try {
			amount = Double.parseDouble(args[2]);
			// 0以下なら不正
			if (amount <= 0) {
				sender.sendMessage(message.getInvalidAmount());
				return;
			}
		} catch (NumberFormatException e) {
			sender.sendMessage(message.getInvalidAmount());
			return;
		}

		String targetName = args[1];
		// アカウント存在チェック
		if (!db.hasAccount(targetName)) {
			sender.sendMessage(message.getAccountNotFound());
			return;
		}

		Player from = (Player) sender;
		if (from.getName().equalsIgnoreCase(targetName)) { // 自分宛送金
			from.sendMessage(message.getPaySelf());
			return;
		}

		String result;
		String displayAmount = db.format(amount);
		// まずは出金
		Database.Reason reason = db.withdrawPlayer(from, amount);

		switch (reason) {
		case ACCOUNT_NOT_FOUND:
			result = message.getAccountNotFound();
			break;
		case NOT_ENOUGH:
			result = message.getPayNotEnough();
			break;
		case SUCCESS:
			result = message.getPaySuccess();
			break;
		default:
			result = message.getUnknownError();
			break;
		}
		sender.sendMessage(result
				.replace(MessageStruct.MACRO_PLAYER, targetName)
				.replace(MessageStruct.MACRO_BALANCE, displayAmount));
		// 成功以外ならエラーで帰る
		if (reason != Database.Reason.SUCCESS) {
			return;
		}

		// 入金
		Player target = jecon.getServer().getPlayer(targetName);
		reason = (target == null
				? db.depositPlayer(targetName, amount)
				: db.depositPlayer(target, amount));
		switch (reason) {
		case SUCCESS:
			if (target != null) {
				target.sendMessage(message.getPayReceive()
						.replace(MessageStruct.MACRO_PLAYER, sender.getName())
						.replace(MessageStruct.MACRO_BALANCE, displayAmount));
			}
			return;
		default:
			sender.sendMessage(message.getUnknownError());
        }
	}

}
