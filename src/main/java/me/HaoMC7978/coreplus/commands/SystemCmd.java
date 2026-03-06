package me.HaoMC7978.coreplus.commands;

import me.HaoMC7978.coreplus.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SystemCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("coreplus.admin")) {
            sender.sendMessage("§cBạn không có quyền thực hiện lệnh này!");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§cSử dụng: /system <enable|disable|reload> <id>");
            return true;
        }

        String action = args[0].toLowerCase();
        String id = args[1];
        var manager = Main.getInstance().getSystemManager();

        switch (action) {
            case "enable" -> {
                manager.enableSystem(id);
                sender.sendMessage("§a[CorePlus] Đã bật hệ thống: §f" + id);
            }
            case "disable" -> {
                manager.disableSystem(id);
                sender.sendMessage("§c[CorePlus] Đã tắt hệ thống: §f" + id);
            }
            case "reload" -> {
                manager.reloadSystem(id);
                sender.sendMessage("§e[CorePlus] Đã tải lại hệ thống: §f" + id);
            }
            default -> sender.sendMessage("§cHành động không hợp lệ!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("enable", "disable", "reload").stream()
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (args.length == 2) {
            // Tự động hiện danh sách các System ID đã đăng ký
            return Main.getInstance().getSystemManager().getRegisteredSystemIds().stream()
                    .filter(s -> s.startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}