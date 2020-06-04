package me.metrofico.hardcoins.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class MessageStruct {

    /**
     * 設定
     */
    private FileConfiguration conf = null;
    /**
     * カスタムコンフィグクラス
     */
    private final CustomConfig customconfig;
    /**
     * 使用されるプラグイン
     */
    private final Plugin plg;
    ;

    public static final String PLAYER_ONRY = ChatColor.RED + "Este comando solo se puede ejecutar desde un jugador.";
    public static final String MACRO_PLAYER = "[%player%]";
    public static final String MACRO_BALANCE = "[%balance%]";
    public static final String MACRO_PAGE = "[%page%]";
    public static final String MACRO_RANK = "[%rank%]";

    private String dontHavePermission;
    private String accountNotFount;
    private String invalidAmount;
    private String unknownError;

    private String showSuccess;

    private String payNotEnough;
    private String paySelf;
    private String paySuccess;
    private String payReceive;

    private String topEmpty;
    private String topFirst;
    private String topEntry;

    private String giveSuccess;

    private String takeSuccess;
    private String takeNotEnough;

    private String setSuccess;

    private String createExists;
    private String createSuccess;

    private String removeSuccess;

    private String helpShow;
    private String helpTop;
    private String helpGive;
    private String helpTake;
    private String helpSet;
    private String helpCreate;
    private String helpRemove;
    private String helpPay;
    private String helpHelp;
    private String helpReload;
    private String showSuccessMe;

    /**
     * 各種設定構造体を初期化します。
     *
     * @param plugin 対象のプラグイン
     */
    public MessageStruct(Plugin plugin) {
        // プラグイン
        plg = plugin;
        // カスタムコンフィグクラスをインスタンス化
        customconfig = new CustomConfig(plg, "message.yml");

        // 読み込み
        reloadConfig();
    }

    /**
     * 設定をリロードします。
     */
    public MessageStruct reloadConfig() {
        // デフォルトをセーブ
        customconfig.saveDefaultConfig();
        // confがnullではない(=リロード)
        if (conf != null) {
            // リロードを行う
            customconfig.reloadConfig();
        }
        // 設定を取得
        conf = customconfig.getConfig();

        dontHavePermission = customconfig.replaceColor(conf.getString("DontHavePermission"));
        accountNotFount = customconfig.replaceColor(conf.getString("AccountNotFound"));
        invalidAmount = customconfig.replaceColor(conf.getString("InvalidAmount"));
        unknownError = customconfig.replaceColor(conf.getString("UnknownError"));
        showSuccessMe = customconfig.replaceColor(conf.getString("Show.Me"));
        showSuccess = customconfig.replaceColor(conf.getString("Show.Success"));

        payNotEnough = customconfig.replaceColor(conf.getString("Pay.NotEnough"));
        paySelf = customconfig.replaceColor(conf.getString("Pay.Self"));
        paySuccess = customconfig.replaceColor(conf.getString("Pay.Success"));
        payReceive = customconfig.replaceColor(conf.getString("Pay.Receive"));

        topEmpty = customconfig.replaceColor(conf.getString("Top.Empty"));
        topFirst = customconfig.replaceColor(conf.getString("Top.First"));
        topEntry = customconfig.replaceColor(conf.getString("Top.Entry"));

        giveSuccess = customconfig.replaceColor(conf.getString("Give.Success"));

        takeSuccess = customconfig.replaceColor(conf.getString("Take.Success"));
        takeNotEnough = customconfig.replaceColor(conf.getString("Take.NotEnough"));

        setSuccess = customconfig.replaceColor(conf.getString("Set.Success"));

        createExists = customconfig.replaceColor(conf.getString("Create.Exists"));
        createSuccess = customconfig.replaceColor(conf.getString("Create.Success"));

        removeSuccess = customconfig.replaceColor(conf.getString("Remove.Success"));

        helpShow = customconfig.replaceColor("/eco [player] - " + conf.getString("Help.Show"));
        helpTop = customconfig.replaceColor("/eco top [page] - " + conf.getString("Help.Top"));
        helpGive = customconfig.replaceColor("/eco give <player> <amount> - " + conf.getString("Help.Give"));
        helpTake = customconfig.replaceColor("/eco take <player> <amount> - " + conf.getString("Help.Take"));
        helpSet = customconfig.replaceColor("/eco set <player> <amount> - " + conf.getString("Help.Set"));
        helpCreate = customconfig.replaceColor("/eco create <player> [amount] - " + conf.getString("Help.Create"));
        helpRemove = customconfig.replaceColor("/eco remove <player> - " + conf.getString("Help.Remove"));
        helpPay = customconfig.replaceColor("/eco pay <player> <amount> - " + conf.getString("Help.Pay"));
        helpHelp = customconfig.replaceColor("/eco help - " + conf.getString("Help.Help"));
        helpReload = customconfig.replaceColor("/eco reload - " + conf.getString("Help.Reload"));

        return this;
    }

    public String getDontHavePermission() {
        return dontHavePermission;
    }

    public String getAccountNotFound() {
        return accountNotFount;
    }

    public String getInvalidAmount() {
        return invalidAmount;
    }

    public String getUnknownError() {
        return unknownError;
    }

    public String getShowSuccess() {
        return showSuccess;
    }

    public String getShowSuccessMe() {
        return showSuccessMe;
    }

    public String getPayNotEnough() {
        return payNotEnough;
    }

    public String getPaySelf() {
        return paySelf;
    }

    public String getPaySuccess() {
        return paySuccess;
    }

    public String getPayReceive() {
        return payReceive;
    }

    public String getTopEmpty() {
        return topEmpty;
    }

    public String getTopFirst() {
        return topFirst;
    }

    public String getTopEntry() {
        return topEntry;
    }

    public String getGiveSuccess() {
        return giveSuccess;
    }

    public String getTakeNotEnough() {
        return takeNotEnough;
    }

    public String getTakeSuccess() {
        return takeSuccess;
    }

    public String getSetSuccess() {
        return setSuccess;
    }

    public String getCreateExists() {
        return createExists;
    }

    public String getCreateSuccess() {
        return createSuccess;
    }

    public String getRemoveSuccess() {
        return removeSuccess;
    }

    public String getHelpShow() {
        return helpShow;
    }

    public String getHelpTop() {
        return helpTop;
    }

    public String getHelpGive() {
        return helpGive;
    }

    public String getHelpTake() {
        return helpTake;
    }

    public String getHelpSet() {
        return helpSet;
    }

    public String getHelpCreate() {
        return helpCreate;
    }

    public String getHelpRemove() {
        return helpRemove;
    }

    public String getHelpPay() {
        return helpPay;
    }

    public String getHelpHelp() {
        return helpHelp;
    }

    public String getHelpReload() {
        return helpReload;
    }
}
