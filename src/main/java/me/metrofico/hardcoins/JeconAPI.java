package me.metrofico.hardcoins;


import me.metrofico.hardcoins.db.Database;
import org.bukkit.Bukkit;

public class JeconAPI {


    public static boolean hasAccount(String name) {
        try {
            if (Bukkit.getPlayer(name) != null) {
                return Jecon.getPlugin(Jecon.class).getDb().hasAccount(Bukkit.getPlayer(name));
            } else {
                return Jecon.getPlugin(Jecon.class).getDb().hasAccount(name);
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasAmountInAccount(String name, double amount) {
        if (Bukkit.getPlayer(name) != null) {
            if (hasAccount(name) && amount > 0 && Jecon.getPlugin(Jecon.class).getDb().getBalance(Bukkit.getPlayer(name)) > amount) {
                return true;
            }
        } else {
            if (hasAccount(name) && amount > 0 && Jecon.getPlugin(Jecon.class).getDb().getBalance(name) > amount) {
                return true;
            }
        }
        return false;
    }

    public static Database.Reason withdrawBalanceAccount(String name, double getamount) {
        if (hasAccount(name) && hasAmountInAccount(name, getamount)) {

            double amount;
            // 金額指定をパース
            try {
                amount = getamount;
                // 0以下なら不正
                if (amount < 0) {
                    return null;
                }
            } catch (NumberFormatException e) {
                return null;
            }
            if (Bukkit.getPlayer(name) != null) {
                switch (Jecon.getPlugin(Jecon.class).getDb().withdrawPlayer(Bukkit.getPlayer(name), amount)) {
                    case ACCOUNT_NOT_FOUND:
                        return Database.Reason.ACCOUNT_NOT_FOUND;
                    case NOT_ENOUGH:
                        return Database.Reason.NOT_ENOUGH;
                    case SUCCESS:
                        return Database.Reason.SUCCESS;
                    default:
                        break;
                }
            } else {
                switch (Jecon.getPlugin(Jecon.class).getDb().withdrawPlayer(name, amount)) {
                    case ACCOUNT_NOT_FOUND:
                        return Database.Reason.ACCOUNT_NOT_FOUND;
                    case NOT_ENOUGH:
                        return Database.Reason.NOT_ENOUGH;
                    case SUCCESS:
                        return Database.Reason.SUCCESS;
                    default:
                        break;
                }
            }
        }
        return null;
    }

    public static boolean createAccount(String name, double getamount) {

        if (!hasAccount(name) && getamount >= 0) {
            try {
                if (Bukkit.getPlayer(name) != null) {
                    Jecon.getPlugin(Jecon.class).getDb().createPlayerAccount(Bukkit.getPlayer(name), getamount);
                } else {
                    Jecon.getPlugin(Jecon.class).getDb().createPlayerAccount(name, getamount);
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        return false;
    }

    public static Database.Reason giveBalanceAccount(String name, double getamount) {

        if (hasAccount(name)) {
            double amount;
            // 金額指定をパース
            try {
                amount = getamount;
                // 0以下なら不正
                if (amount < 0) {
                    return null;
                }
            } catch (NumberFormatException e) {
                return null;
            }
            if (Bukkit.getPlayer(name) != null) {
                switch (Jecon.getPlugin(Jecon.class).getDb().depositPlayer(Bukkit.getPlayer(name), amount)) {
                    case ACCOUNT_NOT_FOUND:
                        return Database.Reason.ACCOUNT_NOT_FOUND;
                    case SUCCESS:
                        return Database.Reason.SUCCESS;
                    default:
                        break;
                }
            } else {
                switch (Jecon.getPlugin(Jecon.class).getDb().depositPlayer(name, amount)) {
                    case ACCOUNT_NOT_FOUND:
                        return Database.Reason.ACCOUNT_NOT_FOUND;
                    case SUCCESS:
                        return Database.Reason.SUCCESS;
                    default:
                        break;
                }
            }
        }
        return null;
    }

}
