package me.metrofico.hardcoins.command.moneysub;

import jp.jyn.jecon.Jecon;
import jp.jyn.jecon.command.MoneyCommand;
import jp.jyn.jecon.config.MessageStruct;
import jp.jyn.jecon.db.Database;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NoArgs implements MoneyCommand {

    private final MessageStruct message;
    private final Database db;

    public NoArgs(Jecon jecon) {
        message = jecon.getMessageStruct();
        db = jecon.getDb();
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        String name;
        double balance;
        boolean other = false;
        // ユーザ名指定の有無をチェック
        if (args.length > 0) {
            // 指定有
            // 権限チェック
            if (!sender.hasPermission("hardcon.show.other")) {
                sender.sendMessage(message.getDontHavePermission());
                return;
            }
            name = args[0];
            balance = db.getBalance(name);
        } else {
            // 指定無
            // 権限チェック
            if (!sender.hasPermission("hardcon.show")) {
                sender.sendMessage(message.getDontHavePermission());
                return;
            }
            // コンソールチェック
            if (!(sender instanceof Player)) {
                sender.sendMessage(message.getHelpShow());
                return;
            }
            name = sender.getName();
            balance = db.getBalance((Player) sender);
            other = true;
        }
        // アカウント存在確認
        if (balance == -1) {
            sender.sendMessage(message.getAccountNotFound());
            return;
        }
        if (!other) {
            sender.sendMessage(message.getShowSuccess()
                    .replace(MessageStruct.MACRO_PLAYER, name)
                    .replace(MessageStruct.MACRO_BALANCE, db.format(balance)));
        } else {
            sender.sendMessage(message.getShowSuccessMe()
                    .replace(MessageStruct.MACRO_PLAYER, name)
                    .replace(MessageStruct.MACRO_BALANCE, db.format(balance)));
        }
    }

}
